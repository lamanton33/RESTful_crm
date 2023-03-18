package client.scenes;

import client.utils.ServerUtils;
import commons.Card;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class AddCardCtrl {
    private final ServerUtils server;

    private final MainCtrl mainCtrl;

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
            server.addCard(getCard());
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

}
