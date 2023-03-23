package client.scenes;

import client.dataclass_controllers.BoardCtrl;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddListCtrl {
    private final SceneCtrl sceneCtrl;
    private BoardCtrl boardCtrl;

    @FXML
    private TextField title;

    /** Initialises the controller using dependency injection */
    @Inject
    public AddListCtrl(SceneCtrl sceneCtrl, BoardCtrl boardCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.boardCtrl = boardCtrl;

    }

    /**
     * Creates list using the board controller and exits the scene
     */
    public void createList() {
        boardCtrl.createList(title.getText());
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
