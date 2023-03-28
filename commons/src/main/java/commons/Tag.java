package commons;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Tag{

    @Id
    public UUID tagID;
    public String tagTitle;
    public String tagColor;

    public Tag() {

    }
    public Tag(String tagTitle, String tagColor) {
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
