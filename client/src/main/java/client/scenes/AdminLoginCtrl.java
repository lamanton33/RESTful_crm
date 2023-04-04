package client.scenes;

import client.SceneCtrl;
import client.utils.ServerUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class AdminLoginCtrl {
    private final ServerUtils server;

    private final SceneCtrl sceneCtrl;

    @FXML
    private TextField password;
    @Inject
    public AdminLoginCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }


    /**
     *Method to login as admin in order to enable admin features
     */
    public void login() {
        // Send request to server to check password

    }


    /**
     * On click, the user is directed to the main page, the multiboard overview
     */
    public void close(ActionEvent actionEvent) {
        sceneCtrl.showMultiboard();
    }
}
