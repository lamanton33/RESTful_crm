package client.scenes;

import client.MultiboardCtrl;
import client.SceneCtrl;
import client.components.BoardComponentCtrl;
import com.google.inject.Inject;
import commons.Board;
import commons.utils.IDGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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

    public void setBoardID(UUID boardID) {
        this.boardID = boardID;
    }
}
