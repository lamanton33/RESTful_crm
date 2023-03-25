package client.utils;

import client.SceneCtrl;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class DragController {

    private Stage primaryStage;

    private final ServerUtils server;

    private final SceneCtrl sceneCtrl;


    @FXML
    private Rectangle rect1;

    @FXML
    private Rectangle rect2;

    @FXML
    private Rectangle rect3;

    @FXML
    private Rectangle rect4;


    @Inject
    public DragController(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    MakeDraggable draggableMaker = new MakeDraggable();

    /**
     * Initializes sample Node to add draggability
     */
    public void initialize() {
        draggableMaker.makeDraggable(rect1);
    }



}
