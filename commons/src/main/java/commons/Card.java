package commons;

import javax.persistence.*;
import java.util.*;

@Entity
public class Card{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int cardID;
    public String cardTitle;
    public String cardDescription;
    @OneToMany(cascade = CascadeType.PERSIST)
    public List<Task> taskList;
    @OneToMany(cascade = CascadeType.PERSIST)
    public List<Tag> tagList;

    public Card() {

    }

    public Card(int cardID, String cardTitle, String cardDescription, List<Task> taskList, List<Tag> tagList) {
        this.cardID = cardID;
        this.cardTitle = cardTitle;
        this.cardDescription = cardDescription;
        this.taskList = taskList;
        this.tagList = tagList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardID == card.cardID && Objects.equals(cardTitle, card.cardTitle)
                && Objects.equals(cardDescription, card.cardDescription)
                && Objects.equals(taskList, card.taskList)
                && Objects.equals(tagList, card.tagList);
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
                ", taskList=" + taskList +
                ", tagList=" + tagList +
                '}';
    }
}
