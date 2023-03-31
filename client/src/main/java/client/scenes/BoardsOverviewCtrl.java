package client.scenes;

import client.SceneCtrl;
import client.components.BoardCardPreviewCtrl;
import client.utils.MyFXML;
import client.utils.ServerUtils;
import javafx.fxml.FXML;

import java.awt.*;
import java.util.List;

public class BoardsOverviewCtrl {

    private List<BoardCardPreviewCtrl> boardCardPreviewCtrls;

    private ServerUtils server;

    private MyFXML fxml;
    private SceneCtrl sceneCtrl;

    @FXML
    Button button;
    @FXML
    TextField textField;




}
