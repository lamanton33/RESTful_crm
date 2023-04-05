package commons;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class Task {

    @Id
    public UUID taskID;
    public String taskTitle;
    public Boolean isCompleted;
    public UUID cardId;
    @ManyToOne
    @JsonIgnore
    public Card card;

    /**
     * @param completed Sets a task to the completed status
     */
    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    /**
     * @param taskTitle Sets a title for a Task object
     */
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public Task() {
    }

    public Task(String taskTitle,
                Boolean isCompleted) {
        this.taskTitle = taskTitle;
        this.isCompleted = isCompleted;
    }
    public Task(UUID taskID,
                String taskTitle,
                Boolean isCompleted) {
        this.taskID = taskID;
        this.taskTitle = taskTitle;
        this.isCompleted = isCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(taskID, task.taskID) && Objects.equals(taskTitle, task.taskTitle)
                && Objects.equals(isCompleted, task.isCompleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskID, taskTitle, isCompleted);
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskID=" + taskID +
                ", taskTitle='" + taskTitle + '\'' +
                ", isCompleted=" + isCompleted +
                '}';
    }
}
