package client.scenes.dataclass_controllers;

import client.scenes.MainCtrl;
import client.scenes.components.CardComponentCtrl;
import client.utils.*;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
@Controller
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
     * Clears all fields
     */
    public void clearFields(){
        cardComponentCtrl.clear();
    }


}
