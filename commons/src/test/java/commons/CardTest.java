package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private Card cardEmpty;
    private Card cardWithTitleAndDescription;
    private Card cardWithTasks;
    private Card cardWithTags;
    private Card cardWithEverything;
    private Card cardWithEverythingDuplicate;

    @BeforeEach
    private void setup() {
        //Initializing tasks
        Task taskEmpty = new Task("", false);
        Task taskUncompleted = new Task("taskUncompleted", false);
        Task taskCompleted = new Task("taskCompleted", true);
        List<Task> taskList = new ArrayList<>();
        taskList.add(taskEmpty);
        taskList.add(taskUncompleted);
        taskList.add(taskCompleted);

        //Initializing tags
        Tag tagEmpty = new Tag( "", "");
        Tag tagRed = new Tag("tagRed", "red");
        Tag tagBlue = new Tag( "tagBlue", "blue");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tagEmpty);
        tagList.add(tagRed);
        tagList.add(tagBlue);

        //Initializing cards
        cardEmpty = new Card("", "", new ArrayList<>(), new ArrayList<>());
        cardWithTitleAndDescription = new Card("cardTitle", "cardDescription",
                new ArrayList<>(), new ArrayList<>());
        cardWithTasks = new Card("", "", taskList, new ArrayList<>());
        cardWithTags = new Card("", "", new ArrayList<>(), tagList);
        cardWithEverything = new Card( "cardTitle", "cardDescription", taskList, tagList);
        cardWithEverythingDuplicate = new Card( "cardTitle", "cardDescription", taskList,
                tagList);

    }

    @Test
    void testEquals() {
        assertEquals(cardWithEverything,cardWithEverythingDuplicate);
        assertNotEquals(cardWithTitleAndDescription,cardEmpty);
        assertNotEquals(cardWithTitleAndDescription,cardWithTasks);
        assertNotEquals(cardWithTasks,cardWithTags);
        assertNotEquals(cardEmpty,cardWithTags);
        assertNotEquals(cardEmpty,cardWithTasks);
        assertNotEquals(cardEmpty,cardWithTitleAndDescription);
        assertNotEquals(cardWithEverything,cardWithTitleAndDescription);
    }

    @Test
    void testHashCode() {
        assertEquals(cardWithEverything.hashCode(),cardWithEverythingDuplicate.hashCode());
        assertNotEquals(cardWithTitleAndDescription.hashCode(),cardEmpty.hashCode());
        assertNotEquals(cardWithTitleAndDescription.hashCode(),cardWithTasks.hashCode());
        assertNotEquals(cardWithTasks.hashCode(),cardWithTags.hashCode());
        assertNotEquals(cardEmpty.hashCode(),cardWithTags.hashCode());
        assertNotEquals(cardEmpty.hashCode(),cardWithTasks.hashCode());
        assertNotEquals(cardEmpty.hashCode(),cardWithTitleAndDescription.hashCode());
        assertNotEquals(cardWithEverything.hashCode(),cardWithTitleAndDescription.hashCode());
    }

    @Test
    void testToString() {
        String actualString = cardWithEverything.toString();
        String string = "Card{cardID=5, cardTitle='cardTitle', cardDescription='cardDescription', " +
                "taskList=[Task{taskID=1, taskTitle='', isCompleted=false}, " +
                "Task{taskID=2, taskTitle='taskUncompleted', isCompleted=false}, " +
                "Task{taskID=3, taskTitle='taskCompleted', isCompleted=true}], " +
                "tagList=[Tag{tagID=1, tagTitle='', tagColor=''}, Tag{tagID=2, tagTitle='tagRed', tagColor='red'}, " +
                "Tag{tagID=3, tagTitle='tagBlue', tagColor='blue'}]}";
        assertEquals(string,actualString);
    }
}
