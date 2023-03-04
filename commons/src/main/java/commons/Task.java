package commons;


import javax.persistence.*;
import java.util.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int taskID;
    public String taskTitle;
    public Boolean isCompleted;

    public Task() {

    }

    public Task(int taskID, String taskTitle, Boolean isCompleted) {
        this.taskID = taskID;
        this.taskTitle = taskTitle;
        this.isCompleted = isCompleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return taskID == task.taskID && Objects.equals(taskTitle, task.taskTitle)
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
