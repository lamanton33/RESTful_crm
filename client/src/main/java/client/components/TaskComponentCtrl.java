package client.components;

import client.*;
import com.google.inject.*;
import commons.*;
import commons.utils.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class TaskComponentCtrl {
    private final SceneCtrl sceneCtrl;
    private final IDGenerator idGenerator;
    private Task task;
    @FXML
    private TextField taskLabel;
    @FXML
    private CheckBox isCompleted;

    @Inject
    public TaskComponentCtrl(SceneCtrl sceneCtrl, IDGenerator idGenerator) {
        this.sceneCtrl = sceneCtrl;
        this.idGenerator = idGenerator;
    }

    /** Initialises the controller.
     * Adds a listener to the task title focus. If a user stops typing it will automatically save the card. */
    @FXML
    public void initialize() {
        taskLabel.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue) {
                saveCard();
            }
        });
    }

    /** Gets the task contained in this controller */
    public Task getTask() {
        var title = taskLabel.getText();
        var isCompleted = this.isCompleted.isSelected();

        task.taskTitle = title;
        task.isCompleted = isCompleted;
        return task;
    }

    /** Sets the UI components to the specified task */
    public void setTask(Task task) {
        taskLabel.setText(task.taskTitle);
        taskLabel.positionCaret(taskLabel.getLength());
        isCompleted.setSelected(task.isCompleted);
        this.task = task;
    }

    /** Saves the card this task is connected to */
    public void saveCard() {
        sceneCtrl.saveCard();
    }

    /** Deletes this task from the card */
    public void deleteTask() {
        sceneCtrl.deleteTask(this);
    }
}
