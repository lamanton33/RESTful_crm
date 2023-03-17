package client.scenes;

import client.utils.*;
import com.google.inject.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.stage.*;

public class ConnectToServerCtrl {

    @FXML
    private TextField url;

    private ServerUtils server;
    private MainCtrl mainCtrl;

    /** Initialises the connection controller. */
    @Inject
    public ConnectToServerCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /** Tries to connect to the server filled in the text box. If it fails it trows an error. */
    public void connect() {
        server.setServer(url.getText());
        try {
            var result = server.connect();
            if (!result.success) {
                mainCtrl.showError(result.message, "Failed to connect");
            } else {
                System.out.println("*Adjusts hacker glasses* I'm in");
                mainCtrl.refreshBoard();
                mainCtrl.showListOverview();
            }
        } catch (RuntimeException e) {
            mainCtrl.showError(e.getMessage(), "Failed to connect");
        }
    }
}
