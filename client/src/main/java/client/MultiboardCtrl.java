package client;

import client.components.BoardComponentCtrl;
import client.utils.MyFXML;
import com.google.inject.Inject;
import javafx.scene.Parent;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

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
        Pair<BoardComponentCtrl, Parent> boardPair = fxml.load(BoardComponentCtrl.class, "client", "scenes",
                "Board.fxml");
        this.boardComponentPair = boardPair;
        this.boardComponentPairs.add(boardPair);
        return boardPair;
    }

}
