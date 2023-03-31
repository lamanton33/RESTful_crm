package client.scenes;

import client.SceneCtrl;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Theme;
import commons.utils.IDGenerator;
import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class CustomizeBoardCtrl {

    private final ServerUtils server;

    private final SceneCtrl sceneCtrl;

    private final Board board;
    private final IDGenerator idGenerator;

    //This needs to be decided by the team
    private final static Theme reset = new Theme("#2A2A2A", "#1b1b1b", "Black");

    @FXML
    private ColorPicker backgroundColor;

    @FXML
    private ColorPicker cardColor;

    @FXML
    private ColorPicker fontColor;

    @FXML
    private Label boardName;

    @Inject
    public CustomizeBoardCtrl(ServerUtils server, SceneCtrl sceneCtrl, Board board, IDGenerator idGenerator) {
        this.server = server;
        this.sceneCtrl = sceneCtrl;
        this.board = board;
        this.idGenerator = idGenerator;
    }


    /**
     * Retrieves the values for the new Theme, updates the board and returns to board overview.
     */
    public void save() {
        Theme newTheme = new Theme(backgroundColor.getValue().toString(),
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

    /**
     * Resets the values of the color pickers to the inital values
     * defined in the application
     */
    public void resetValues(){
        this.backgroundColor.setValue(Color.web(reset.backgroundColor));
        this.cardColor.setValue(Color.web(reset.cardColor));
        this.fontColor.setValue(Color.web(reset.textColor));
    }

}