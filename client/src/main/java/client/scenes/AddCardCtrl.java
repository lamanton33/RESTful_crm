package client.scenes;

import client.scenes.dataclass_controllers.ListCtrl;
import com.google.inject.Inject;
import commons.Card;
import jakarta.ws.rs.WebApplicationException;

public class AddCardCtrl {


    private int listID;
    private final MainCtrl mainCtrl;
    private final ListCtrl listCtrl;

    @Inject
    public AddCardCtrl(MainCtrl mainCtrl, ListCtrl listCtrl) {
        this.mainCtrl = mainCtrl;
        this.listCtrl = listCtrl;
    }


    public int getCurrentListID() {
        return listID;
    }

    public void setListID(int listID) {
        this.listID = listID;
    }
    /**
     * Creates new card on the server
     * Accessed trough addCard view, must exit to board
     */
    public void createCard(){
        listCtrl.createCard(listID);
        mainCtrl.showBoard();
    }



    /**
     * Closes add task window
     */
    public void close(){
        mainCtrl.showBoard();
    }
}
