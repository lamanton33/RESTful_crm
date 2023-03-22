package client.scenes.dataclass_controllers;

import client.scenes.MainCtrl;
import client.scenes.AddCardCtrl;
import client.utils.*;
import com.google.inject.*;
import commons.*;
import jakarta.ws.rs.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.util.*;
public class ListCtrl {

    private final BoardCtrl boardCtrl;
    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private TextField title;


    @Inject
    public ListCtrl(ServerUtils server, MainCtrl mainCtrl, BoardCtrl boardCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.boardCtrl = boardCtrl;
    }


    /** Adds a card to the list in the UI. It finds the list it's supposed to add it to and then adds it. */
    public void addCardToList(Card card, int listId) {
        for(CardList cardList:boardCtrl.getBoard().getCardListList()){
            if(cardList.getCardListID() == listId){
                cardList.addCard(card);
                boardCtrl.refresh();
            }
        }
        //TODO Show some error
    }

    /**
     * Creates new card on the server
     */
    public void createCard(int listId){
        Card newCard = new Card("");
        try {
            var result = server.addCardToList(newCard, listId);
            if (!result.success) {
                mainCtrl.showError(result.message, "Failed to create card");
            }
            addCardToList(newCard, listId);
        } catch (WebApplicationException e) {
            mainCtrl.showError(e.getMessage(), "Failed to create card");
            return;
        }
    }



}