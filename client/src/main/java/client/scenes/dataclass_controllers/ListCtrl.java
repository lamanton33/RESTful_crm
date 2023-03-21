package client.scenes.dataclass_controllers;

import client.scenes.MainCtrl;
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

    private final List<CardCtrl> cardCtrls;

    @Inject
    public ListCtrl(ServerUtils server, MainCtrl mainCtrl, BoardCtrl boardCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.cardCtrls = new ArrayList<>();
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
     * Accessed trough addCard view, must exit to board
     */
    public void createCard(){

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

        clearFields();
        mainCtrl.showBoard();
    }

    /**
     * Cancels add new list operation, returns to overview
     */
    public void cancel(){
        clearFields();
        //Go to list overview
        mainCtrl.showBoard();
    }

    /**
     * Creates a new list and shows the updated list overview
     */
    public void createList(){
        try {
            var result = server.addList(getList());
            if (!result.success) {
                mainCtrl.showError(result.message, "Failed to Create List");
            }
            boardCtrl.getBoard().addCardList(result.value);
        } catch (WebApplicationException e) {
            mainCtrl.showError(e.getMessage(), "Failed to Create List");
        }

        clearFields();
        mainCtrl.showBoard();
    }


    /**
     * Clears all fields
     */
    public void clearFields(){
        title.clear();
    }

    /**
     * Returns a new list with the data in the scene
     */
    public CardList getList(){
        var listTitle = title.getText();
        List<Card> list = new ArrayList<>();
        CardList cardList = new CardList(listTitle, list);
        return cardList;
    }


}