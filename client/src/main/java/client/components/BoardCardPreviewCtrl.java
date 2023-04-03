package client.components;

import client.MultiboardCtrl;
import client.SceneCtrl;
import client.scenes.BoardsOverviewCtrl;
import client.utils.MyFXML;
import client.utils.ServerUtils;
import commons.Board;
import commons.Result;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;
import java.util.UUID;

public class BoardCardPreviewCtrl {

    private MyFXML fxml;
    private SceneCtrl sceneCtrl;
    private BoardsOverviewCtrl boardsOverviewCtrl;
    private MultiboardCtrl multiBoardCtrl;
    private ServerUtils server;

    @FXML
    Label boardTitle;
    @FXML
    Label boardDescription;

    // replace with icon
    @FXML
    Label pwdProtected;
    private Board board;


    @Inject
    public BoardCardPreviewCtrl(SceneCtrl sceneCtrl,
                                BoardsOverviewCtrl boardsOverviewCtrl,
                                MultiboardCtrl multiBoardCtrl,
                                ServerUtils server) {
        this.multiBoardCtrl = multiBoardCtrl;
        this.boardsOverviewCtrl = boardsOverviewCtrl;
        this.sceneCtrl = sceneCtrl;
        this.server = server;
    }

    /**
     * @return the board id
     */
    public UUID getBoardId() {
        return this.board.boardID;
    }

    /**
     * @return the board
     */
    public Board getBoard() {
        return this.board;
    }

    /**
     * @param board the board to be set
     */
    public void setBoard(Board board) {
        this.board = board;
    }


    /**
     * @param board the board to be displayed
     *
     *              Sets the content of the board card
     */
    public void setContent(Board board) {
        this.board = board;
        boardTitle.setText(board.boardTitle);
        boardDescription.setText(board.description);
        if (board.isProtected) {
            pwdProtected.setText("Password Protected");
        }
        else {
            pwdProtected.setText("Public Board");
        }
    }

    /**
     * Opens the board when the user clicks on it
     */
    public void openBoard() {
        multiBoardCtrl.openBoard(this.board.boardID);
    }

    /**
     * Deletes the board if the user is an admin
     * or leaves the board if the user is not an admin
     */
    public void leaveOrDeleteBoard() {
        // authentication and password protection
        // should be checked here
        leaveBoard();
    }

    /**
     * Leaves the board
     * (for non-admin users)
     */
    private void leaveBoard() {
        boardsOverviewCtrl.deleteBoardLocal(this.board.boardID);
        boardsOverviewCtrl.loadAllBoards();
        boardsOverviewCtrl.loadPreviews();
    }

    /**
     * Deletes the board from the multiboard overview and server
     */
    public void deleteBoard() {
        Result<Board> res = server.deleteBoard(this.board.boardID, this.board);
        if(res.success){
            boardsOverviewCtrl.deleteBoardLocal(this.board.boardID);
            boardsOverviewCtrl.loadAllBoards();
            boardsOverviewCtrl.loadPreviews();
        }
        else{
            sceneCtrl.showError(res.message, "Error deleting board");
        }
    }

    /**
     * Opens the edit board scene
     */
    public void editBoard() {
        sceneCtrl.editBoard(this.board);
    }


}
