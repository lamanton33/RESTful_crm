package commons;

import javax.persistence.*;
import java.util.*;

@Entity
public class CardList {

    /**
     * Sets the cardList title to String cardListTitle
     */
    public void setCardListTitle(String cardListTitle) {
        this.cardListTitle = cardListTitle;
    }

    public int getCardListID() {
        return cardListID;
    }

    public String getCardListTitle() {
        return cardListTitle;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int cardListID;
    public String cardListTitle;
    @OneToMany(cascade = CascadeType.PERSIST)
    public List<Card> cardList;

    public CardList() {

    }

    public CardList(int id, String cardListTitle, List<Card> cardList) {
        this.cardListID = id;
        this.cardListTitle = cardListTitle;
        this.cardList = cardList;
    }

    public CardList(String cardListTitle, List<Card> cardList) {

        this.cardListTitle = cardListTitle;
        this.cardList = cardList;
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
                //"cardListID=" + cardListID +
                ", cardListTitle='" + cardListTitle + '\'' +
                ", cardList=" + cardList +
                '}';
    }
}
