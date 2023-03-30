package client.scenes;

import client.utils.*;
import com.google.inject.*;
import commons.*;
import jakarta.ws.rs.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.util.*;
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
        try {
            var result = server.addList(getList());
            if (!result.success) {
                mainCtrl.showError(result.message, "Failed to Create List");
            }
            mainCtrl.addListToBoard(result.value);
        } catch (WebApplicationException e) {
            mainCtrl.showError(e.getMessage(), "Failed to Create List");
        }

        clearFields();
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
        //TODO add board id when multiboard is implemented
        return cardList;
    }
}