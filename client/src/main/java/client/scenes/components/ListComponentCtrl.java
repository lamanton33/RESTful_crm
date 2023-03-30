package client.scenes.components;

import client.*;
import client.scenes.*;
import client.utils.ServerUtils;
import com.google.inject.*;
import commons.*;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.util.*;

public class ListComponentCtrl {


    private final ServerUtils serverUtils;
    private CardList list;
    private final MainCtrl mainCtrl;
    private final MyFXML fxml;
    private final List<CardComponentCtrl> cardControllers;

    @FXML
    public ListView<Parent> listView;
    @FXML
    private Label listName;

    @Inject
    public ListComponentCtrl(MainCtrl mainCtrl, MyFXML fxml, ServerUtils serverUtils) {
        this.mainCtrl = mainCtrl;
        this.fxml = fxml;
        this.serverUtils = serverUtils;
        cardControllers = new ArrayList<>();

    }


    /**
     * @param event The mouse event that triggered the drag
     *              This method is called when the user drags a card component
     */
    public void dragOverDetected(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasString()) {

            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }


    /** Sets the data the list is supposed to display */
    public void setList(CardList list) {

        this.list = list;
        listName.setText(list.cardListTitle);

        var cardNodes = listView.getItems();
        cardNodes.remove(0, cardNodes.size()-1);
        for (var card : list.cardList) {
            addSingleCard(card);
        }
    }


    /**
     * @param event The mouse event that triggered the drag
     *
     * This method is called when the user drops a dragged card component
     */
    public void dragDropped(DragEvent event){
        System.out.println("Drag drop detected");
        Dragboard dragboard = event.getDragboard();
        boolean success = false;

        if(dragboard.hasString()){

            int sourceList = Integer.parseInt(dragboard.getString().split(" ")[0]);
            int cardIdentifier = Integer.parseInt(dragboard.getString().split(" ")[1]);

            System.out.println("Card identifier: " + cardIdentifier);
            Result<Card> res = serverUtils.getCard(cardIdentifier);

            if(res.success){
                Card card = res.value;
                if(list.cardListID != sourceList){

                    serverUtils.moveCardBetweenLists(card,sourceList, list.cardListID,list.cardList.size());
                }
                success = true;
            }
        }
        event.setDropCompleted(success);
        event.consume();
        mainCtrl.refreshBoard();
    }


    /** Adds a single list to the board */
    public void addSingleCard(Card card) {
        if (card == null) {
            return;
        }

        var cardNodes = listView.getItems();
        var component = fxml.load(CardComponentCtrl.class, "client", "scenes", "components", "CardComponent.fxml");
        var parent = component.getValue();
        var ctrl = component.getKey();
        ctrl.setCard(card);
        cardControllers.add(ctrl);
        cardNodes.add(cardNodes.size(), parent);
    }

    /**
     * Goes to add new card scene
     */
    public void addCard() {
        mainCtrl.showCardCreationPopup(list.cardListID);
    }

    /** Gets the id of the list controlled here */
    public int getListId() {
        return list.cardListID;
    }
}
