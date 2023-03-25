package client.dataclass_controllers;

import client.SceneCtrl;
import client.components.ListComponentCtrl;
import client.utils.*;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
@Controller
public class CardCtrl {
    private final ServerUtils server;
    private final SceneCtrl sceneCtrl;

    private final ListComponentCtrl listComponentCtrl;

    /** Initialises the controller using dependency injection */
    @Inject
    public CardCtrl(ServerUtils server, SceneCtrl sceneCtrl, ListComponentCtrl listComponentCtrl){
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.listComponentCtrl = listComponentCtrl;
    }
}
