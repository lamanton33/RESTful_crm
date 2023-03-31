package commons;

import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class Card {

    @Id
    public UUID cardID;
    public String cardTitle;
    public String cardDescription;
    @OneToMany(cascade = CascadeType.PERSIST)
    public List<Task> taskList;
    @OneToMany(cascade = CascadeType.PERSIST)
    public List<Tag> tagList;
    public UUID cardListId;

    @ManyToOne
    @JsonIgnore
    public CardList cardList;

    public Card() {

    }


    public Card(UUID cardID,
                CardList cardList,
                String cardTitle,
                String cardDescription,
                List<Task> taskList,
                List<Tag> tagList) {
        this.cardList = cardList;
        this.cardID = cardID;
        this.cardTitle = cardTitle;
        this.cardDescription = cardDescription;
        this.taskList = taskList;
        this.tagList = tagList;
        this.cardListId = cardList.cardListId;

    }

    /**
     * Sets the title of Card
     * @param cardTitle
     */
    public void setCardTitle(String cardTitle){
        this.cardTitle = cardTitle;
    }

    /** getter for cardID
     * @return cardID
     */
    public UUID getCardID() {
        return cardID;
    }

    /** Setter for cardId
     * @param cardID
     */
    public void setCardID(UUID cardID) {
        this.cardID = cardID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardID, card.cardID) && Objects.equals(cardTitle, card.cardTitle) &&
                Objects.equals(cardDescription, card.cardDescription) && Objects.equals(taskList, card.taskList) &&
                Objects.equals(tagList, card.tagList) && Objects.equals(cardListId, card.cardListId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardID, cardTitle, cardDescription, taskList, tagList);
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardID=" + cardID +
                ", cardTitle='" + cardTitle + '\'' +
                ", cardDescription='" + cardDescription + '\'' +
                ", taskList=" + taskList.toString() +
                ", tagList=" + tagList.toString() +
                '}';
    }
}
