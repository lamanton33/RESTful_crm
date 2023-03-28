package client.scenes;

import client.SceneCtrl;
import client.components.BoardComponentCtrl;
import client.utils.*;
import commons.*;
import jakarta.ws.rs.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import javax.inject.Inject;
import java.util.UUID;

public class AddCardCtrl {
    private final ServerUtils server;

    private final SceneCtrl sceneCtrl;
    private final BoardComponentCtrl boardComponentCtrl;

    private UUID cardListId;

    @FXML
    private TextField titleOfCard;

    @FXML
    private TextArea description;

    @Inject

    public AddCardCtrl (ServerUtils server, SceneCtrl sceneCtrl, BoardComponentCtrl boardComponentCtrl){
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.boardComponentCtrl = boardComponentCtrl;
    }

    /**
     * Closes add task window
     */
    public void close(){
        sceneCtrl.showBoard();
    }

    /**
     * Gets the info of the card
     * @return instance of new Card with picked title and description
     */
    public Card getNewCard(){
        var titleVar = titleOfCard.getText();
        return new Card(titleVar);
    }

    /**
     * Creates new card on the server
     */
    public void createCard(){
        System.out.println("Creating card");
        try {
            var result = server.addCardToList(getNewCard(), cardListId);
            if (!result.success) {
                sceneCtrl.showError(result.message, "Failed to create card");
            }
            boardComponentCtrl.addCardToList(result.value, cardListId);
        } catch (WebApplicationException e) {
            sceneCtrl.showError(e.getMessage(), "Failed to create card");
            return;
        }

        clearFields();
        sceneCtrl.showBoard();
    }

    /**
     * Clears all fields
     */
    public void clearFields(){
        titleOfCard.clear();

    }

    /** Sets the id of the list to add the card to */
    public void setCurrentListID(UUID cardListID) {
        this.cardListId = cardListID;
    }
}
