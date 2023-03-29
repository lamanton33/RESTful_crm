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
        String string = "Task{taskID=1, taskTitle='TaskTitle', isCompleted=true}";
        assertEquals(string,actualString);
    }
}