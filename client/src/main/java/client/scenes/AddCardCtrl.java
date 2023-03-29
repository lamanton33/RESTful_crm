package client.scenes;

import client.MultiboardCtrl;
import client.SceneCtrl;
import client.components.BoardComponentCtrl;
import client.utils.*;
import commons.*;
import commons.utils.IDGenerator;
import jakarta.ws.rs.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.UUID;

public class AddCardCtrl {
    private final ServerUtils server;

    private final SceneCtrl sceneCtrl;
    private final BoardComponentCtrl boardComponentCtrl;
    private final IDGenerator idGenerator;
    private final MultiboardCtrl multiboardCtrl;


    @FXML
    private TextField titleOfCard;

    @FXML
    private TextArea description;
    private CardList cardList;

    @Inject

    public AddCardCtrl (ServerUtils server, SceneCtrl sceneCtrl, BoardComponentCtrl boardComponentCtrl, IDGenerator idGenerator, MultiboardCtrl multiboardCtrl){
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.boardComponentCtrl = boardComponentCtrl;
        this.idGenerator = idGenerator;
        this.multiboardCtrl = multiboardCtrl;
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
        var cardTitle = titleOfCard.getText();
        return new Card(idGenerator.generateID(),cardList,cardTitle, "Add an description", new ArrayList<Task>() , new ArrayList<Tag>());
    }

    /**
     * Creates new card on the server
     */
    public void createCard(){
        Card newCard = getNewCard();
        System.out.println("Creating a card with id\t" + newCard.cardID + "\tin list\t" + cardList.cardListId);
        try {
            var result = server.addCardToList(newCard, cardList.cardListId);
            if (!result.success) {
                sceneCtrl.showError(result.message, "Failed to create card");
            }

            multiboardCtrl.getBoardController(newCard.cardList.boardId).addCardToList(result.value, cardList.cardListId);

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
    public void setCurrentList(CardList cardList) {
        this.cardList = cardList;

    }
}
