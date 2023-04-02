package commons;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class CardList {

    @Id
    public UUID cardListId;
    public String cardListTitle;

    @OneToMany(cascade = CascadeType.PERSIST)
    public List<Card> cardList;

    public UUID boardId;

    @JsonIgnore
    @ManyToOne
    public Board board;

    public CardList() {

    }
    public CardList(UUID cardListId,
                    String cardListTitle,
                    List<Card> cardList,
                    UUID boardId,
                    Board board) {
        this.cardListId = cardListId;
        this.cardListTitle = cardListTitle;
        this.cardList = cardList;
        this.boardId = boardId;
        this.board = board;
    }
    public CardList(UUID cardListId,
                    String cardListTitle,
                    List<Card> cardList,
                    Board board) {
        this.cardListId = cardListId;
        this.cardListTitle = cardListTitle;
        this.cardList = cardList;
        this.board = board;
    }
    public CardList(String cardListTitle, List<Card> cardList, Board board) {
        this.cardListTitle = cardListTitle;
        this.cardList = cardList;
        this.board = board;
    }

    public CardList(String listTitle, List<Card> cardList) {
        this.cardListTitle = listTitle;
        this.cardList = cardList;
    }

    /** Getter for the cardList title
     * @return cardListTitle
     */
    public String getCardListTitle() {
        return cardListTitle;
    }

    /** getter for the list ID
     * @return cardListID
     */
    public UUID getCardListId() {
        return cardListId;
    }

    /** Setter for cardlistID
     * @param cardListID
     */
    public void setCardListId(UUID cardListID) {
        this.cardListId = cardListID;
    }

    /**
     * Setter for boardId
     * @param boardId
     */
    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }

    /** Getter for the list of cards
     * @return List<Card>
     */
    public List<Card> getCardList() {
        return cardList;
    }

    /** Setter for board
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }
    /**
     * Sets the cardList title to String cardListTitle
     */
    public void setCardListTitle(String cardListTitle) {
        this.cardListTitle = cardListTitle;
    }

    /** Adds cards to the internal cardList of the class, used for testing purposes only for now
     * @param card
     */
    public void addCard(Card card){
        cardList.add(card);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardList cardList1 = (CardList) o;
        return Objects.equals(cardListId, cardList1.cardListId) &&
                Objects.equals(cardListTitle, cardList1.cardListTitle) &&
                Objects.equals(cardList, cardList1.cardList) && Objects.equals(boardId, cardList1.boardId)
                && Objects.equals(board, cardList1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardListId, cardListTitle, cardList, boardId, board);
    }

    @Override
    public String toString() {
        return "CardList{" +
                "cardListId=" + cardListId +
                ", cardListTitle='" + cardListTitle + '\'' +
                ", cardList=" + cardList +
                ", boardId=" + boardId +
                '}';
    }
}
