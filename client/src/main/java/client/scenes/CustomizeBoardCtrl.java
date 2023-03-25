package client.scenes;

import client.SceneCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Theme;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;

public class CustomizeBoardCtrl {

    private final ServerUtils server;

    private final SceneCtrl sceneCtrl;

    private final Board board;

    @FXML
    private ColorPicker backgroundColor;

    @FXML
    private ColorPicker cardColor;

    @FXML
    private ColorPicker fontColor;

    @FXML
    private Label boardName;

    @Inject
    public CustomizeBoardCtrl(ServerUtils server, SceneCtrl sceneCtrl, Board board) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.board = board;
    }

    /**
     * Retrieves the values for the new Theme, updates the board and returns to board overview.
     */
    public void save() {
        Theme newTheme = new Theme(1, backgroundColor.getValue().toString(),
                cardColor.getValue().toString(), fontColor.getValue().toString());
        server.updateBoardTheme(this.board.boardID, newTheme);
        //Should eventually return to board overview, not list overview
        sceneCtrl.showBoard();
    }

    /**
     * Closes the customization operation, returns to the board overview scene
     */
    public void close() {
        sceneCtrl.showBoard(); //Should show the board overview
    }
}