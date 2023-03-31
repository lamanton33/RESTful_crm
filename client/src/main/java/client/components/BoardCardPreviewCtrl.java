package client.components;

import client.SceneCtrl;
import client.utils.MyFXML;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;

public class BoardCardPreviewCtrl {

    private ServerUtils server;
    private MyFXML fxml;
    private SceneCtrl sceneCtrl;

    @FXML
    Label boardTitle;
    @FXML
    Label boardDescription;

    // replace with icon
    @FXML
    Label pwdProtected;


    @Inject
    public BoardCardPreviewCtrl() {
        this.sceneCtrl = sceneCtrl;
    }

}
