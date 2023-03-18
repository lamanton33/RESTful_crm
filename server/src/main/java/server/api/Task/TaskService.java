package server.api.Task;

import commons.CardList;
import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.TaskRepository;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Retrieves all the tasks from the repository
     */
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    /**
     * Adds the task list to the repository
     */
    public Task addNewTask(Task task) {
        if (task.taskTitle == null || task == null) {
            return null;
        }
        return taskRepository.save(task);
    }

    /**
     * Deletes the task with the given id
     */
    public void deleteTask(Integer id) {
        taskRepository.deleteById(id);
    }

    /**
     * Updates the name of the task with id {id},
     * with the name of the given Task.
     */
    public Task updateTaskTitle(Task task, Integer id) {
        return taskRepository.findById(id)
                .map(t -> {
                    t.setTaskTitle(task.taskTitle);
                    return taskRepository.save(t);
                })
                .orElseGet(() -> {
                    return taskRepository.save(task);
                });
    }

    /**
     * Gets the task with the given id
     */
    public Task getTaskById(Integer id) {
        return taskRepository.findById(id).get();
    }

    /**
     * Sets the boolean isCompleted to the opposite of the current value
     */
    public Task checkOrUncheckTask(Integer id){
        return taskRepository.findById(id)
                .map(t -> {
                    t.setCompleted(!t.isCompleted);
                    return taskRepository.save(t);
                }).get();
    }

}
