package client.components;

import client.MyFXML;
import client.dataclass_controllers.Datasource;
import client.interfaces.UpdatableComponent;
import client.scenes.SceneCtrl;
import com.google.inject.Inject;
import commons.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class CardComponentCtrl implements UpdatableComponent {


    private MyFXML fxml;
    private Datasource datasource;
    private SceneCtrl sceneCtrl;
    @FXML
    private Label title;
    @FXML
    private Label description;
    private Card card;


    /** Initialises the controller using dependency injection */
    @Inject
    public CardComponentCtrl(SceneCtrl sceneCtrl, MyFXML fxml, Datasource datasource) {
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.datasource = datasource;
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

    @Override
    public void refresh() {
        System.out.println("Refreshing card with ID " + card.getCardID());
    }
}
