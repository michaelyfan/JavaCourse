package roman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RomanNumeralConverterTest {

    private RomanNumeralConverter converter;

    @BeforeEach
    void setup() {
        converter = new RomanNumeralConverter();
    }

    @Test
    void testBasic() {
        assertEquals("IV", converter.toRoman(4));
    }

    @ParameterizedTest(name = "{1} expected for {0}")
    @CsvSource({
            "IV, 4",
            "IX, 9",
            "XIII, 13",
            "XL, 40",
            "XC, 90",
            "CD, 400",
            "CMI, 901",
            "MCMXCIV, 1994",
            "MMMDCCCLXXXVIII, 3888"
    })
    void testMultiple(String expected, int input) {
        assertEquals(expected, converter.toRoman(input));
    }
}