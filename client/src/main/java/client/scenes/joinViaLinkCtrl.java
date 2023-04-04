package client.scenes;

import client.MultiboardCtrl;
import client.SceneCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Result;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.UUID;


public class joinViaLinkCtrl {

    SceneCtrl sceneCtrl;
    MultiboardCtrl multiboardCtrl;
    BoardsOverviewCtrl boardsOverviewCtrl;
    ServerUtils server;


    @FXML
    TextField boardLink;

    @FXML
    Button joinButton;

    public joinViaLinkCtrl(){

    }

    /** Initialises the controller using dependency injection */
    @Inject
    public joinViaLinkCtrl(SceneCtrl sceneCtrl,ServerUtils server, MultiboardCtrl multiboardCtrl, BoardsOverviewCtrl boardsOverviewCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.multiboardCtrl = multiboardCtrl;
        this.boardsOverviewCtrl = boardsOverviewCtrl;
        this.server = server;
    }


    public void joinBoard(ActionEvent actionEvent) {
        String boardLinkText = boardLink.getText();
        Result<Board> res = server.getBoard(UUID.fromString(boardLinkText));
        UUID boardUUID;
        try{
            boardUUID = UUID.fromString(boardLinkText);

        }catch (Exception e){
            sceneCtrl.showError("Invalid invite String","INVALID STRING");
        }
        if(res.success){
            multiboardCtrl.saveBoard(UUID.fromString(boardLinkText));
            sceneCtrl.showMultiboard();
        }else {
            sceneCtrl.showError("Invalid invite link!", "Board does not exist !!!");
        }

    }


    /**
     * Clears all fields
     */
    public void clearFields(){
        boardLink.clear();;
    }

    /**
     * Exits the scene
     */
    public void cancel() {
        clearFields();
        sceneCtrl.showMultiboard();
    }
}
