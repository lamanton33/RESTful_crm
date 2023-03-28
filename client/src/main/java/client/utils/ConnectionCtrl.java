package client.utils;

import client.MultiboardCtrl;
import client.components.BoardComponentCtrl;
import client.SceneCtrl;
import com.google.inject.Inject;
import commons.Result;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class ConnectionCtrl {

    private ServerUtils server;
    private SceneCtrl sceneCtrl;
    private MultiboardCtrl multiboardCtrl;

    private String serverUrl;
    @FXML
    private TextField urlField;

    /** Initialises the controller using dependency injection */
    @Inject
    public ConnectionCtrl(ServerUtils server, SceneCtrl sceneCtrl, MultiboardCtrl multiboardCtrl) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.multiboardCtrl = multiboardCtrl;
    }

    /** Tries to connect to the server filled in the text box and create a websocket,
     * f it fails it creates a popup showing the error */
    public void connect() {
        this.serverUrl = urlField.getText();
        if(serverUrl.isEmpty()){
            serverUrl = "http://localhost:8080/";
        }
        server.setServer(this.serverUrl);
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
                Pair<BoardComponentCtrl, Parent> boardPair = multiboardCtrl.createBoard();
                sceneCtrl.setBoard( new Scene(boardPair.getValue()));

            }
        } catch (RuntimeException e) {
            sceneCtrl.showError(e.getMessage(), "Failed to connect");
        }
    }

    /** Creates a websocket session using Stomp protocol and sets it in serverUtils
     * @return result Result Object containing status and a payload from the server
     */
    public Result<Object> startWebsocket(){
        String url = "ws://" + serverUrl.split("//")[1]  +"/websocket";
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        try{
            StompSession session = stompClient.connect(url, new StompSessionHandlerAdapter(){}).get();
            server.setSession(session);
            return Result.SUCCESS;
        }catch (ExecutionException | InterruptedException e){
            return Result.FAILED_WEBSOCKET_CONNECTION.of(e);
        }
    }


}
