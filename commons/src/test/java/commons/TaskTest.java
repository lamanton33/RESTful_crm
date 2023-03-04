package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testEquals() {
        Task taskA = new Task(1,"TaskTitle",true);
        Task taskB = new Task(1,"TaskTitle",true);
        Task taskC = new Task(2,"TaskTitleDifferent",false);

        assertEquals(taskA,taskB);
        assertNotEquals(taskB,taskC);
    }

    @Test
    void testHashCode() {
        Task taskA = new Task(1,"TaskTitle",true);
        Task taskB = new Task(1,"TaskTitle",true);
        Task taskC = new Task(2,"TaskTitleDifferent",false);
        assertEquals(taskA.hashCode(),taskB.hashCode());
        assertNotEquals(taskB.hashCode(),taskC.hashCode());
    }

    @Test
    void testToString() {
        Task task = new Task(1,"TaskTitle",true);
        String actualString = task.toString();
        String string = "Task{taskID=1, taskTitle='TaskTitle', isCompleted=true}";
        assertEquals(string,actualString);
    }
}