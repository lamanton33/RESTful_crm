package client.components;

import client.*;
import com.google.inject.*;
import commons.*;
import commons.utils.*;
import javafx.fxml.*;
import javafx.scene.control.*;

import java.util.*;

public class TaskComponentCtrl {
    private final SceneCtrl sceneCtrl;
    private final IDGenerator idGenerator;
    private UUID id;
    @FXML
    private TextField taskLabel;
    @FXML
    private CheckBox isCompleted;

    @Inject
    public TaskComponentCtrl(SceneCtrl sceneCtrl, IDGenerator idGenerator) {
        this.sceneCtrl = sceneCtrl;
        this.idGenerator = idGenerator;
    }

    /** Gets the task contained in this controller */
    public Task getTask() {
        var title = taskLabel.getText();
        var isCompleted = this.isCompleted.isSelected();

        var task = new Task(title, isCompleted);
        task.taskID = id != null ? id : idGenerator.generateID();
        return task;
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
