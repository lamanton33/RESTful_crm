package commons;

import javax.persistence.*;
import java.util.*;

@Entity
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int themeID;
    public String backgroundColor;
    public String cardColor;
    public String textColor;

    public Theme() {

    }

    public Theme(int themeID, String backgroundColor, String cardColor, String textColor) {
        this.themeID = themeID;
        this.backgroundColor = backgroundColor;
        this.cardColor = cardColor;
        this.textColor = textColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Theme theme = (Theme) o;
        return themeID == theme.themeID && Objects.equals(backgroundColor, theme.backgroundColor)
                && Objects.equals(cardColor, theme.cardColor) && Objects.equals(textColor, theme.textColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(themeID, backgroundColor, cardColor, textColor);
    }

    @Override
    public String toString() {
        return "Theme{" +
                "themeID=" + themeID +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", cardColor='" + cardColor + '\'' +
                ", textColor='" + textColor + '\'' +
                '}';
    }
}
