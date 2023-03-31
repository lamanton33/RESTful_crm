package server.api.Task;

import commons.Card;
import commons.Result;
import commons.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import server.api.Card.CardService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    TaskService taskService;
    @Mock
    CardService cardService;

    @InjectMocks
    TaskController taskController;

    Task task1;
    Task task2;
    Card card1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskController = new TaskController(taskService, cardService);

        task1 = new Task(1, "Test Task", false);
        task2 = new Task(2, "Test Task 2", true);

        card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(List.of(task1, task2)), new ArrayList<>());
    }
    @Test
    void getAllTasks() {
        List<Task> allTasks = new ArrayList<>();
        allTasks.add(task1);
        doReturn(Result.SUCCESS.of(allTasks)).when(taskService).getAll();

        Result<List<Task>> result = taskController.getAllTasks();
        assertEquals(Result.SUCCESS.of(allTasks), result);
    }

    @Test
    void getTaskById() {
        doReturn(Result.SUCCESS.of(task1)).when(taskService).getTaskById(1);

        Result<Task> result = taskController.getTaskById(1);
        assertEquals(Result.SUCCESS.of(task1), result);
    }

    @Test
    void addNewTask() {
        doReturn(Result.SUCCESS.of(task1)).when(taskService).addNewTask(task1);

        Result<Task> result = taskController.addNewTask(task1);
        assertEquals(Result.SUCCESS.of(task1), result);
    }

    @Test
    void deleteTask() {
        doReturn(Result.SUCCESS.of(task1)).when(taskService).deleteTask(1);

        taskController.deleteTask(1);
        assertEquals(null, taskService.getTaskById(1));
    }

    @Test
    void updateTaskTitle() {
        doReturn(Result.SUCCESS.of(task1)).when(taskService).updateTask( task1, 1);

        Result<Task> result = taskController.updateTaskTitle(task1, 1);
        assertEquals(Result.SUCCESS.of(task1), result);
    }

    @Test
    void checkOrUncheckTask() {
        doReturn(Result.SUCCESS.of(task1)).when(taskService).checkOrUncheckTask(1);

        Result<Task> result = taskController.checkOrUncheckTask(1);
        assertEquals(Result.SUCCESS.of(task1), result);
    }
}