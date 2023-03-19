package server.api.Task;

import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Handles the routes for the Task endpoints
 */
@RestController
@RequestMapping("/api/Task")
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * Retrieves all the tasks from the repository
     */
    @GetMapping({" ", "/"})
    public List<Task> getAllTasks(){
        return taskService.getAll();
    }

    /**
     * Retrieves the CardList with the given id from the repository
     */
    @GetMapping({"id"})
    public Task getTaskById(@PathVariable Integer id){
        return taskService.getTaskById(id);
    }

    /**
     * Post request to add the tasks in the request body to the repository
     */
    @PostMapping({" ", "/"})
    public Task addNewTask(@RequestBody Task task){
        return taskService.addNewTask(task);
    }

    /**
     * Delete request to remove the tasks with the given id from the repository
     */
    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }

    /**
     * Put request to update the task's title with id {id}
     */
    @PutMapping("/{id}")
    public Task updateTaskTitle(@RequestBody Task task, @PathVariable Integer id){
        return taskService.updateTaskTitle(task, id);
    }

    /**
     * Put request to toggle the task's isCompleted value
     */
    @PutMapping("/toggleIsCompleted/{id}")
    public Task checkOrUncheckTask(@PathVariable Integer id){
        return taskService.checkOrUncheckTask(id);
    }
}
