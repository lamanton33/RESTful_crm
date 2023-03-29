package client;

import client.components.BoardComponentCtrl;
import client.utils.MyFXML;
import com.google.inject.Inject;
import javafx.scene.Parent;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MultiboardCtrl {

    private MyFXML fxml;
    private List<Pair<BoardComponentCtrl, Parent>>  boardComponentPairs;

    private Pair<BoardComponentCtrl, Parent>  boardComponentPair;

    public MultiboardCtrl() {
    }

    @Inject
    public MultiboardCtrl(MyFXML fxml) {
        this.fxml = fxml;
        this.boardComponentPairs = new ArrayList<>();
    }

    /**
     * @return boardOverviewFXMLObject Pair<BoardComponentCtrl, Parent>
     */
    public Pair<BoardComponentCtrl, Parent> createBoard(){
        this.boardComponentPair = fxml.load(
                BoardComponentCtrl.class, "client", "scenes", "components", "BoardComponent.fxml");
        this.boardComponentPairs.add(boardComponentPair);
        BoardComponentCtrl boardComponentCtrl = boardComponentPair.getKey();
        boardComponentCtrl.initializeBoard();
        return boardComponentPair;
    }
    /**
     * @return boardOverviewFXMLObject Pair<BoardComponentCtrl, Parent>
     */
    public Pair<BoardComponentCtrl, Parent> openBoard(UUID boardId){
        Pair<BoardComponentCtrl, Parent> boardPair = fxml.load(BoardComponentCtrl.class, "client", "scenes",
                "components", "BoardComponent.fxml");
        this.boardComponentPair = boardPair;
        this.boardComponentPairs.add(boardPair);
        BoardComponentCtrl boardComponentCtrl = boardPair.getKey();
        boardComponentCtrl.setBoard(boardId);
        return boardPair;
    }


    /**Getter for the boardComponentCtrl
     * @param boardID
     * @return
     */
    public BoardComponentCtrl getBoardController(UUID boardID) {
        for(Pair<BoardComponentCtrl, Parent> boardPair: boardComponentPairs){
            if(boardPair.getKey().getBoardID().equals(boardID)){
                return boardPair.getKey();
            }
        }
        return null;
    }
}
