package server.api.Task;

import commons.Card;
import commons.Task;
import commons.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import server.database.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;
    @InjectMocks
    TaskService taskService;

    Task task1;
    Task task2;
    Card card1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository);

        task1 = new Task(1, "Test Task", false);
        task2 = new Task(2, "Test Task 2", true);

        card1 = new Card(1, "Test Card", "pikachu is cute",
                new ArrayList<>(List.of(task1, task2)), new ArrayList<>());
    }

    @Test
    void getAll() {
        List<Task> taskList = new ArrayList<>(List.of(task1, task2));
        doReturn(taskList).when(taskRepository).findAll();

        Result<List<Task>> result = taskService.getAll();
        assertEquals(Result.SUCCESS.of(taskList), result);
    }
    @Test
    void getAllFAIL() {
        doThrow(new RuntimeException()).when(taskRepository).findAll();

        Result<List<Task>> result = taskService.getAll();
        assertEquals(Result.FAILED_GET_ALL_TASKS.of(null), result);
    }
    @Test
    void addNewTask() {
        doReturn(task1).when(taskRepository).save(task1);

        Result<Task> result = taskService.addNewTask(task1);
        assertEquals(Result.SUCCESS.of(task1), result);
    }

    @Test
    void addNewTaskNull() {
        Task nullTask = null;

        Result<Task> result = taskService.addNewTask(nullTask);
        assertEquals(Result.OBJECT_ISNULL.of(null), result);
    }

    @Test
    void addNewTaskFAIL() {
        doThrow(new RuntimeException()).when(taskRepository).save(task1);

        Result<Task> result = taskService.addNewTask(task1);
        assertEquals(Result.FAILED_ADD_NEW_TASK.of(null), result);
    }

    @Test
    void deleteTask() {
        Result<Boolean> result = taskService.deleteTask(task1.taskID);
        assertEquals(Result.SUCCESS.of(true), result);
    }

    @Test
    void deleteTaskFAIL() {
        doThrow(new RuntimeException()).when(taskRepository).deleteById(task1.taskID);

        Result<Boolean> result = taskService.deleteTask(task1.taskID);
        assertEquals(Result.FAILED_DELETE_TASK.of(false), result);
    }

    @Test
    void updateTaskTitle() {
        doReturn(Optional.of(task1)).when(taskRepository).findById(task1.taskID);
        doReturn(task1).when(taskRepository).save(task1);

        Result<Task> result = taskService.updateTask(task1, 1);
        assertEquals(Result.SUCCESS.of(task1), result);
    }
    @Test
    void updateTaskTitleFAIL() {
        doThrow(new RuntimeException()).when(taskRepository).findById(task1.taskID);

        Result<Task> result = taskService.updateTask(task2, 1);
        assertEquals(Result.FAILED_UPDATE_TASK.of(null), result);
    }
    @Test
    void getTaskById() {
        doReturn(Optional.of(task1)).when(taskRepository).findById(task1.taskID);

        Result<Task> result = taskService.getTaskById(task1.taskID);
        assertEquals(Result.SUCCESS.of(task1), result);
    }
    @Test
    void getTaskByIdFAIL() {
        doThrow(new RuntimeException()).when(taskRepository).findById(42);

        Result<Task> result = taskService.getTaskById(42);
        assertEquals(Result.FAILED_RETRIEVE_TASK_BY_ID.of(null), result);
    }
    @Test
    void checkOrUncheckTask() {
        doReturn(Optional.of(task1)).when(taskRepository).findById(task1.taskID);
        doReturn(task1).when(taskRepository).save(task1);

        Result<Task> result = taskService.checkOrUncheckTask(1);
        assertEquals(Result.SUCCESS.of(task1), result);
    }
    @Test
    void checkOrUncheckTaskFAIL() {
        doThrow(new RuntimeException()).when(taskRepository).findById(task1.taskID);

        Result<Task> result = taskService.checkOrUncheckTask(1);
        assertEquals(Result.FAILED_TOGGLE_TASK_COMPLETED_STATUS.of(null), result);
    }
}