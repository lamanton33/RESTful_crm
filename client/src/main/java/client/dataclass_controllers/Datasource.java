package client.dataclass_controllers;

import client.components.BoardComponentCtrl;
import client.components.ListComponentCtrl;
import client.scenes.SceneCtrl;
import client.utils.ServerUtils;
import commons.Board;
import commons.Result;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;

@Controller
public class Datasource {

    private Board board;

//    private ServerUtils server;
//    private BoardCtrl boardCtrl;
//
//    @Inject
//    public Datasource(ServerUtils server, BoardCtrl boardCtrl){
//        this.server = server;
//        this.boardCtrl = boardCtrl;
//    }
//
//    public void registerForMessages(){
//        //websockets init
//        //TODO board ID
//        server.registerForMessages("/topic/boards", Result.class, result ->{
//            System.out.println("subscribing BoardCtrl to websocket");
//            this.board = (Board) result.value;
//            boardCtrl.refresh();
//        });
//    }

    /** Returns most up-to-date board available on the client
     *  Use a dummy board for debugging the client without server
     * @return board
     */
    public Board getBoard(){
        if(this.board == null){
            this.board = Board.createDummyBoard();
        }
        return this.board;
    }
}
