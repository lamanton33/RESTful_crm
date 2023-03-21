package client.scenes.components;

import client.*;
import client.scenes.MainCtrl;
import client.scenes.dataclasscontrollers.CardCtrl;
import client.scenes.dataclasscontrollers.ListCtrl;
import com.google.inject.*;
import commons.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class ListComponentCtrl {
    private MainCtrl mainCtrl;
    private ListCtrl listCtrl;
    private MyFXML fxml;

    @FXML
    private Label listName;
    @FXML
    private VBox cardContainer;

    @Inject
    public ListComponentCtrl(MainCtrl mainCtrl, MyFXML fxml) {
        this.mainCtrl = mainCtrl;
        this.fxml = fxml;
    }

    /** Adds a single list to the board */
    public void addSingleCard(Card card) {
        if (card == null) {
            return;
        }

        var cardNodes = cardContainer.getChildren();
        var component = fxml.load(CardComponentCtrl.class, "client", "scenes", "components", "CardComponent.fxml");
        var parent = component.getValue();
        var ctrl = component.getKey();
        //listCtrl.addCardCtrl(ctrl);
        cardNodes.add(cardNodes.size()-1, parent);
    }

    /**
     * Goes to add new card scene
     */
    public void addCard() {
        mainCtrl.showCardCreationPopup();
    }

    public ListCtrl getListCtrl() {
        return listCtrl;
    }

//    /** Gets the id of the list controlled here */
//    public int getListId() {
//        return list.cardListID;
//    }
}
