package client.components;

import client.*;
import client.dataclass_controllers.Datasource;
import client.interfaces.UpdatableComponent;
import client.scenes.SceneCtrl;
import client.dataclass_controllers.BoardCtrl;
import com.google.inject.*;
import commons.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Pair;

import java.util.List;

public class ListComponentCtrl implements UpdatableComponent {
    private MyFXML fxml;
    private Datasource datasource;
    private SceneCtrl sceneCtrl;

    private List<CardList> cardList;

    private int listID;

    @FXML
    private Label listName;
    @FXML
    private VBox cardContainer;

    @Inject
    public ListComponentCtrl(SceneCtrl sceneCtrl, MyFXML fxml, Datasource datasource) {
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.datasource = datasource;
    }

    /** Setter for list id
     * @param listID
     */
    public void setListID(int listID) {
        this.listID = listID;
    }

    /**
     * Refreshes the list with up-to-date data, propagates trough CardComponentCtrl
     */
    public void refresh() {
        this.cardList = datasource.getBoard().getCardListList();
        var cards = cardContainer.getChildren();
        cards.remove(0, cards.size()-1);
        for (CardList cardList : cardList) {
            if(cardList.getListID() == this.listID){
                System.out.println("Refreshing list with ID " + cardList.getListID());
                this.listName.setText(cardList.getListTitle());
                for(Card card: cardList.getList()){
                    addSingleCard(card);
                }
            }
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
        cardNodes.add(cardNodes.size()-1, parent);
    }

    /**
     * Goes to add new card scene
     */
    public void addCardPopUp() {
        sceneCtrl.showCardCreationPopup(listID);
    }
}
