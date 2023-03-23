package commons;

import javax.persistence.*;
import java.util.*;

@Entity
public class CardList {

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

    /**
     * Sets the cardList title to String cardListTitle
     */
    public void setListTitle(String cardListTitle) {
        this.cardListTitle = cardListTitle;
    }

    /** Getter for the cardList title
     * @return cardListTitle
     */
    public String getListTitle() {
        return cardListTitle;
    }

    /** getter for the list ID
     * @return cardListID
     */
    public int getListID() {
        return cardListID;
    }

    /** Getter for the list of cards
     * @return List<Card>
     */
    public List<Card> getList() {
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
                ", cardList=" + cardList.toString() +
                '}';
    }
}
