package client.scenes;

import client.utils.*;
import commons.*;
import jakarta.ws.rs.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import javax.inject.Inject;

public class AddCardCtrl {
    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private int cardListId;

    @FXML
    private TextField titleOfCard;

    @FXML
    private TextArea description;

    @Inject

    public AddCardCtrl (ServerUtils server, MainCtrl mainCtrl){
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Closes add task window
     */
    public void close(){
        mainCtrl.showListOverview();
    }

    /**
     * Gets the info of the card
     * @return instance of new Card with picked title and description
     */
    public Card getCard(){
        var titleVar = titleOfCard.getText();
        return new Card(titleVar);
    }

    /**
     * Creates new card on the server
     */
    public void createCard(){
        try {
            var result = server.addCardToList(getCard(), cardListId);
            if (!result.success) {
                mainCtrl.showError(result.message, "Failed to create card");
            }
            mainCtrl.addCardToList(result.value, cardListId);
        } catch (WebApplicationException e) {
            mainCtrl.showError(e.getMessage(), "Failed to create card");
            return;
        }

        clearFields();
        mainCtrl.showListOverview();
    }

    /**
     * Clears all fields
     */
    public void clearFields(){
        titleOfCard.clear();

    }

    /** Sets the id of the list to add the card to */
    public void setListId(int cardListID) {
        this.cardListId = cardListID;
    }
}
