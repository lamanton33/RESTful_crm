package commons;


import javax.persistence.*;
import java.util.*;

@Entity
public class Card{



    @Id
    public UUID cardID;
    public String cardTitle;
    public String cardDescription;
    @OneToMany(cascade = CascadeType.PERSIST)
    public List<Task> taskList;
    @OneToMany(cascade = CascadeType.PERSIST)
    public List<Tag> tagList;

    public Card() {

    }

    public Card(String cardTitle){
        this.cardTitle = cardTitle;

    }
    public Card(String cardTitle, String cardDescription, List<Task> taskList, List<Tag> tagList) {
        this.cardTitle = cardTitle;
        this.cardDescription = cardDescription;
        this.taskList = taskList;
        this.tagList = tagList;
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


    public void setCardID(UUID cardID) {
        this.cardID = cardID;
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
                ", taskList=" + taskList.toString() +
                ", tagList=" + tagList.toString() +
                '}';
    }
}
