package roman;

public class RomanNumeralConverter {

    // Parallel arrays mapping Arabic values to Roman numerals in descending order
    private static final int[] VALUES = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] SYMBOLS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public String toRoman(int n) {
        if (n < 1 || n > 3999) {
            throw new IllegalArgumentException("Input must be greater than 0 and less than 4000");
        }

        StringBuilder sb = new StringBuilder();

        // Loop through the values from largest to smallest
        for (int i = 0; i < VALUES.length; i++) {
            // While the current value can be subtracted from n, append its symbol
            while (n >= VALUES[i]) {
                sb.append(SYMBOLS[i]);
                n -= VALUES[i];
            }
        }

        return sb.toString();
    }

}
