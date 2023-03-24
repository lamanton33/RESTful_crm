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

    private ServerUtils server;

    @Inject
    public Datasource(ServerUtils server){
        this.server = server;
    }
    public void registerForMessages(){
        //websockets init
        //TODO board ID
        server.registerForMessages("/topic/boards",Result.class, result ->{
            System.out.println("subscribing BoardCtrl to websocket");
            this.board = (Board) result.value;;
        });
    }

    /** Returns most up-to-date board available on the client
     *  Use a dummy board for debugging the client without server
     * @return board
     */
    public Board getBoard(){
        System.out.println("Retrieving board");
        server.send("/app/boards/get-dummy-board", null);
        return this.board;
    }
}
