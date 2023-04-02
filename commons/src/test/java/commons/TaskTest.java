package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TaskTest {

    @Test
    void testEquals() {
        Task taskA = new Task("TaskTitle",true);
        Task taskB = new Task("TaskTitle",true);
        Task taskC = new Task("TaskTitleDifferent",false);

        assertEquals(taskA,taskB);
        assertNotEquals(taskB,taskC);
    }

    @Test
    void testHashCode() {
        Task taskA = new Task("TaskTitle",true);
        Task taskB = new Task("TaskTitle",true);
        Task taskC = new Task("TaskTitleDifferent",false);
        assertEquals(taskA.hashCode(),taskB.hashCode());
        assertNotEquals(taskB.hashCode(),taskC.hashCode());
    }

    @Test
    void testToString() {
        Task task = new Task("TaskTitle",true);
        String actualString = task.toString();
        String string = "Task{taskID=null, taskTitle='TaskTitle', isCompleted=true}";
        assertEquals(string,actualString);
    }

    @Test
    void setTaskTitle() {
        Task task = new Task("TaskTitle",true);
        task.setTaskTitle("TaskTitleDifferent");
        assertEquals("TaskTitleDifferent",task.taskTitle);
    }

    @Test
    void emptyConstructor() {
        Task task = new Task();
        assertEquals(null,task.taskID);
        assertEquals(null,task.taskTitle);
        assertEquals(null,task.isCompleted);
        assertEquals(null,task.cardId);
        assertEquals(null,task.card);
    }
}