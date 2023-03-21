package client.scenes.components;

import client.MyFXML;
import client.scenes.MainCtrl;
import client.scenes.dataclasscontrollers.BoardCtrl;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.CardList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BoardComponentCtrl {
    private final MyFXML fxml;
    private BoardCtrl boardCtrl;
    private MainCtrl mainCtrl;
    @FXML
    private Label title;
    @FXML
    private Label description;

    @FXML
    private HBox hBoxContainer;


    @Inject
    public BoardComponentCtrl(MainCtrl mainCtrl, MyFXML fxml,BoardCtrl boardCtrl) {
        this.mainCtrl = mainCtrl;
        this.boardCtrl = boardCtrl;
        this.fxml = fxml;

    }


    /** Sets the details of a board */
    public void setBoard(Board board) {
        title.setText(board.boardTitle);
        description.setText(board.description);
    }

    /** Adds a single list to the end of board */
    public void addSingleList(CardList list) {
        if (list == null) {
            return;
        }

        var listNodes = hBoxContainer.getChildren();
        var component = fxml.load(ListComponentCtrl.class, "client", "scenes", "components", "ListComponent.fxml");
        var parent = component.getValue();
        //var ctrl = component.getKey();
        //ctrl.getListCtrl().setList(list);
        //listComponentCtrls.add(ctrl);
        listNodes.add(listNodes.size()-1, parent);
    }

    /**
     * Refreshes overview with updated data
     */
    public void refresh() {
        var cardLists = boardCtrl.getBoard().getCardList();
        var listNodes = hBoxContainer.getChildren();
        listNodes.remove(0, listNodes.size()-1);

        for (var list : cardLists) {
            addSingleList(list);
        }
    }

}
