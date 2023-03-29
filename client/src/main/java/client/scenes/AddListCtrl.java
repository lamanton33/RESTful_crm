package client.scenes;

import client.*;
import client.components.*;
import com.google.inject.*;
import javafx.fxml.*;
import javafx.scene.control.*;

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
