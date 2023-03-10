package client.scenes;

import client.utils.MakeDraggable;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;


public class DragController {

    private Stage primaryStage;

    private final ServerUtils server;

    private final MainCtrl mainCtrl;


    @FXML
    private Rectangle rect1;

    @FXML
    private Rectangle rect2;

    @FXML
    private Rectangle rect3;

    @FXML
    private Rectangle rect4;


    @Inject
    public DragController(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    MakeDraggable draggableMaker = new MakeDraggable();

    public void initialize1() {
        draggableMaker.makeDraggable(rect1);
    }



}
