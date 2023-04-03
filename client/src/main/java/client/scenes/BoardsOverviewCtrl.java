package client.scenes;

import client.*;
import client.components.BoardCardPreviewCtrl;
import client.components.BoardComponentCtrl;
import client.utils.ConnectionCtrl;
import client.utils.MyFXML;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javafx.util.Pair;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

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


    /** Tries to connect to the server filled in the text box and create a websocket,
     * f it fails it creates a popup showing the error */
    public void connectToServer(){
        if(connectionCtrl.connect(connectionString.getText()).equals(Result.SUCCESS)){
            loadAllBoards();
        };
    }


    private void loadPreviews() {

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

    private void loadAllBoards() {
        this.localBoards = multiboardCtrl.loadBoards();
        loadPreviews();
    }

    /**
     * @param actionEvent event that triggers the method
     *                    creates a new board and loads it into the multiboard
     */
    public void createBoard(ActionEvent actionEvent) {
        multiboardCtrl.createBoard();
        loadAllBoards();
        loadPreviews();
    }

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

