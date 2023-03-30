package client.components;

import client.*;
import com.google.inject.*;
import commons.*;
import javafx.fxml.*;
import javafx.scene.control.*;

public class TaskComponentCtrl {
    private final SceneCtrl sceneCtrl;
    private int index;
    @FXML
    private TextField taskLabel;
    @FXML
    private CheckBox isCompleted;

    @Inject
    public TaskComponentCtrl(SceneCtrl sceneCtrl) {
        this.sceneCtrl = sceneCtrl;
    }

    /** Gets the task contained in this controller */
    public Task getTask() {
        var title = taskLabel.getText();
        var isCompleted = this.isCompleted.isSelected();

        return new Task(title, isCompleted);
    }

    /** Sets the UI components to the specified task */
    public void setTask(String title, boolean completed) {
        taskLabel.setText(title);
        isCompleted.setSelected(completed);
    }

    /** Deletes this task from the card */
    public void deleteTask() {
        sceneCtrl.deleteTask(this);
    }
}
