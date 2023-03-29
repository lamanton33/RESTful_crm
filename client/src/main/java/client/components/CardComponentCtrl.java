package client.components;

import client.SceneCtrl;
import client.interfaces.InstanceableComponent;
import client.utils.MyFXML;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Card;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.UUID;

public class CardComponentCtrl implements InstanceableComponent {
    private ServerUtils server;
    private MyFXML fxml;
    private SceneCtrl sceneCtrl;
    @FXML
    private Label title;
    @FXML
    private Label description;
    private Card card;

    /** Initialises the controller using dependency injection */
    @Inject
    public CardComponentCtrl(ServerUtils server, SceneCtrl sceneCtrl, MyFXML fxml) {
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.server = server;
    }

    @Override
    public void registerForMessages(){
        server.registerForMessages("/topic/update-card", UUID.class, payload ->{
            try {
                if(payload.equals(card.getCardID())){
                    // Needed to prevent threading issues
                    Platform.runLater(() -> refresh());
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
                }
            }
        );
    }

    @Override
    public void refresh() {

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

    /** Setter for card
     * @param cardId
     */
    public void setCardId(UUID cardId) {
        this.card.setCardID(cardId);
    }
}
