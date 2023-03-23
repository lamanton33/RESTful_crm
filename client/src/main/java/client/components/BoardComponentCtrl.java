package client.components;

import client.MyFXML;
import client.dataclass_controllers.Datasource;
import client.interfaces.UpdatableComponent;
import client.scenes.SceneCtrl;
import com.google.inject.Inject;
import commons.Board;
import commons.CardList;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

import javax.security.auth.Refreshable;
import java.util.List;

public class BoardComponentCtrl implements UpdatableComponent {

    private MyFXML fxml;
    private Datasource datasource;
    private SceneCtrl sceneCtrl;

    @FXML
    private Label boardTitle = new Label();
    @FXML
    private Label boardDescription = new Label();

    @FXML
    private HBox hBoxContainer;

    /** Initialises the controller using dependency injection */
    @Inject
    public BoardComponentCtrl(SceneCtrl sceneCtrl, MyFXML fxml, Datasource datasource) {
        this.sceneCtrl = sceneCtrl;
        this.fxml = fxml;
        this.datasource = datasource;
    }


    /**
     * Refreshes the board with up-to-date data, propagates trough ListComponentCtrl
     */
    public void refresh() {
        Board board = datasource.getBoard();
        System.out.println("Refreshing board with ID " + board.getBoardID());
        boardTitle.setText(board.boardTitle);
        boardDescription.setText(board.description);

        List<CardList> cardListLists = board.getCardListList();
        ObservableList<Node> listNodes = this.hBoxContainer.getChildren();
        listNodes.remove(0, listNodes.size()-1);

        System.out.println("List board " + cardListLists.size() + " lists");
        for (CardList list : cardListLists) {
            addList(list);
        }
    }

    /** Adds a single list to the end of board
     * @param list the list that gets added to the board
     * */
    public void addList(CardList list) {
        ObservableList<Node> listNodes = hBoxContainer.getChildren();
        Pair<ListComponentCtrl, Parent> component = fxml.load(
                ListComponentCtrl.class, "client", "scenes", "components", "ListComponent.fxml");
        Parent parent = component.getValue();
        ListComponentCtrl listComponentCtrl = component.getKey();
        listComponentCtrl.setListID(list.getListID());
        listComponentCtrl.refresh();
        listNodes.add(listNodes.size()-1, parent);
    }

    /**
     * Goes to add new list scene
     */
    public void openAddListScene() {
        sceneCtrl.showAddList();
    }
}
