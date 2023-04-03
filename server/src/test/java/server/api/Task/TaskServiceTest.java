package server.api.Task;

import commons.Card;
import commons.CardList;
import commons.Task;
import commons.Result;
import commons.utils.HardcodedIDGenerator;
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
    CardList cardList1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskService = new TaskService(taskRepository);

        HardcodedIDGenerator idGenerator1 = new HardcodedIDGenerator();
        idGenerator1.setHardcodedID("1");
        task1 = new Task(idGenerator1.generateID(), "Test Task", false);
        HardcodedIDGenerator idGenerator2 = new HardcodedIDGenerator();
        idGenerator2.setHardcodedID("2");
        task2 = new Task(idGenerator2.generateID(), "Test Task", true);
        cardList1 = new CardList("Test Card List", new ArrayList<>());
        card1 = new Card(cardList1, "Test Card", "pikachu is cute",
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
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        doReturn(Optional.of(task1)).when(taskRepository).findById(idGenerator.generateID());

        Result<Boolean> result = taskService.deleteTask(task1.taskID);
        assertEquals(Result.SUCCESS.of(true), result);
    }

    @Test
    void deleteTaskFAIL() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        doThrow(new RuntimeException()).when(taskRepository).findById(idGenerator.generateID());

        Result<Boolean> result = taskService.deleteTask(task1.taskID);
        assertEquals(Result.FAILED_DELETE_TASK.of(false), result);
    }

    @Test
    void deleteTaskFAILNull() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        doReturn(Optional.ofNullable(null)).when(taskRepository).findById(idGenerator.generateID());

        Result<Boolean> result = taskService.deleteTask(task1.taskID);
        assertEquals(Result.FAILED_DELETE_TASK.of(false), result);
    }

    @Test
    void updateTask() {
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        doReturn(task1).when(taskRepository).save(task1);

        Result<Task> result = taskService.updateTask(task1, idGenerator.generateID());
        assertEquals(Result.SUCCESS.of(task1), result);
    }
    @Test
    void updateTaskTitleFAIL() {
        doThrow(new RuntimeException()).when(taskRepository).save(task1);

        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Result<Task> result = taskService.updateTask(task2, idGenerator.generateID());
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
        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("42");
        doThrow(new RuntimeException()).when(taskRepository).findById(idGenerator.generateID());

        Result<Task> result = taskService.getTaskById(idGenerator.generateID());
        assertEquals(Result.FAILED_RETRIEVE_TASK_BY_ID.of(null), result);
    }
    @Test
    void checkOrUncheckTask() {
        doReturn(Optional.of(task1)).when(taskRepository).findById(task1.taskID);
        doReturn(task1).when(taskRepository).save(task1);

        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Result<Task> result = taskService.checkOrUncheckTask(idGenerator.generateID());
        assertEquals(Result.SUCCESS.of(task1), result);
    }
    @Test
    void checkOrUncheckTaskFAIL() {
        doThrow(new RuntimeException()).when(taskRepository).findById(task1.taskID);

        HardcodedIDGenerator idGenerator = new HardcodedIDGenerator();
        idGenerator.setHardcodedID("1");
        Result<Task> result = taskService.checkOrUncheckTask(idGenerator.generateID());
        assertEquals(Result.FAILED_TOGGLE_TASK_COMPLETED_STATUS.of(null), result);
    }
}