package client.scenes.components;

import commons.CardList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ListComponentCtrl {
    private CardList list;

    @FXML
    private Label listName;

    /** Sets the data the list is supposed to display */
    public void setList(CardList list) {
        this.list = list;
        listName.setText(list.cardListTitle);
        //TODO add cards programmatically here
    }
}
