package client.components;

import client.utils.MyFXML;
import client.SceneCtrl;
import com.google.inject.Inject;
import commons.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class CardComponentCtrl{
    private MyFXML fxml;
    private SceneCtrl sceneCtrl;
    @FXML
    private Label title;
    @FXML
    private Label description;
    private Card card;


    /** Initialises the controller using dependency injection */
    @Inject
    public CardComponentCtrl(SceneCtrl sceneCtrl, MyFXML fxml) {
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
    }

    /** Sets the details of a card
     * @param card
     * */
    public void setCard(Card card) {
        this.card = card;
        title.setText(card.cardTitle);
        description.setText(card.cardDescription);
    }

    /**
     * Clears fields
     */
    public void clear() {
        title.setText("");
    }

}
