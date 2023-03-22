package client.scenes.dataclass_controllers;

import client.scenes.MainCtrl;
import client.scenes.interfaces.UsesWebsockets;
import client.scenes.components.BoardComponentCtrl;
import com.google.inject.*;

import client.utils.ServerUtils;
import commons.*;
import jakarta.ws.rs.WebApplicationException;
import javafx.scene.control.TableColumn;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
@Controller
public class BoardCtrl implements UsesWebsockets {

    private final ServerUtils server;
    private final BoardComponentCtrl boardComponentCtrl;

    private final MainCtrl mainCtrl;

    private Board board;


    @Inject
    public BoardCtrl(ServerUtils server, MainCtrl mainCtrl, BoardComponentCtrl boardComponentCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.boardComponentCtrl = boardComponentCtrl;
        board = new Board();

    }

    @Override
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

    /**
     * Creates a new list and shows the updated list overview
     */
    public void createList(String listTitle){
        List<Card> list = new ArrayList<>();
        CardList cardList = new CardList(listTitle, list);
        try {
            var result = server.addList(cardList);
            if (!result.success) {
                mainCtrl.showError(result.message, "Failed to Create List");
            }
            board.addCardList(result.value);
        } catch (WebApplicationException e) {
            mainCtrl.showError(e.getMessage(), "Failed to Create List");
        }
    }


    public Board getBoard(){
        server.send("topic/boards/get-dummy-board", null);
        System.out.println("Sending board");
        return Board.createDummyBoard();
    }
}