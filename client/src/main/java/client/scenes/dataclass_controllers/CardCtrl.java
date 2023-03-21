package client.scenes.dataclass_controllers;

import client.scenes.MainCtrl;
import client.scenes.components.CardComponentCtrl;
import client.utils.*;

import javax.inject.Inject;

public class CardCtrl {
    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    private final CardComponentCtrl cardComponentCtrl;

    @Inject
    public CardCtrl(ServerUtils server, MainCtrl mainCtrl,CardComponentCtrl cardComponentCtrl){
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.cardComponentCtrl = cardComponentCtrl;
    }

    /**
     * Closes add task window
     */
    public void close(){
        mainCtrl.showBoard();
    }


    /**
     * Clears all fields
     */
    public void clearFields(){
        cardComponentCtrl.clear();
    }


}
