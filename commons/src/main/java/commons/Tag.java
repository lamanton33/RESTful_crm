package commons;


import javax.persistence.*;
import java.util.Objects;

@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int tagID;
    public String tagTitle;
    public String tagColor;

    public Tag() {

    }
    public Tag(int tagID, String tagTitle, String tagColor) {
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