package client.scenes;

import client.MultiboardCtrl;
import client.SceneCtrl;
import client.components.BoardComponentCtrl;
import client.utils.*;
import com.google.inject.*;
import commons.*;
import jakarta.ws.rs.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddListCtrl {
    private final SceneCtrl sceneCtrl;
    private MultiboardCtrl multiboardCtrl;
    private UUID boardID;
    @FXML
    private TextField title;

    /** Initialises the controller using dependency injection */
    @Inject
    public AddListCtrl(SceneCtrl sceneCtrl, MultiboardCtrl multiboardCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.multiboardCtrl = multiboardCtrl;
    }

    /**
     * Creates list using the board controller and exits the scene
     */
    public void createList() {
        BoardComponentCtrl boardComponentCtrl = multiboardCtrl.getBoardController(boardID);
        boardComponentCtrl.createList(title.getText());
        cancel();
    }

    /**
     * Clears all fields
     */
    public void clearFields(){
        title.clear();
    }

    /**
     * Exits the scene
     */
    public void cancel() {
        clearFields();
        sceneCtrl.showBoard();
    }

    /** Setter for boardId
     * @param boardID
     */
    public void setBoardID(UUID boardID) {
        this.boardID = boardID;
    }

    /** Getter for list
     * @return cardlist
     */
    public CardList getList(){
        var listTitle = title.getText();
        List<Card> list = new ArrayList<>();
        CardList cardList = new CardList(listTitle, list);
        return cardList;
    }
}
