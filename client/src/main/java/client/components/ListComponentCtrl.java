package client.components;

import client.SceneCtrl;
import client.interfaces.InstanceableComponent;
import client.utils.MyFXML;
import commons.utils.IDGenerator;
import commons.utils.RandomIDGenerator;
import client.utils.ServerUtils;
import com.google.inject.*;
import commons.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListComponentCtrl implements InstanceableComponent {
    private final IDGenerator idGenerator;
    private MyFXML fxml;
    private ServerUtils server;
    private SceneCtrl sceneCtrl;

    private CardList cardList;


    @FXML
    private Label cardListName;
    @FXML
    private VBox cardContainer;

    private List<CardComponentCtrl> cardComponentCtrls;

    @Inject
    public ListComponentCtrl(RandomIDGenerator idGenerator, ServerUtils server, SceneCtrl sceneCtrl, MyFXML fxml) {
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.cardComponentCtrls = new ArrayList<>();
        this.server = server;
        this.idGenerator = idGenerator;
    }


    /**
     * Registers the component for receiving message from the websocket
     */
    public void registerForMessages(){
        System.out.println("List:\t" + cardList.getCardListId() + "\tregistered for messaging");
        server.registerForMessages("/topic/update-cardlist/", UUID.class, payload ->{
                    System.out.println("Endpoint \"/topic/update-cardlist/\" has been hit by a list with the id:\t" + payload);
                    try {
                        if(payload.equals(cardList.getCardListId())){
                            System.out.println("Refreshing list:\t" + cardList.getCardListId() + "\twith\t"
                                    + cardList.getCardList().size() + "\tlists");
                            // Needed to prevent threading issues
                            Platform.runLater(() -> refresh());
                        }
                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }

    @Override
    public void refresh() {
        this.cardList = server.getList(cardList.getCardListId()).value;
        setList(cardList);
    }


    /** Setter for list id
     * @param cardListID
     */
    public void setCardListID(UUID cardListID) {
        this.cardList.setCardListId(cardListID);
    }

    /** Getter for list id
     * @return listID
     */
    public UUID getListId() {
        return cardList.getCardListId();
    }

    /**
     * Refreshes the list with up-to-date data, propagates trough CardComponentCtrl
     */
    public void setList(CardList cardList) {
        var cards = cardContainer.getChildren();
        this.cardList = cardList;
        setCardListID(cardList.getCardListId());
        cards.remove(0, cards.size()-1);
        this.cardListName.setText(cardList.getCardListTitle());
        for(Card card: cardList.getCardList()){
            addSingleCard(card);
        }
    }

    /** Adds a single card to the board
     * @param card that gets added
     * */
    public void addSingleCard(Card card) {
        ObservableList<Node> cardNodes = cardContainer.getChildren();
        Pair<CardComponentCtrl, Parent> component = fxml.load(
                CardComponentCtrl.class, "client", "scenes", "components", "CardComponent.fxml");
        Parent parent = component.getValue();
        CardComponentCtrl cardComponentCtrl = component.getKey();
        cardComponentCtrl.setCard(card);
        cardComponentCtrl.setCardId(idGenerator.generateID());
        cardComponentCtrls.add(cardComponentCtrl);
        cardNodes.add(cardNodes.size()-1, parent);
    }

    /**
     * Goes to add new card scene
     */
    public void addCardPopUp() {
        sceneCtrl.showCardCreationPopup(cardList);
    }
}
