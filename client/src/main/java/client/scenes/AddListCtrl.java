package client.scenes;

import com.google.inject.Inject;

import client.utils.ServerUtils;
import commons.Card;
import commons.CardList;
import jakarta.ws.rs.WebApplicationException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;

import java.util.ArrayList;
import java.util.List;
public class AddListCtrl {

    private final ServerUtils server;

    private final MainCtrl mainCtrl;

    @FXML
    private TextField title;

    @Inject
    public AddListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Cancels add new list operation, returns to overview
     */
    public void cancel(){
        clearFields();
        //Go to list overview
        mainCtrl.showListOverview();
    }

    /**
     * Creates a new list and shows the updated list overview
     */
    public void create(){
        CardList list = null;
        try {
            list = server.addList(getList());  //should be result and not list
        } catch (WebApplicationException e) {
            mainCtrl.showError(e.getMessage(), "Failed to Create List");
        }

        clearFields();
        mainCtrl.addListToBoard(list);
        mainCtrl.showListOverview();
    }


    /**
     * Clears all fields
     */
    public void clearFields(){
        title.clear();
    }

    /**
     * Returns a new list with the data in the scene
     */
    public CardList getList(){
        var listTitle = title.getText();
        List<Card> list = new ArrayList<>();
        CardList cardList = new CardList(listTitle, list);
        return cardList;
    }
}