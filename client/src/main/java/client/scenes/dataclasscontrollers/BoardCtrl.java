package client.scenes.dataclasscontrollers;

import client.*;
import client.scenes.MainCtrl;
import client.scenes.components.BoardComponentCtrl;
import client.scenes.components.ListComponentCtrl;
import com.google.inject.*;

import client.utils.ServerUtils;
import commons.*;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;

import java.util.*;

public class BoardCtrl {

    private final ServerUtils server;
    private final BoardComponentCtrl boardComponentCtrl;


    private final MainCtrl mainCtrl;
    private final ListCtrl listCtrl;
    private List<CardList> listList;



    private Board board;


    @Inject
    public BoardCtrl(ServerUtils server, MainCtrl mainCtrl, BoardComponentCtrl boardComponentCtrl, ListCtrl listCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.boardComponentCtrl = boardComponentCtrl;
        this.listCtrl = listCtrl;
        board = new Board();
        //websockets init
        server.registerForMessages("/topic/board",Result.class, result ->{
            this.board = (Board) result.value;
            refresh();
        });
    }

    /**
     * Goes to add new list scene
     */
    public void addList() {
        mainCtrl.showAddList();
    }

    /**
     * Refreshes overview with updated data
     */
    public void refresh() {
        this.board = getBoard();
        boardComponentCtrl.refresh();
    }

    /**
     * Deletes list from the overview
     */
    public void delete() {
//        Integer deletingIdx = table.getSelectionModel().getSelectedItem().cardListID;
//        if (deletingIdx != null) {
//            server.deleteList(deletingIdx);
//            table.getItems().remove(deletingIdx);
//            refresh();
//        }
    }

    /**
     * Edits the name on the list by clicking on it
     */
    public void editChange(TableColumn.CellEditEvent<CardList, String> cardListStringCellEditEvent) {
//        CardList list = table.getSelectionModel().getSelectedItem();
//        list.setCardListTitle(cardListStringCellEditEvent.getNewValue());
//        server.editList(list, list.cardListID);
    }


    public Board getBoard() {
        return board;
    }
}