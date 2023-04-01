package client.scenes;

import client.*;
import client.components.BoardCardPreviewCtrl;
import client.components.BoardComponentCtrl;
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
    private List<BoardCardPreviewCtrl> boardCardPreviewCtrls;
    private List<BoardComponentCtrl> boardComponentCtrls;

    private List<UUID> localBoards;
    private ServerUtils server;
    private MyFXML fxml;
    private SceneCtrl sceneCtrl;

    private String serverUrl;

    @FXML
    TextField connectionString;

    @FXML
    VBox box1;

    @FXML
    VBox box2;


    @Inject
    public BoardsOverviewCtrl(ServerUtils server, MyFXML fxml, SceneCtrl sceneCtrl, MultiboardCtrl multiboardCtrl) {
        this.server = server;
        this.fxml = fxml;
        this.sceneCtrl = sceneCtrl;
        this.multiboardCtrl = multiboardCtrl;
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
    public void connectToServer() {
        this.serverUrl = connectionString.getText();
        if(serverUrl.isEmpty()){
            serverUrl = "http://localhost:8080/";
        }
        server.setServer(serverUrl);
        //The following code should not need a try catch block right?
        // Since the error handling is managed with the Result objects
        try {
            Result<Object> serverConnectionResult = server.connect();
            Result<Object> webSocketConnectionResult = startWebsocket();

            if (!serverConnectionResult.success) {
                sceneCtrl.showError(serverConnectionResult.message, "Can't reach server");
            }else if(!webSocketConnectionResult.success){
                sceneCtrl.showError(webSocketConnectionResult.message, "Failed to start websocket");
            } else {
                System.out.println("*Adjusts hacker glasses* I'm in");
//                Pair<BoardComponentCtrl, Parent> boardPair = multiboardCtrl.createBoard();
//                sceneCtrl.setBoard( new Scene(boardPair.getValue()));

//                Pair<BoardComponentCtrl, Parent> boardPairr = multiboardCtrl.loadBoard();
//                sceneCtrl.setBoard(new Scene(boardPairr.getValue()));
                loadAllBoards();
                loadPreviews();

//                sceneCtrl.showMultiboard();

            }
        } catch (RuntimeException e) {
            sceneCtrl.showError(e.getMessage(), "Failed to connect");
        }
        System.out.println("BoardsOverviewCtrl initialized");
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

    /** Creates a websocket session using Stomp protocol and sets it in serverUtils
     * @return result Result Object containing status and a payload from the server
     */
    public Result<Object> startWebsocket() {
        String url = "ws://" + this.serverUrl.split("//")[1] + "/websocket";
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        try {
            StompSession session = stompClient.connect(url, new StompSessionHandlerAdapter() {
            }).get();
            server.setSession(session);
            return Result.SUCCESS;
        } catch (ExecutionException | InterruptedException e) {
            return Result.FAILED_WEBSOCKET_CONNECTION.of(e);
        }

    }


}
