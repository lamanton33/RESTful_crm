package server.api.Task;

import commons.Result;
import commons.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import server.api.Card.CardService;

import java.util.List;

/**
 * Handles the routes for the Task endpoints
 */
@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;
    private final CardService cardService;

    @Autowired
    public TaskController(TaskService taskService, CardService cardService) {
        this.taskService = taskService;
        this.cardService = cardService;
    }

    /**
     * Retrieves all the tasks from the repository
     */
    @GetMapping({" ", "/get-all/"})
    public Result<List<Task>> getAllTasks(){
        return taskService.getAll();
    }

    /**
     * Retrieves the CardList with the given id from the repository
     */
    @GetMapping({"/get/{id}"})
    public Result<Task> getTaskById(@PathVariable Integer id){
        return taskService.getTaskById(id);
    }

    /**
     * Post request to add the tasks in the request body to the repository
     */
    @PostMapping({" ", "/create/"})
    public Result<Task> addNewTask(@RequestBody Task task){
        return taskService.addNewTask(task);
    }

    /**
     * Delete request to remove the tasks with the given id from the repository
     */
    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }

    /**
     * Put request to update the task's title with id {id}
     */
    @PutMapping("/update/{id}")
    public Result<Task> updateTaskTitle(@RequestBody Task task, @PathVariable Integer id){
        return taskService.updateTaskTitle(task, id);
    }

    /**
     * Put request to toggle the task's isCompleted value
     */
    @PutMapping("/toggle-is-completed/{id}")
    public Result<Task> checkOrUncheckTask(@PathVariable Integer id){
        return taskService.checkOrUncheckTask(id);
    }
}
