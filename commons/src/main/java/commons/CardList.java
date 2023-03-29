package commons;


import commons.utils.IDGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
public class CardList {

    @Id
    public UUID cardListID;
    public String cardListTitle;

    @OneToMany(cascade = CascadeType.PERSIST)
    public List<Card> cardList;

    @ManyToOne
    public Board board;

    public CardList() {

    }


    public CardList(String cardListTitle, List<Card> cardList, Board board) {
        this.cardListTitle = cardListTitle;
        this.cardList = cardList;
        this.board = board;
    }

    /**
     * Sets the cardList title to String cardListTitle
     */
    public void setCardListTitle(String cardListTitle) {
        this.cardListTitle = cardListTitle;
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
    public UUID getCardListID() {
        return cardListID;
    }

    public void setCardListID(UUID cardListID) {
        this.cardListID = cardListID;
    }

    /** Getter for the list of cards
     * @return List<Card>
     */
    public List<Card> getCardList() {
        return cardList;
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
        return cardListID == cardList1.cardListID && Objects.equals(cardListTitle, cardList1.cardListTitle)
                && Objects.equals(cardList, cardList1.cardList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardListID, cardListTitle, cardList);
    }

    @Override
    public String toString() {
        return "CardList{" +
                "cardListID=" + cardListID +
                ", cardListTitle='" + cardListTitle + '\'' +
                ", cardList=" + cardList +
                 ", board" + board.getBoardID() +
                '}';
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
