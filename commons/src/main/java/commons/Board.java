package commons;



import javax.persistence.*;
import java.util.*;


@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int boardID;
    public String boardTitle;
    @OneToMany(cascade = CascadeType.PERSIST)
    public List<CardList> cardListList;
    public String description;
    public Boolean isProtected;
    public String passwordHash;
    @ManyToOne(cascade = CascadeType.PERSIST)
    public Theme boardTheme;

    public Board() {

    }
    public Board(String boardTitle, int boardID, List<CardList> cardListList, String description, Boolean isProtected,
                 String passwordHash, Theme boardTheme) {
        this.boardTitle = boardTitle;
        this.boardID = boardID;
        this.cardListList = cardListList;
        this.description = description;
        this.isProtected = isProtected;
        this.passwordHash = passwordHash;
        this.boardTheme = boardTheme;
    }


    public void addCardList(CardList cardList){
        cardListList.add(cardList);
    }


    public List<CardList> getCardList() {
        return cardListList;
    }

    public static Board createDummyBoard(){
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

        Theme theme = new Theme(1,"backgroundColor","cardColor","textColor");

        //Initializing card list of lists

        List<CardList> cardListWithCardsList = new ArrayList<>();
        cardListWithCardsList.add(cardListWithCards);
        cardListWithCardsList.add(cardListEmpty);
        return new Board("boardTitle",2,cardListWithCardsList,"description",
                false,"passwordHash",theme);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return boardID == board.boardID && Objects.equals(boardTitle, board.boardTitle)
                && Objects.equals(cardListList, board.cardListList)
                && Objects.equals(description, board.description)
                && Objects.equals(isProtected, board.isProtected)
                && Objects.equals(passwordHash, board.passwordHash)
                && Objects.equals(boardTheme, board.boardTheme);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardID, boardTitle, cardListList, description, isProtected, passwordHash, boardTheme);
    }

    @Override
    public String toString() {
        return "Board{" +
                "boardID=" + boardID +
                ", boardTitle='" + boardTitle + '\'' +
                ", cardListList=" + cardListList +
                ", description='" + description + '\'' +
                ", isProtected=" + isProtected +
                ", passwordHash='" + passwordHash + '\'' +
                ", boardTheme=" + boardTheme +
                '}';
    }


}


