package client.scenes.components;

import commons.CardList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ListComponentCtrl {
    private CardList data;

    @FXML
    private Label listName;
    /**
     * Initializes a new list that can be appended to the board
     */
    public ListComponentCtrl(CardList cardList) {
        this.listName = new Label(cardList.cardListTitle);
    }


}
