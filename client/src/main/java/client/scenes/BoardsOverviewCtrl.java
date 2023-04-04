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
import javafx.scene.shape.Circle;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BoardsOverviewCtrl {

    private final MultiboardCtrl multiboardCtrl;
    private final ConnectionCtrl connectionCtrl;
    private List<BoardCardPreviewCtrl> boardCardPreviewCtrls;
    private List<BoardComponentCtrl> boardComponentCtrls;

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
    Button joinButton;

    @FXML
    VBox box1;

    @FXML
    VBox box2;
    @FXML
    VBox box3;

    @FXML
    Circle status;

    @FXML
    Button adminButton;

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
        joinButton.setVisible(false);
        adminButton.setVisible(false);
    }

    /**
     * triggers the admin login form window to open
     */
    public void adminLogin(){
        sceneCtrl.showAdminLoginPopup();
    }

    /**
     * @param boardID the id of the board to be deleted
     *                this method is called when a board is deleted
     *                it removes the preview controller of the board
     *                from the list of preview controllers
     *                The effect of this method only has a local scope
     *                (client side
     */
    public void deleteBoardLocal(UUID boardID) {
        boardCardPreviewCtrls.removeIf(boardCardPreviewCtrl -> boardCardPreviewCtrl.getBoardId().equals(boardID));
        multiboardCtrl.deleteBoard(boardID);
    }


    /**
     * @param board the board to be added to the list of boards
     *              this method is called when a board is updated
     */
    public void updateBoard(Board board) {
        for (BoardCardPreviewCtrl boardCardPreviewCtrl : boardCardPreviewCtrls) {
            if (boardCardPreviewCtrl.getBoardId().equals(board.boardID)) {
                boardCardPreviewCtrl.setBoard(board);
            }
        }
        Result<Board> res = server.updateBoard(board);
        if(res.success){
            loadAllBoards();
            loadPreviews();
        }
        else {
            sceneCtrl.showError(res.message, "Failed to update board");
        }
    }

    /** Tries to connect to the server filled in the text box and create a websocket,
     * f it fails it creates a popup showing the error */
    public void connectToServer(){
        if(!server.isConnected){
            if(connectionCtrl.connect(connectionString.getText()).equals(Result.SUCCESS)){
                loadAllBoards();
                disConnectButton.setText("Disconnect");
                createButton.setVisible(true);
                adminButton.setVisible(true);
                joinButton.setVisible(true);
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
     *  loads the multiboard overview preview cards
     *
     */
    public void loadPreviews() {
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

    /**
     * loads all boards from the local cache
     */
    public void loadAllBoards() {
        this.localBoards = multiboardCtrl.loadBoards();
        loadPreviews();
    }

    /**
     * @param actionEvent event that triggers the method
     *                    creates a new board and loads it into the multiboard
     */
    public void createBoard(ActionEvent actionEvent) {
        sceneCtrl.showCreateBoardPopup();
        loadAllBoards();
        loadPreviews();
    }

    /**
     * @param boardId the id of the board to be retrieved
     * @return the board with the given id
     */
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

    public void joinViaLink(ActionEvent actionEvent) {
        sceneCtrl.showJoinBoar();

    }
}

