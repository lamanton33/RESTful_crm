package commons;

        import org.junit.jupiter.api.Test;
        import static org.junit.jupiter.api.Assertions.*;

class ThemeTest {


    @Test
    void testEquals() {
        Theme themeA = new Theme(1,"backgroundColor","cardColor","textColor");
        Theme themeB = new Theme(1,"backgroundColor","cardColor","textColor");
        Theme themeC = new Theme(2,"backgroundColorDifferent", "cardColorDifferent",
                "textColorDifferent");

        assertEquals(themeA,themeB);
        assertNotEquals(themeB,themeC);
    }

    @Test
    void testHashCode() {
        Theme themeA = new Theme(1,"backgroundColor","cardColor","textColor");
        Theme themeB = new Theme(1,"backgroundColor","cardColor","textColor");
        Theme themeC = new Theme(2,"backgroundColorDifferent", "cardColorDifferent",
                "textColorDifferent");
        assertEquals(themeA.hashCode(),themeB.hashCode());
        assertNotEquals(themeB.hashCode(),themeC.hashCode());
    }

    @Test
    void testToString() {
        Theme theme = new Theme(1,"backgroundColor","cardColor","textColor");

        String actualString = theme.toString();
        String string = "Theme{themeID=1, backgroundColor='backgroundColor', cardColor='cardColor', " +
                        "textColor='textColor'}";
        assertEquals(string,actualString);
    }
}