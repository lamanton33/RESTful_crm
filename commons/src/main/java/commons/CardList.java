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

    public CardList(int cardListID, String cardListTitle, List<Card> cardList) {
        this.cardListID = cardListID;
        this.cardListTitle = cardListTitle;
        this.cardList = cardList;
    }

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
                '}';
    }
}
