package client.scenes.components;

import client.*;
import client.scenes.MainCtrl;
import client.scenes.dataclass_controllers.BoardCtrl;
import com.google.inject.*;
import commons.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ListComponentCtrl {
    private MainCtrl mainCtrl;
    private BoardCtrl boardCtrl;
    private MyFXML fxml;

    private int listID;

    @FXML
    private Label listName;
    @FXML
    private VBox cardContainer;

    @Inject
    public ListComponentCtrl(MainCtrl mainCtrl, MyFXML fxml, BoardCtrl boardCtrl) {
        this.mainCtrl = mainCtrl;
        this.fxml = fxml;
        this.boardCtrl = boardCtrl;
    }

    /** Adds a single list to the board */
    public void addSingleCard(Card card) {
        if (card == null) {
            return;
        }

        var cardNodes = cardContainer.getChildren();
        var component = fxml.load(CardComponentCtrl.class, "client", "scenes", "components", "CardComponent.fxml");
        var parent = component.getValue();
        var ctrl = component.getKey();
        //listCtrl.addCardCtrl(ctrl);
        cardNodes.add(cardNodes.size()-1, parent);
    }

    /**
     * Goes to add new card scene
     */
    public void addCardPopUp() {
        mainCtrl.showCardCreationPopup(listID);
    }

//    public ListCtrl getListCtrl() {
//        return listCtrl;
//    }

    public void refresh() {
        var cards = cardContainer.getChildren();
        cards.remove(0, cards.size()-1);
        for (CardList cardList : boardCtrl.getBoard().getCardListList()) {
            if(cardList.getCardListID() == this.listID){
                addAllCards(cardList);
                break;
            }
        }
    }

    private void addAllCards(CardList cardList) {
        for(Card card: cardList.getCardList()){
            addSingleCard(card);
        }
    }

//    /** Gets the id of the list controlled here */
//    public int getListId() {
//        return list.cardListID;
//    }
}
