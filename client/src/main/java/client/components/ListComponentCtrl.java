package client.components;

import client.MultiboardCtrl;
import client.SceneCtrl;
import client.interfaces.InstanceableComponent;
import client.utils.MyFXML;
import commons.utils.IDGenerator;
import commons.utils.RandomIDGenerator;
import client.utils.ServerUtils;
import com.google.inject.*;
import commons.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListComponentCtrl implements InstanceableComponent {

    private final MultiboardCtrl multiboardCtrl;

    private final IDGenerator idGenerator;
    private MyFXML fxml;
    private ServerUtils server;
    private SceneCtrl sceneCtrl;

    private CardList cardList;

    @FXML
    private TextField title;
    @FXML
    public ListView<Parent> listView;

    private List<CardComponentCtrl> cardComponentCtrls;

    @Inject
    public ListComponentCtrl(MultiboardCtrl multiboardCtrl, RandomIDGenerator idGenerator, ServerUtils server,
                             SceneCtrl sceneCtrl, MyFXML fxml) {
        this.multiboardCtrl = multiboardCtrl;
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.cardComponentCtrls = new ArrayList<>();
        this.server = server;
        this.idGenerator = idGenerator;
    }


    @Override
    public void refresh() {
        System.out.println("Refreshing a list with id:\t" + cardList.getCardListId() + "\twith\t"
                + cardList.cardList.size() + "\tcards");
        this.cardList = server.getList(cardList.getCardListId()).value;
        setList(cardList);
    }

    @Override
    public void registerForMessages(){
        System.out.println("List:\t" + cardList.getCardListId() + "\tregistered for messaging");
        server.registerForMessages("/topic/update-cardlist/", UUID.class, payload ->{
            System.out.println("Endpoint \"/topic/update-cardlist/\" has been hit by a list with the id:\t"
                    + payload);
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
        });
    }


    /**
     * Refreshes the list with up-to-date data, propagates trough CardComponentCtrl
     */
    public void setList(CardList cardList) {
        this.cardList = cardList;
        title.setText(cardList.cardListTitle);

        var cardNodes = listView.getItems();
        cardNodes.remove(0, cardNodes.size()-1);
        for (var card : cardList.cardList) {
            addSingleCard(card);
        }

    }

    /** Adds a single card to the board
     * @param card that gets added
     * */
    public void addSingleCard(Card card) {
//        ObservableList<Node> cardNodes = cardContainer.getChildren();
//        Pair<CardComponentCtrl, Parent> component = fxml.load(
//                CardComponentCtrl.class, "client", "scenes", "components", "CardComponent.fxml");
//        Parent parent = component.getValue();
//        CardComponentCtrl cardComponentCtrl = component.getKey();
//        cardComponentCtrl.setCard(card);
//        cardComponentCtrl.setCardId(idGenerator.generateID());
//        cardComponentCtrls.add(cardComponentCtrl);
//        cardNodes.add(cardNodes.size()-1, parent);
        if (card == null) {
            return;
        }

        var cardNodes = listView.getItems();
        var component = fxml.load(CardComponentCtrl.class, "client", "scenes", "components", "CardComponent.fxml");
        var parent = component.getValue();
        var ctrl = component.getKey();
        ctrl.setCard(card);
        cardComponentCtrls.add(ctrl);
        cardNodes.add(cardNodes.size(), parent);

    }

    /**
     * @param event The mouse event that triggered the drag
     *              This method is called when the user drags a card component
     */
    public void dragOverDetected(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            Dragboard dragboard = event.getDragboard();
            UUID cardIdentifier = UUID.fromString(dragboard.getString().split(" ")[1]);
            UUID sourceList = UUID.fromString(dragboard.getString().split(" ")[0]) ;
            System.out.println("Detected a drag event in\t" + cardList.cardListId + "\ton card\t" + cardIdentifier);
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);

        }
        event.consume();
    }

    /**
     * @param event The mouse event that triggered the drag
     *
     * This method is called when the user drops a dragged card component
     */
    public void dragDropped(DragEvent event){
        System.out.println("Drag drop detected\t " + cardList.cardList.size() + " CARDS in list");
        Dragboard dragboard = event.getDragboard();
        boolean success = false;

        if(dragboard.hasString()){

            UUID sourceList = UUID.fromString(dragboard.getString().split(" ")[0]) ;
            UUID cardIdentifier = UUID.fromString(dragboard.getString().split(" ")[1]);

            System.out.println("Card identifier: " + cardIdentifier);
            Result<Card> res = server.getCard(cardIdentifier);

            if(res.success){
                Card card = res.value;
                if(!cardList.cardListId.equals(sourceList)){
                    server.moveCardBetweenLists(card,sourceList, cardList.cardListId,cardList.cardList.size());
                }
                else{
                    server.moveCardBetweenLists(card,sourceList, sourceList,0);
                }
                success = true;
            }
        }
        event.setDropCompleted(success);
        event.consume();
    }

    /**
     * Goes to add new card scene
     */
    public void addCardPopUp() {
        sceneCtrl.showCardCreationPopup(cardList);
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
     * updates name
     * @param actionEvent
     */
    public void updateName(ActionEvent actionEvent) {
        this.cardList.setCardListTitle(title.getText());
        server.editList(this.cardList, cardList.getCardListId());
        //Should be updated by websockets
    }

    /**
     * Deletes list
     * @param mouseEvent
     */
    public void deleteList(MouseEvent mouseEvent) {
        server.deleteList(this.cardList.cardListId, cardList);
    }
}

