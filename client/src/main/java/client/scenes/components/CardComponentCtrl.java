package client.scenes.components;

import client.MyFXML;
import client.scenes.MainCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.*;
import javafx.fxml.*;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;



public class CardComponentCtrl {


    Card card;
    @FXML
    public Pane cardPane;
    @FXML
    private Label title;
    @FXML
    private Label description;
    private final ServerUtils serverUtils;
    private final MainCtrl mainCtrl;
    private final MyFXML fxml;

    @Inject
    public CardComponentCtrl(ServerUtils serverUtils, MainCtrl mainCtrl, MyFXML fxml) {
        this.serverUtils = serverUtils;
        this.mainCtrl = mainCtrl;
        this.fxml = fxml;
    }



    /** Sets the details of a card */
    public void setCard(Card card) {
        this.card = card;
        title.setText(card.cardTitle);
        description.setText(card.cardDescription);
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

            int sourceList = Integer.parseInt(dragboard.getString().split(" ")[0]);
            int cardIdentifier = Integer.parseInt(dragboard.getString().split(" ")[1]);

            //Info printouts

            System.out.println("Source list: " + sourceList +", Current List: " + card.cardListId);
            System.out.println("Card identifier: " + cardIdentifier);

            // Temporary solution to retrieve cardList to retrieve index. Will need alternative solution
            Result<Card> res = serverUtils.getCard(cardIdentifier);
            Result<CardList> cardListResult = serverUtils.getList(card.cardListId);


            if(res.success && cardListResult.success){
                Card card1 = res.value;
                CardList cardList = cardListResult.value;

                // Index to print is the index of the card in the list
                int indexTo = cardList.cardList.indexOf(card);
                System.out.println("IndexTo: " +  indexTo);

                // Move the card to the new list
                serverUtils.moveCardBetweenLists(card1,sourceList, card.cardListId, indexTo);
                success = true;
            }
        }
        event.setDropCompleted(success);
        event.consume();
        // Refresh the board may need refactoring after webSockets
        mainCtrl.refreshBoard();
    }
}
