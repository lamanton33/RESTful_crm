package client.scenes;

import client.scenes.dataclass_controllers.BoardCtrl;
import client.scenes.dataclass_controllers.ListCtrl;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddListCtrl {
    private final MainCtrl mainCtrl;
    private int listID;
    //TODO the listID should not be parked here, but rather directly passed trough somehow

    private BoardCtrl boardCtrl;
    @FXML
    private TextField title;


    @Inject
    public AddListCtrl(MainCtrl mainCtrl, BoardCtrl boardCtrl) {
        this.mainCtrl = mainCtrl;
        this.boardCtrl = boardCtrl;

    }


    public int getCurrentListID() {
        return listID;
    }

    public void setCurrentListID(int listID) {
        this.listID = listID;
    }

    public void createList() {
        boardCtrl.createList(title.getText());
        cancel();
    }

    /**
     * Clears all fields
     */
    public void clearFields(){
        title.clear();
    }
    public void cancel() {
        clearFields();
        mainCtrl.showBoard();
    }
}
