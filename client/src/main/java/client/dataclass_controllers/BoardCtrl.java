package client.dataclass_controllers;

import client.interfaces.UsesWebsockets;
import client.components.BoardComponentCtrl;
import com.google.inject.*;

import client.utils.ServerUtils;
import commons.*;
import javafx.scene.control.TableColumn;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
@Controller
public class BoardCtrl {

    private final ServerUtils server;

    private final BoardComponentCtrl boardComponentCtrl;
    private final Datasource datasource;

    private Board board;

    /** Initialises the controller using dependency injection */
    @Inject
    public BoardCtrl(ServerUtils server, Datasource datasource, BoardComponentCtrl boardComponentCtrl) {
        this.server = server;
        this.datasource = datasource;
        this.boardComponentCtrl = boardComponentCtrl;
    }


    public void registerForMessages(){
        //websockets init
        //TODO board ID
        server.registerForMessages("/topic/boards",Result.class, result ->{
            System.out.println("subscribing BoardCtrl to websocket");
            this.board = (Board) result.value;
            refresh();
        });
    }



    /**
     * Refreshes board view with updated data
     */
    public void refresh() {
        System.out.println(datasource.getBoard().toString());
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

    /**
     * Creates a new list and shows the updated list overview, takes in a title
     * @param listTitle
     */
    public void createList(String listTitle){
        CardList cardList = new CardList(listTitle, new ArrayList<>());
        Result<CardList> result = server.addList(cardList);
        board.addCardList(result.value);
    }
}