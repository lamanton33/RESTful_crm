package client.scenes.components;

import commons.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class CardComponentCtrl {

    @FXML
    private Label title;
    @FXML
    private Label description;

    /** Sets the details of a card */
    public void setCard(Card card) {
        title.setText(card.cardTitle);
        description.setText(card.cardDescription);
    }
}
