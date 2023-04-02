package commons;


import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import java.util.Objects;
import java.util.*;

@Entity
public class Tag {

    @Id
    public UUID tagID;
    public String tagTitle;
    public String tagColor;
    public UUID cardId;
    @ManyToOne
    @JsonIgnore
    public Card card;

    public Tag() {

    }
    public Tag(UUID tagID,
               String tagTitle,
               String tagColor,
               UUID cardId,
               Card card) {
        this.tagID = tagID;
        this.tagTitle = tagTitle;
        this.tagColor = tagColor;
        this.cardId = cardId;
        this.card = card;
    }
    public Tag(String tagTitle,
               String tagColor,
               UUID cardId,
               Card card) {
        this.tagTitle = tagTitle;
        this.tagColor = tagColor;
        this.cardId = cardId;
        this.card = card;
    }
    public Tag(UUID tagID,
               String tagTitle,
               String tagColor) {
        this.tagID = tagID;
        this.tagTitle = tagTitle;
        this.tagColor = tagColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tag tag = (Tag) o;
        return tagID == tag.tagID && Objects.equals(tagTitle, tag.tagTitle) && Objects.equals(tagColor, tag.tagColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagID, tagTitle, tagColor);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagID=" + tagID +
                ", tagTitle='" + tagTitle + '\'' +
                ", tagColor='" + tagColor + '\'' +
                '}';
    }

    /**
     * Sets color of tag
     * @param tagColor
     */
    public void setTagColor(String tagColor) {
        this.tagColor = tagColor;
    }

    /**
     * Sets tag title
     * @param tagTitle
     */
    public void setTagTitle(String tagTitle) {
        this.tagTitle = tagTitle;
    }
}
