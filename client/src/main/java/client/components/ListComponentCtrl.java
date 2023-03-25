package client.components;

import client.SceneCtrl;
import client.utils.MyFXML;
import com.google.inject.*;
import commons.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class ListComponentCtrl {
    private final MyFXML fxml;
    private SceneCtrl sceneCtrl;

    private List<CardList> cardList;

    private int listID;

    @FXML
    private Label listName;
    @FXML
    private VBox cardContainer;

    private List<CardComponentCtrl> cardComponentCtrls;

    @Inject
    public ListComponentCtrl(SceneCtrl sceneCtrl, MyFXML fxml) {
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.cardComponentCtrls = new ArrayList<>();
    }

    /** Setter for list id
     * @param listID
     */
    public void setListID(int listID) {
        this.listID = listID;
    }

    /** Getter for list id
     * @return listID
     */
    public int getListId() {
        return listID;
    }

    /**
     * Refreshes the list with up-to-date data, propagates trough CardComponentCtrl
     */
    public void setList(CardList cardList) {
        var cards = cardContainer.getChildren();
        setListID(cardList.getListID());
        cards.remove(0, cards.size()-1);
        System.out.println("Refreshing list with ID " + listID);
        this.listName.setText(cardList.getListTitle());
        for(Card card: cardList.getList()){
            addSingleCard(card);
        }
    }

    /** Adds a single card to the board
     * @param card that gets added
     * */
    public void addSingleCard(Card card) {
        ObservableList<Node> cardNodes = cardContainer.getChildren();
        Pair<CardComponentCtrl, Parent> component = fxml.load(
                CardComponentCtrl.class, "client", "scenes", "components", "CardComponent.fxml");
        Parent parent = component.getValue();
        CardComponentCtrl cardComponentCtrl = component.getKey();
        cardComponentCtrl.setCard(card);
        cardComponentCtrls.add(cardComponentCtrl);
        cardNodes.add(cardNodes.size()-1, parent);
    }

    /**
     * Goes to add new card scene
     */
    public void addCardPopUp() {
        sceneCtrl.showCardCreationPopup(listID);
    }
}
