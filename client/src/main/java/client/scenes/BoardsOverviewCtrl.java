package client.scenes;

import client.*;
import client.components.*;
import client.utils.*;
import com.google.inject.*;
import commons.*;
import javafx.event.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.*;
import java.util.*;

public class BoardsOverviewCtrl {

    private final MultiboardCtrl multiboardCtrl;
    private final ConnectionCtrl connectionCtrl;
    private List<BoardCardPreviewCtrl> boardCardPreviewCtrls;
    private List<BoardComponentCtrl> boardComponentCtrls;

    private List<UUID> localBoards;
    private ServerUtils server;
    private MyFXML fxml;
    private SceneCtrl sceneCtrl;

    @FXML
    TextField connectionString;

    @FXML
    VBox box1;

    @FXML
    VBox box2;


    @Inject
    public BoardsOverviewCtrl(ServerUtils server, MyFXML fxml, SceneCtrl sceneCtrl, MultiboardCtrl multiboardCtrl,
                              ConnectionCtrl connectionCtrl) {
        this.server = server;
        this.fxml = fxml;
        this.sceneCtrl = sceneCtrl;
        this.multiboardCtrl = multiboardCtrl;
        this.connectionCtrl = connectionCtrl;
        this.boardCardPreviewCtrls = new ArrayList<>();
    }

    /**
     * triggers the admin login form window to open
     */
    public void adminLogin(){
        sceneCtrl.showAdminLoginPopup();
    }

    /**
     * @param boardID the id of the board to be deleted
     *                this method is called when a board is deleted
     *                it removes the preview controller of the board
     *                from the list of preview controllers
     */
    public void deleteBoard(UUID boardID) {
        boardCardPreviewCtrls.removeIf(boardCardPreviewCtrl -> boardCardPreviewCtrl.getBoardId().equals(boardID));
        multiboardCtrl.deleteBoard(boardID);
    }


    /**
     * @param board the board to be added to the list of boards
     *              this method is called when a board is updated
     */
    public void updateBoard(Board board) {
        for (BoardCardPreviewCtrl boardCardPreviewCtrl : boardCardPreviewCtrls) {
            if (boardCardPreviewCtrl.getBoardId().equals(board.boardID)) {
                boardCardPreviewCtrl.setBoard(board);
            }
        }
        Result<Board> res = server.updateBoard(board);
        if(res.success){
            loadAllBoards();
            loadPreviews();
        }
        else {
            sceneCtrl.showError(res.message, "Failed to update board");
        }
    }

    /** Tries to connect to the server filled in the text box and create a websocket,
     * f it fails it creates a popup showing the error */
    public void connectToServer(){
        if(connectionCtrl.connect(connectionString.getText()).equals(Result.SUCCESS)){
            loadAllBoards();
        };
    }


    /**
     *  loads the multiboard overview preview cards
     */
    public void loadPreviews() {

        box1.getChildren().clear();
        box2.getChildren().clear();
        for (int i = 0; i < localBoards.size(); i++) {
            if( i%2 == 0){
                Pair<BoardCardPreviewCtrl, Parent> boardPair = fxml.load(BoardCardPreviewCtrl.class, "client", "scenes",
                        "components", "BoardCardPreview.fxml");
                BoardCardPreviewCtrl boardCardPreviewCtrl = boardPair.getKey();
                boardCardPreviewCtrl.setContent(retrieveContent(localBoards.get(i)));
                box1.getChildren().add(boardPair.getValue());
                boardCardPreviewCtrls.add(boardCardPreviewCtrl);
            }else{
                Pair<BoardCardPreviewCtrl, Parent> boardPair = fxml.load(BoardCardPreviewCtrl.class, "client", "scenes",
                        "components", "BoardCardPreview.fxml");
                BoardCardPreviewCtrl boardCardPreviewCtrl = boardPair.getKey();
                boardCardPreviewCtrl.setContent(retrieveContent(localBoards.get(i)));
                box2.getChildren().add(boardPair.getValue());
                boardCardPreviewCtrls.add(boardCardPreviewCtrl);
            }
        }
    }

    /**
     * loads all boards from the local cache
     */
    public void loadAllBoards() {
        this.localBoards = multiboardCtrl.loadBoards();
        loadPreviews();
    }

    /**
     * @param actionEvent event that triggers the method
     *                    creates a new board and loads it into the multiboard
     */
    public void createBoard(ActionEvent actionEvent) {
        sceneCtrl.showCreateBoardPopup();
        loadAllBoards();
        loadPreviews();
    }


    /**
     * @param boardId the id of the board to be retrieved
     * @return the board with the given id
     */
    private Board retrieveContent(UUID boardId) {
        Result<Board> result = server.getBoard(boardId);
        if(result.success) {
            return result.value;
        }
        else {
            System.out.println("Failed to retrieve board");
        }
        return null;
    }
}
