package client.scenes;

import client.SceneCtrl;
import client.components.BoardComponentCtrl;
import com.google.inject.Inject;
import commons.utils.IDGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddListCtrl {
    private final SceneCtrl sceneCtrl;
    private BoardComponentCtrl boardComponentCtrl;

    @FXML
    private TextField title;

    /** Initialises the controller using dependency injection */
    @Inject
    public AddListCtrl(SceneCtrl sceneCtrl, BoardComponentCtrl boardComponentCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.boardComponentCtrl = boardComponentCtrl;
    }

    /**
     * Creates list using the board controller and exits the scene
     */
    public void createList() {
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
}
