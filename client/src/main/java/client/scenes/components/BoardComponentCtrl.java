package client.scenes.components;

import client.MyFXML;
import client.scenes.AddListCtrl;
import client.scenes.MainCtrl;
import client.scenes.dataclass_controllers.BoardCtrl;
import com.google.inject.Inject;
import commons.Board;
import commons.CardList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BoardComponentCtrl {
    private final MyFXML fxml;

    private MainCtrl mainCtrl;

    @FXML
    private Label title;
    @FXML
    private Label description;

    @FXML
    private HBox hBoxContainer;


    @Inject
    public BoardComponentCtrl(MyFXML fxml,MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.fxml = fxml;
    }


    /** Sets the details of a board */
    public void setBoard(Board board) {
        title.setText(board.boardTitle);
        description.setText(board.description);
    }

    /** Adds a single list to the end of board */
    public void addList(CardList list) {
        var listNodes = hBoxContainer.getChildren();
        var component = fxml.load(ListComponentCtrl.class, "client", "scenes", "components", "ListComponent.fxml");
        var parent = component.getValue();
        ListComponentCtrl listComponentCtrl = component.getKey();
        listComponentCtrl.refresh();
        //ctrl.getListCtrl().setList(list);
        //listComponentCtrls.add(ctrl);
        listNodes.add(listNodes.size()-1, parent);
    }

    /**
     * Refreshes overview with updated data
     */
    public void refresh() {
        System.out.println("refreshing board");
        var cardLists = mainCtrl.getBoard().getCardListList();
        var listNodes = hBoxContainer.getChildren();

        listNodes.remove(0, listNodes.size()-1);

        for (var list : cardLists) {
            addList(list);
        }
    }

    /**
     * Goes to add new list scene
     */
    public void openAddListScene() {
        mainCtrl.showAddList();
    }
}
