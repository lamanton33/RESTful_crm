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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
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

    private List<UUID> localBoards;
    private ServerUtils server;
    private MyFXML fxml;
    private SceneCtrl sceneCtrl;
    private Color connectedColor = Color.web("#34faab",1.0);
    private Color disConnectedColor= Color.web("ff6f70",1.0);

    private List<VBox> vboxList = new ArrayList<>();

    @FXML
    TextField connectionString;

    @FXML
    Button disConnectButton;

    @FXML
    VBox box1;

    @FXML
    VBox box2;
    @FXML
    VBox box3;

    @FXML
    Circle status;


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

    public void initialize(){
        vboxList.add(box1);
        vboxList.add(box2);
        vboxList.add(box3);
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
        if(!server.isConnected){
            if(connectionCtrl.connect(connectionString.getText()).equals(Result.SUCCESS)){
                loadAllBoards();
                disConnectButton.setText("Disconnect");
                status.setFill(connectedColor);
            };
        }else{
            connectionCtrl.stopWebsocket();
            server.disconnect();
            status.setFill(disConnectedColor);
            disConnectButton.setText("Connect");
            clearPreviews();
            this.boardCardPreviewCtrls = new ArrayList<>();

        }
    }

    public void clearPreviews(){
        for (VBox vbox:vboxList) {
            vbox.getChildren().clear();
        };
    }

    private void loadPreviews() {
        clearPreviews();
        int listIndex = 0;
        for (VBox vbox:vboxList) {
            Pair<BoardCardPreviewCtrl, Parent> boardPair = fxml.load(BoardCardPreviewCtrl.class, "client", "scenes",
                    "components", "BoardCardPreview.fxml");
            BoardCardPreviewCtrl boardCardPreviewCtrl = boardPair.getKey();
            boardCardPreviewCtrl.setContent(retrieveContent(localBoards.get(listIndex)));
            vbox.getChildren().add(boardPair.getValue());
            boardCardPreviewCtrls.add(boardCardPreviewCtrl);
        };
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

