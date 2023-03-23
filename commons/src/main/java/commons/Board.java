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

    /**
     * Sets the board theme
     */
    public void setBoardTheme(Theme boardTheme) {
        this.boardTheme = boardTheme;
    }
}


