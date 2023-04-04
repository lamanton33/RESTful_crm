package client.components;

import client.*;
import client.utils.*;
import com.google.inject.*;
import commons.*;
import commons.utils.*;
import javafx.beans.value.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.*;

public class NewCardComponentCtrl {
    private final ServerUtils server;
    private final IDGenerator idGenerator;
    private final SceneCtrl sceneCtrl;
    private final ChangeListener<Boolean> focusChangeListener;

    private CardList cardList;

    @FXML
    private TextField title;
    @FXML
    private Pane cardPane;

    @Inject
    public NewCardComponentCtrl(ServerUtils server, IDGenerator idGenerator, SceneCtrl sceneCtrl) {
        this.server = server;
        this.idGenerator = idGenerator;
        this.sceneCtrl = sceneCtrl;
        this.focusChangeListener = (observable, oldFocus, newFocus) -> {
            if (!newFocus) {
                createCard(title.getText());
            }};
    }

    /** Sets a listener on the focused property. This way we know the user has stopped typing */
    @FXML
    public void initialize() {
        title.focusedProperty().addListener(focusChangeListener);
    }

    /** The onAction listener. When the user presses enter this activates */
    public void action() {
        title.focusedProperty().removeListener(focusChangeListener);
        createCard(title.getText());
    }

    /** Sets the focus on the text box so that the user can type right away.
     * When the card is the first in the list it doesn't work. */
    public void setFocused() {
        title.requestFocus();   //this only works some of the time for some reason, sometimes you just have to click...
    }

    /** Gets called when the text box loses focus or the user presses enter.
     * Creates the card. */
    public void createCard(String title) {
        System.out.println("text changed: " + title);
        var card = new Card(
                idGenerator.generateID(),
                cardList,
                title,
                "",
                new ArrayList<>(),
                new ArrayList<>()
        );
        var result = server.addCardToList(card, cardList.cardListId);
        if (!result.success) {
            sceneCtrl.showError(result.message, "Failed to create card");
        }
    }

    /** Sets the list the card should be added to. */
    public void setList(CardList cardList) {
        this.cardList = cardList;
    }
}
