package client.scenes;

import client.*;
import client.components.BoardCardPreviewCtrl;
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
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @FXML
    Button createButton;


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
     * Initializes the vboxes for the previews
     */
    public void initialize(){
        vboxList.add(box1);
        vboxList.add(box2);
        vboxList.add(box3);
        createButton.setVisible(false);
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
                createButton.setVisible(true);
                status.setFill(connectedColor);
            };
        }else{
            connectionCtrl.stopWebsocket();
            server.disconnect();
            status.setFill(disConnectedColor);
            createButton.setVisible(false);
            disConnectButton.setText("Connect");
            clearPreviews();
            this.boardCardPreviewCtrls = new ArrayList<>();

        }
    }

    /**
     * Clears all Previews
     */
    public void clearPreviews(){
        for (VBox vbox:vboxList) {
            vbox.getChildren().clear();
        };
    }

    /**
     * Loads in all the board previews
     */
    private void loadPreviews() {
        clearPreviews();
        int listIndex = 0;
        VBox vbox;
        for (int i = 0; i < localBoards.size();i++) {
            Pair<BoardCardPreviewCtrl, Parent> boardPair = fxml.load(BoardCardPreviewCtrl.class, "client", "scenes",
                    "components", "BoardCardPreview.fxml");
            vbox = vboxList.get(listIndex);
            BoardCardPreviewCtrl boardCardPreviewCtrl = boardPair.getKey();
            boardCardPreviewCtrl.setContent(retrieveContent(localBoards.get(listIndex++)));
            vbox.getChildren().add(boardPair.getValue());
            System.out.println(listIndex);
            boardCardPreviewCtrls.add(boardCardPreviewCtrl);
            if(listIndex >= 3){
                listIndex =0;
            }
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

