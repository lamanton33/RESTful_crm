package client.components;

import client.MultiboardCtrl;
import client.interfaces.InstanceableComponent;
import client.utils.MyFXML;
import client.SceneCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.*;
import javafx.application.Platform;
import javafx.fxml.*;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.UUID;

public class CardComponentCtrl implements InstanceableComponent {
    private final MultiboardCtrl multiboardCtrl;
    private ServerUtils server;
    private MyFXML fxml;
    private SceneCtrl sceneCtrl;
    @FXML
    public Pane cardPane;
    @FXML
    private Label title;
    @FXML
    private Label description;
    private Card card;

    /** Initialises the controller using dependency injection */
    @Inject
    public CardComponentCtrl(ServerUtils server, SceneCtrl sceneCtrl, MyFXML fxml, MultiboardCtrl multiboardCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.server = server;
        this.multiboardCtrl = multiboardCtrl;
    }

    @Override
    public void registerForMessages(){
        server.registerForMessages("/topic/update-card", UUID.class, payload ->{
            try {
                if(payload.equals(card.getCardID())){
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
        Result<Card> res = server.getCard(card.getCardID());
        if(res.success){
            setCard(res.value);
        }else {
            System.out.println("Error: " + res.message);
        }
    }

    /** Sets the details of a card
     * @param card
     * */
    public void setCard(Card card) {
        this.card = card;
        title.setText(card.cardTitle);
        description.setText(card.cardDescription);
    }

    /**
     * Clears fields
     */
    public void clear() {
        title.setText("");
    }

    /** Setter for card
     * @param cardId
     */
    public void setCardId(UUID cardId) {
        this.card.setCardID(cardId);
    }

    /** Starts editing the card that was clicked */
    public void editCard(MouseEvent event) {
        if (event.getClickCount() == 2) {
            sceneCtrl.editCard(card);
        }
    }

    /**
     * @param event The mouse event that triggered the drag
     *
     *              This method is called when the user drags a card component
     */
    public void dragDetected(MouseEvent event) {

        System.out.println("Drag detected");
        Dragboard db = cardPane.startDragAndDrop(TransferMode.COPY_OR_MOVE);

        SnapshotParameters params = new SnapshotParameters();

        Group group = new Group(cardPane);
        group.setStyle("-fx-background-color: #2A2A2A; -fx-background-radius: 13;");

        params.setFill(Color.TRANSPARENT);
        WritableImage image = cardPane.snapshot(params, null);

        db.setDragView(image,event.getX(),event.getY());

        ClipboardContent content = new ClipboardContent();
        content.putString(card.cardListId + " " + card.cardID);
        db.setContent(content);

        event.consume();
    }

    /**
     * Tells status of dragging
     * @param evt
     */
    public void setOnDragDone(DragEvent evt){
        if (evt.getTransferMode() == null) {
//            sceneCtrl.refreshList(card.cardListId);
//            could refresh list somehoe TO BE IMPLEMENTED!!!
            System.out.println("drag aborted");
        } else {
            System.out.println("drag successfully completed");
        }
    }

    /**
     * @param event The drag event that triggered the drag over
     *
     *              Dragging over a card component enables data transfer
     */
    public void dragOver(DragEvent event){
        Dragboard db = event.getDragboard();
        if (db.hasString()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            event.consume();
        }
    }


    /**
     * @param event The drag event that triggered the drop
     * <p>
     *             This method is called when the user drops a card
     *             component on another card component.
     */
    public void dragDrop(DragEvent event){
        System.out.println("Drag drop detected");
        Dragboard dragboard = event.getDragboard();
        boolean success = false;

        // If the dragboard has a string, then the card was dragged from another list
        if(dragboard.hasString()){

            UUID sourceList = UUID.fromString(dragboard.getString().split(" ")[0]) ;
            UUID cardIdentifier = UUID.fromString(dragboard.getString().split(" ")[1]);

            //Info printouts

            System.out.println("Source list: " + sourceList +", Current List: " + card.cardListId);
            System.out.println("Card identifier: " + cardIdentifier);

            // Temporary solution to retrieve cardList to retrieve index. Will need alternative solution
            Result<Card> res = server.getCard(cardIdentifier);
            Result<CardList> cardListResult = server.getList(card.cardListId);


            if(res.success && cardListResult.success){
                Card card1 = res.value;
                CardList cardList = cardListResult.value;

                // Index to print is the index of the card in the list
                int indexTo = cardList.cardList.indexOf(card);

                System.out.println("IndexTo: " +  indexTo);

                // Move the card to the new list
                server.moveCardBetweenLists(card1,sourceList, card.cardListId, indexTo);
                success = true;

            }
        }
        event.setDropCompleted(success);
        event.consume();
        // Refresh the board may need refactoring after webSockets
        ;
    }

    /**
     * Deletes a card from the repo on click of the trash icon
     */
    public void deleteCard(MouseEvent mouseEvent) {
        server.deleteCard(this.card,this.card.cardListId);
    }
}
