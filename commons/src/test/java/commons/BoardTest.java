package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private final List<CardList> cardListList = new ArrayList<>();
    private final Theme theme = new Theme(1,"backgroundColor","cardColor","textColor");

    @BeforeEach
    public void setup(){
        //Initializing tasks
        Task taskEmpty = new Task(1,"",false);
        Task taskUncompleted = new Task(2,"taskUncompleted",false);
        Task taskCompleted = new Task(3,"taskCompleted",true);
        List<Task> taskList = new ArrayList<>();
        taskList.add(taskEmpty);
        taskList.add(taskUncompleted);
        taskList.add(taskCompleted);

        //Initializing tags
        Tag tagEmpty = new Tag(1,"","");
        Tag tagRed = new Tag(2,"tagRed","red");
        Tag tagBlue = new Tag(3,"tagBlue","blue");
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tagEmpty);
        tagList.add(tagRed);
        tagList.add(tagBlue);

        //Initializing cards
        Card cardEmpty = new Card(1,"","",new ArrayList<>(),new ArrayList<>());
        Card cardWithTitleAndDescription = new Card(2,"cardTitle","cardDescription",
                new ArrayList<>(),new ArrayList<>());
        Card cardWithTasks = new Card(3,"","",taskList,new ArrayList<>());
        Card cardWithTags = new Card(4,"","",new ArrayList<>(),tagList);
        Card cardWithEverything = new Card(5,"cardTitle","cardDescription",taskList,tagList);
        Card cardWithEverythingDuplicate = new Card(5, "cardTitle", "cardDescription", taskList,
                tagList);
        //Initializing card lists
        CardList cardListWithCards = new CardList(1,"cardListWithCards",new ArrayList<>());
        CardList cardListEmpty = new CardList(2,"cardListEmpty", new ArrayList<>());
        cardListWithCards.addCard(cardEmpty);
        cardListWithCards.addCard(cardWithTitleAndDescription);
        cardListWithCards.addCard(cardWithTasks);
        cardListWithCards.addCard(cardWithTags);
        cardListWithCards.addCard(cardWithEverything);
        cardListWithCards.addCard(cardWithEverythingDuplicate);

        //Initializing card list of lists
        cardListList.add(cardListEmpty);
        cardListList.add(cardListWithCards);
    }

    @Test
    void testGetDummyBoard(){
        Board boardA = Board.createDummyBoard();
        Board boardB = new Board("boardTitle",1,cardListList,"description",
                false,"passwordHash",theme);
        assertEquals(boardA.toString(),boardB.toString());
    }


    @Test
    void testEquals() {
        Board boardA = new Board("boardTitle",1,cardListList,"description",
                false,"passwordHash",theme);
        Board boardB = new Board("boardTitle",1,cardListList,"description",
                false,"passwordHash",theme);
        Board boardC = new Board("boardTitle",2,cardListList,"description",
                false,"passwordHash",theme);

        assertEquals(boardA,boardB);
        assertNotEquals(boardB,boardC);
    }

    @Test
    void testHashCode() {
        Board boardA = new Board("boardTitle",1,cardListList,"description",
                false,"passwordHash",theme);
        Board boardB = new Board("boardTitle",1,cardListList,"description",
                false,"passwordHash",theme);
        Board boardC = new Board("boardTitle",2,cardListList,"description",
                false,"passwordHash",theme);

        assertEquals(boardA.hashCode(),boardB.hashCode());
        assertNotEquals(boardB.hashCode(),boardC.hashCode());
    }

    @Test
    void testToString() {
        Board board = new Board("boardTitle",1,cardListList,"description",
                false,"passwordHash",theme);
        String actualString = board.toString();
        System.out.println(board.toString());
        String string = "Board{boardID=1, boardTitle='boardTitle', cardListList=[CardList{, cardListTitle='cardListEmpty', cardList=[]}, " +
                "CardList{, cardListTitle='cardListWithCards', cardList=[Card{cardID=1, cardTitle='', cardDescription='', taskList=[], tagList=[]}," +
                " Card{cardID=2, cardTitle='cardTitle', cardDescription='cardDescription', taskList=[], tagList=[]}, " +
                "Card{cardID=3, cardTitle='', cardDescription='', taskList=[Task{taskID=1, taskTitle='', isCompleted=false}, " +
                "Task{taskID=2, taskTitle='taskUncompleted', isCompleted=false}, Task{taskID=3, taskTitle='taskCompleted', isCompleted=true}], tagList=[]}," +
                " Card{cardID=4, cardTitle='', cardDescription='', taskList=[], tagList=[Tag{tagID=1, tagTitle='', tagColor=''}, Tag{tagID=2, tagTitle='tagRed', tagColor='red'}," +
                " Tag{tagID=3, tagTitle='tagBlue', tagColor='blue'}]}, Card{cardID=5, cardTitle='cardTitle', cardDescription='cardDescription'," +
                " taskList=[Task{taskID=1, taskTitle='', isCompleted=false}, Task{taskID=2, taskTitle='taskUncompleted', isCompleted=false}, " +
                "Task{taskID=3, taskTitle='taskCompleted', isCompleted=true}], tagList=[Tag{tagID=1, tagTitle='', tagColor=''}, " +
                "Tag{tagID=2, tagTitle='tagRed', tagColor='red'}, Tag{tagID=3, tagTitle='tagBlue', tagColor='blue'}]}, " +
                "Card{cardID=5, cardTitle='cardTitle', cardDescription='cardDescription', taskList=[Task{taskID=1, taskTitle='', isCompleted=false}, " +
                "Task{taskID=2, taskTitle='taskUncompleted', isCompleted=false}, Task{taskID=3, taskTitle='taskCompleted', isCompleted=true}], tagList=[Tag{tagID=1, tagTitle='', tagColor=''}, " +
                "Tag{tagID=2, tagTitle='tagRed', tagColor='red'}, Tag{tagID=3, tagTitle='tagBlue', tagColor='blue'}]}]}], description='description', isProtected=false, passwordHash='passwordHash', " +
                "boardTheme=Theme{themeID=1, backgroundColor='backgroundColor', cardColor='cardColor', textColor='textColor'}}";
        assertEquals(string,actualString);
    }
}