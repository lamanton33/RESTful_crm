package client.components;

import client.MultiboardCtrl;
import client.SceneCtrl;
import client.utils.MyFXML;
import client.utils.ServerUtils;
import commons.Board;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.inject.Inject;

public class BoardCardPreviewCtrl {

    private ServerUtils server;
    private MyFXML fxml;
    private SceneCtrl sceneCtrl;
    private MultiboardCtrl multiBoardCtrl;

    @FXML
    Label boardTitle;
    @FXML
    Label boardDescription;

    // replace with icon
//    @FXML
//    Label pwdProtected;
    private Board board;


    @Inject
    public BoardCardPreviewCtrl(SceneCtrl sceneCtrl, MultiboardCtrl multiBoardCtrl) {
        this.multiBoardCtrl = multiBoardCtrl;
        this.sceneCtrl = sceneCtrl;
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
//        if (board.isProtected) {
//            pwdProtected.setText("Password Protected");
//        }
//        else {
//            pwdProtected.setText("Public Board");
//        }
    }

    /**
     * Opens the board when the user clicks on it
     */
    public void openBoard() {
        multiBoardCtrl.openBoard(this.board.boardID);
    }


}
