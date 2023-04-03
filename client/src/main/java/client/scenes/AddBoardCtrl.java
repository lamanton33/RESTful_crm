package client.scenes;

import client.MultiboardCtrl;
import client.SceneCtrl;
import com.google.inject.Inject;
import commons.Board;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.UUID;

public class AddBoardCtrl {
    private final SceneCtrl sceneCtrl;
    private MultiboardCtrl multiboardCtrl;
    private BoardsOverviewCtrl boardsOverviewCtrl;
    private UUID boardID;

    private boolean created;
    @FXML
    private TextField title;

    @FXML
    private TextArea description;
    @FXML
    private Button applyButton;
    private Board board;

    /** Initialises the controller using dependency injection */
    @Inject
    public AddBoardCtrl(SceneCtrl sceneCtrl, MultiboardCtrl multiboardCtrl, BoardsOverviewCtrl boardsOverviewCtrl) {
        this.sceneCtrl = sceneCtrl;
        this.multiboardCtrl = multiboardCtrl;
        this.boardsOverviewCtrl = boardsOverviewCtrl;
    }

    /**
     * @param actionEvent the event that triggered the method
     *                    Creates a new board
     */
    public void createBoard(ActionEvent actionEvent) {
        multiboardCtrl.createBoard(title.getText(), description.getText());
        boardsOverviewCtrl.loadAllBoards();
        boardsOverviewCtrl.loadPreviews();
        cancel();
    }
    public void createOrUpdate(ActionEvent actionEvent){
        if(created){
            updateBoard(actionEvent);
        }
        else {
            createBoard(actionEvent);
        }
    }

    /**
     * @param actionEvent the event that triggered the method
     *                    Updates the board
     */
    public void updateBoard(ActionEvent actionEvent) {
        board.boardTitle = title.getText();
        board.description = description.getText();
        boardsOverviewCtrl.updateBoard(board);
        boardsOverviewCtrl.loadAllBoards();
        boardsOverviewCtrl.loadPreviews();
        cancel();
    }

    /**
     * Clears all fields
     */
    public void clearFields(){
        title.clear();
        description.clear();
    }

    /**
     * Exits the scene
     */
    public void cancel() {
        clearFields();
        sceneCtrl.showMultiboard();
    }

    /**
     * @param board the board to be edited
     *              Sets the scene to edit mode
     */
    public void edit(Board board) {
        created = true;
        this.board = board;
        applyButton.setText("Apply");
        applyButton.setOnAction(this::updateBoard);

        title.setText(board.boardTitle);
        description.setText(board.description);
    }

    public void create() {
        created = false;
        applyButton.setText("Create");
        applyButton.setOnAction(this::createBoard);
    }



}
