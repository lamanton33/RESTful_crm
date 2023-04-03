package server.api.Task;

import commons.Result;
import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import server.database.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public Result<List<Task>> getAll() {
        try{
            return Result.SUCCESS.of(taskRepository.findAll());
        }
        catch (Exception e){
            return Result.FAILED_GET_ALL_TASKS;
        }
    }

    /**
     * Adds the task list to the repository
     */
    public Result<Task> addNewTask(Task task) {
        if (task == null || task.taskTitle == null) {
            return Result.OBJECT_ISNULL.of(null);
        }
        try{
            return Result.SUCCESS.of(taskRepository.save(task));
        }catch (Exception e){
            return Result.FAILED_ADD_NEW_TASK;
        }
    }

    /**
     * Deletes the task with the given id
     */
    public Result<Boolean> deleteTask(UUID id) {
        if(id == null) return Result.OBJECT_ISNULL.of(false);
        try {
            if(taskRepository.findById(id).isPresent()){
                taskRepository.deleteById(id);
                return Result.SUCCESS.of(true);
            }
            return Result.FAILED_DELETE_TASK.of(false);
        } catch (Exception e) {
            return Result.FAILED_DELETE_TASK.of(false);
        }
    }

    /**
     * Updates the name of the task with id {id},
     * with the name of the given Task.
     */
    public Result<Task> updateTask(Task task, UUID id) {
        try {
            return Result.SUCCESS.of(taskRepository.save(task));
        }catch (Exception e){
            return Result.FAILED_UPDATE_TASK;
        }
    }

    /**
     * Gets the task with the given id
     */
    public Result<Task> getTaskById(UUID id) {
        try {
            return Result.SUCCESS.of(taskRepository.findById(id).get());
        }catch (Exception e){
            return Result.FAILED_RETRIEVE_TASK_BY_ID;
        }
    }

    /**
     * Sets the boolean isCompleted to the opposite of the current value
     */
    public Result<Task> checkOrUncheckTask(UUID id){
        try{
            return Result.SUCCESS.of(taskRepository.findById(id)
                   .map(t -> {
                       t.setCompleted(!t.isCompleted);
                       return taskRepository.save(t);
                   }).get());
        }
        catch (Exception e){
            return Result.FAILED_TOGGLE_TASK_COMPLETED_STATUS.of(null);
        }
    }

}
