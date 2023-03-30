package client.scenes;

import client.*;
import client.utils.ServerUtils;

import javax.inject.Inject;

public class BoardOverviewCtrl {

    private final ServerUtils server;

    private final SceneCtrl sceneCtrl;

    public BoardOverviewCtrl(ServerUtils server, SceneCtrl sceneCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
    }

    @Inject

    /**
     * triggers the admin login form window to open
     */
    public void adminLogin(){
        sceneCtrl.showAdminLoginPopup();
    }


}
