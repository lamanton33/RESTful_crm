package commons;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ResultTest {

    Result<String> result1 = new Result<String>(5, "this is a result", true,"the value");

    @Test
    void of() {
        var result2 = Result.SUCCESS.of("This is a value");

        assertEquals(new Result<>(0, "Operation was successful", true, "This is a value"), result2);
    }

    @Test
    void testEqualsComparesCorrectly() {
        var result2 = new Result<String>(5, "this is a result", true,"the value");

        assertEquals(result1, result2);
    }

    @Test
    void testEqualsFindsInequality() {
        var result2 = new Result<String>(5, "this is not a result", false,"the value");

        assertNotEquals(result1, result2);
    }

    @Test
    void testEqualsFindsInequalityInValue() {
        var result2 = new Result<String>(5, "this is a result", true,"a different value");

        assertNotEquals(result1, result2);
    }

    @Test
    void testHashCode() {
        assertEquals(-765908567, result1.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Result{success=true, code=5, message='this is a result', value=the value}", result1.toString());
    }
}