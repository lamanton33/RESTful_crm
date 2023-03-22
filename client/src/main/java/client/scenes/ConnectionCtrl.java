package client.scenes;

import client.scenes.dataclass_controllers.BoardCtrl;
import client.utils.*;
import com.google.inject.*;
import commons.Result;
import javafx.fxml.*;
import javafx.scene.control.*;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

public class ConnectionCtrl {


    @FXML
    private TextField urlField;

    private ServerUtils server;
    private MainCtrl mainCtrl;
    private final BoardCtrl boardCtrl;

    /** Initialises the connection controller. */
    @Inject
    public ConnectionCtrl(ServerUtils server, MainCtrl mainCtrl, BoardCtrl boardCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.boardCtrl = boardCtrl;
    }

    /** Tries to connect to the server filled in the text box. If it fails it trows an error. */
    public void connect() {
        String url = urlField.getText();
        if(url.isEmpty()){
            url = "http://localhost:8080/";
        }
        server.setServer(url);

        try {
            System.out.println("Trying to connect to " + server.getServerUrl());
            var result = server.connect();
            var resultWS = startWebsocket();

            if (!result.success) {
                mainCtrl.showError(result.message, "Server not available");
            }else if(!resultWS.success){
                boardCtrl.registerForMessages();
                mainCtrl.showError(resultWS.message, "Failed to start websocket");
            } else {
                System.out.println("*Adjusts hacker glasses* I'm in");

                mainCtrl.refreshBoard();
                mainCtrl.showBoard();
            }
        } catch (RuntimeException e) {
            mainCtrl.showError(e.getMessage(), "Failed to connect");
        }
    }

    /** start the websocket. */
    public Result<Object> startWebsocket(){
        String url = "ws://" + server.getServerUrl().split("//")[1]  +"/websocket";
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        try{
            System.out.println("Trying to connect to " + url);
            System.out.println("Hostname: " + url);
            server.setSession(stompClient.connect(url, new StompSessionHandlerAdapter(){}).get());
            return Result.SUCCESS;

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }catch (ExecutionException e){
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }


}
