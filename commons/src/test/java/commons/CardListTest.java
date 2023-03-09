package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardListTest {
    private Card cardEmpty;
    private Card cardWithTitleAndDescription;
    private Card cardWithTasks;
    private Card cardWithTags;
    private Card cardWithEverything;
    private Card cardWithEverythingDuplicate;

    @BeforeEach
    private void setup() {
        //Initializing tasks
        Task taskEmpty = new Task(1, "", false);
        Task taskUncompleted = new Task(2, "taskUncompleted", false);
        Task taskCompleted = new Task(3, "taskCompleted", true);
        List<Task> taskList = new ArrayList<>();
        taskList.add(taskEmpty);
        taskList.add(taskUncompleted);
        taskList.add(taskCompleted);

        //Initializing tags
        Tag tagEmpty = new Tag(1, "", "");
        Tag tagRed = new Tag(2, "tagRed", "red");
        Tag tagBlue = new Tag(3, "tagBlue", "blue");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tagEmpty);
        tagList.add(tagRed);
        tagList.add(tagBlue);

        //Initializing cards
        cardEmpty = new Card(1, "", "", new ArrayList<>(), new ArrayList<>());
        cardWithTitleAndDescription = new Card(2, "cardTitle", "cardDescription",
                new ArrayList<>(), new ArrayList<>());
        cardWithTasks = new Card(3, "", "", taskList, new ArrayList<>());
        cardWithTags = new Card(4, "", "", new ArrayList<>(), tagList);
        cardWithEverything = new Card(5, "cardTitle", "cardDescription", taskList, tagList);
        cardWithEverythingDuplicate = new Card(5, "cardTitle", "cardDescription", taskList,
                tagList);

    }
        @Test
    void testEquals() {
            CardList cardListA = new CardList(1,"cardListTitle",new ArrayList<>());
            CardList cardListB = new CardList(1,"cardListTitle", new ArrayList<>());
            CardList cardListC = new CardList(2,"cardListTitle", new ArrayList<>());
            CardList cardListD = new CardList(3,"cardListTitle", new ArrayList<>());
            CardList cardListE = new CardList(3,"cardListTitle", new ArrayList<>());

            assertEquals(cardListA,cardListB);

            cardListA.addCard(cardWithEverything);
            assertNotEquals(cardListA,cardListB);

            cardListB.addCard(cardWithEverythingDuplicate);
            assertEquals(cardListA,cardListB);

            cardListC.addCard(cardWithEverything);
            assertNotEquals(cardListB,cardListC);

            cardListB.addCard(cardWithTasks);
            cardListA.addCard(cardWithTags);
            assertNotEquals(cardListA,cardListB);

            cardListD.addCard(cardEmpty);
            cardListE.addCard(cardEmpty);
            assertEquals(cardListD,cardListE);

            cardListE.addCard(cardWithTitleAndDescription);
            assertNotEquals(cardListD,cardListE);
        }

    @Test
    void testHashCode() {
        CardList cardListA = new CardList(1,"cardListTitle",new ArrayList<>());
        CardList cardListB = new CardList(1,"cardListTitle", new ArrayList<>());
        CardList cardListC = new CardList(2,"cardListTitle", new ArrayList<>());
        CardList cardListD = new CardList(3,"cardListTitle", new ArrayList<>());
        CardList cardListE = new CardList(3,"cardListTitle", new ArrayList<>());

        assertEquals(cardListA.hashCode(),cardListB.hashCode());

        cardListA.addCard(cardWithEverything);
        assertNotEquals(cardListA.hashCode(),cardListB.hashCode());

        cardListB.addCard(cardWithEverythingDuplicate);
        assertEquals(cardListA.hashCode(),cardListB.hashCode());

        cardListC.addCard(cardWithEverything);
        assertNotEquals(cardListB.hashCode(),cardListC.hashCode());

        cardListB.addCard(cardWithTasks);
        cardListA.addCard(cardWithTags);
        assertNotEquals(cardListA.hashCode(),cardListB.hashCode());

        cardListD.addCard(cardEmpty);
        cardListE.addCard(cardEmpty);
        assertEquals(cardListD.hashCode(),cardListE.hashCode());

        cardListE.addCard(cardWithTitleAndDescription);
        assertNotEquals(cardListD.hashCode(),cardListE.hashCode());
    }

    @Test
    void testToString() {
        CardList cardList = new CardList(1,"cardListTitle",new ArrayList<>());
        cardList.addCard(cardEmpty);
        cardList.addCard(cardWithTitleAndDescription);
        cardList.addCard(cardWithTasks);
        cardList.addCard(cardWithTags);
        cardList.addCard(cardWithEverything);
        cardList.addCard(cardWithEverythingDuplicate);
        String actualString = cardList.toString();

        String string ="CardList{, cardListTitle='cardListTitle', cardList=[Card{cardID=1, cardTitle='', cardDescription='', " +
                "taskList=[], tagList=[]}, Card{cardID=2, cardTitle='cardTitle', cardDescription='cardDescription', " +
                "taskList=[], tagList=[]}, Card{cardID=3, cardTitle='', cardDescription='', taskList=[Task{taskID=1, taskTitle='', isCompleted=false}, " +
                "Task{taskID=2, taskTitle='taskUncompleted', isCompleted=false}, Task{taskID=3, taskTitle='taskCompleted', isCompleted=true}], " +
                "tagList=[]}, Card{cardID=4, cardTitle='', cardDescription='', taskList=[], tagList=[Tag{tagID=1, tagTitle='', tagColor=''}, " +
                "Tag{tagID=2, tagTitle='tagRed', tagColor='red'}, Tag{tagID=3, tagTitle='tagBlue', tagColor='blue'}]}, " +
                "Card{cardID=5, cardTitle='cardTitle', cardDescription='cardDescription', taskList=[Task{taskID=1, taskTitle='', isCompleted=false}, " +
                "Task{taskID=2, taskTitle='taskUncompleted', isCompleted=false}, Task{taskID=3, taskTitle='taskCompleted', isCompleted=true}], " +
                "tagList=[Tag{tagID=1, tagTitle='', tagColor=''}, Tag{tagID=2, tagTitle='tagRed', tagColor='red'}, Tag{tagID=3, tagTitle='tagBlue', tagColor='blue'}]}, " +
                "Card{cardID=5, cardTitle='cardTitle', cardDescription='cardDescription', taskList=[Task{taskID=1, taskTitle='', isCompleted=false}, " +
                "Task{taskID=2, taskTitle='taskUncompleted', isCompleted=false}, Task{taskID=3, taskTitle='taskCompleted', isCompleted=true}]," +
                " tagList=[Tag{tagID=1, tagTitle='', tagColor=''}, Tag{tagID=2, tagTitle='tagRed', tagColor='red'}, Tag{tagID=3, tagTitle='tagBlue', tagColor='blue'}]}]}";
        assertEquals(string,actualString);
    }
}