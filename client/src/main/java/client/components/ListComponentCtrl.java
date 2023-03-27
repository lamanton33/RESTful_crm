package client.components;

import client.SceneCtrl;
import client.interfaces.InstanceableComponent;
import client.utils.MyFXML;
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

public class ListComponentCtrl implements InstanceableComponent {
    private MyFXML fxml;
    private ServerUtils server;
    private SceneCtrl sceneCtrl;

    private List<CardList> cardList;

    private int cardListID;

    @FXML
    private Label cardListName;
    @FXML
    private VBox cardContainer;

    private List<CardComponentCtrl> cardComponentCtrls;

    @Inject
    public ListComponentCtrl(ServerUtils server, SceneCtrl sceneCtrl, MyFXML fxml) {
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.cardComponentCtrls = new ArrayList<>();
        this.server = server;
    }

    @Override
    public void refresh() {

    }

    @Override
    public void registerForMessages(){
        server.registerForMessages("/topic/update-cardlist", payload ->{
                    try {
                        Result result = (Result) payload;
                        int potentialCardListID =  (Integer) result.value;
                        if(potentialCardListID == cardListID){
                            Platform.runLater(() -> refresh());
                        }
                    } catch (RuntimeException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }


    /** Setter for list id
     * @param cardListID
     */
    public void setCardListID(int cardListID) {
        this.cardListID = cardListID;
    }

    /** Getter for list id
     * @return listID
     */
    public int getListId() {
        return cardListID;
    }

    /**
     * Refreshes the list with up-to-date data, propagates trough CardComponentCtrl
     */
    public void setList(CardList cardList) {
        var cards = cardContainer.getChildren();
        setCardListID(cardList.getCardListID());
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
        cardComponentCtrls.add(cardComponentCtrl);
        cardNodes.add(cardNodes.size()-1, parent);
    }

    /**
     * Goes to add new card scene
     */
    public void addCardPopUp() {
        sceneCtrl.showCardCreationPopup(cardListID);
    }
}
