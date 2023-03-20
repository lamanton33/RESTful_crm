package client.scenes.components;

import client.*;
import client.scenes.*;
import com.google.inject.*;
import commons.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class ListComponentCtrl {
    private CardList list;
    private MainCtrl mainCtrl;
    private MyFXML fxml;
    private List<CardComponentCtrl> cardControllers;

    @FXML
    private Label listName;
    @FXML
    private VBox cardContainer;

    @Inject
    public ListComponentCtrl(MainCtrl mainCtrl, MyFXML fxml) {
        this.mainCtrl = mainCtrl;
        this.fxml = fxml;
        cardControllers = new ArrayList<>();
    }

    /** Sets the data the list is supposed to display */
    public void setList(CardList list) {
        this.list = list;
        listName.setText(list.cardListTitle);

        var cardNodes = cardContainer.getChildren();
        cardNodes.remove(0, cardNodes.size()-1);
        for (var card : list.cardList) {
            addSingleCard(card);
        }
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
        ctrl.setCard(card);
        cardControllers.add(ctrl);
        cardNodes.add(cardNodes.size()-1, parent);
    }

    /**
     * Goes to add new card scene
     */
    public void addCard() {
        mainCtrl.showCardCreationPopup(list.cardListID);
    }

    /** Gets the id of the list controlled here */
    public int getListId() {
        return list.cardListID;
    }
}
