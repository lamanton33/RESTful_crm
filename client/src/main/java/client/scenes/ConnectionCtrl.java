package client.scenes;

import client.scenes.dataclass_controllers.BoardCtrl;
import client.utils.*;
import com.google.inject.*;
import javafx.fxml.*;
import javafx.scene.control.*;

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

        server.setServer(urlField.getText());
        try {
            System.out.println("Trying to connect to " + server.getServerUrl());
            var result = server.connect();
            var resultWS = server.startWebsocket();
            boardCtrl.registerForMessages();
            if (!result.success) {
                mainCtrl.showError(result.message, "Failed to connect to server");
            }else if(!resultWS.success){
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
}
