package commons;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Tag {

    @Id
    public UUID tagID;
    public String tagTitle;
    public String tagColor;
    public int cardId;
    @ManyToOne
    @JsonIgnore
    public Card card;

    public Tag() {

    }
    public Tag(UUID tagID, String tagTitle, String tagColor) {
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
}
