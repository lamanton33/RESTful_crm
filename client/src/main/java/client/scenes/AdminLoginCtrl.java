package client.scenes;

import client.MultiboardCtrl;
import client.SceneCtrl;
import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.inject.Inject;

public class AdminLoginCtrl {
    private final ServerUtils server;

    private final SceneCtrl sceneCtrl;
    private static final String passwordTrue = "admin";

    @FXML
    private TextField password;

    public AdminLoginCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }


    /**
     *Method to login as admin in order to enable admin features
     */
    public void login(){
        if (password.getText().equals(passwordTrue)){
            //Triggers methods that will turn on admin features
        }
    }
}
