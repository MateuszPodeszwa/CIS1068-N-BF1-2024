package Utils.Randomiser;

import java.util.Arrays;
import java.util.Random;

/**
 * The {@code GenerateUniqueNumber} class generates a unique numeric sequence of a specified length.
 * It provides methods to retrieve the generated number in various data types such as {@code int}, {@code long},
 * {@code double}, {@code char[]} array, and {@code String}.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 * GenerateUniqueNumber generator = new GenerateUniqueNumber(5);
 * int uniqueInt = generator.toInteger();
 * </pre>
 *
 * @author
 * Mateusz Podeszwa
 * @version 1.0
 * @since 2024
 */
public class GenerateUniqueNumber {

    /**
     * The maximum length of the random number sequence.
     */
    private final int sequenceLength;

    /**
     * The builder used to construct the unique number.
     */
    private UniqueNumberBuilder uniqueNumber;

    /**
     * Constructs a {@code GenerateUniqueNumber} object with a specified sequence length.
     * If the provided length is invalid, it defaults to 1.
     *
     * @param sequenceLength the length of the random number sequence to generate
     */
    public GenerateUniqueNumber(int sequenceLength) {
        if (sequenceLength > 0 && sequenceLength < Integer.MAX_VALUE)
            this.sequenceLength = sequenceLength;
        else
            this.sequenceLength = 1;

        generateUniqueNumber();
    }

    /**
     * Generates a single random digit between 0 and 9.
     *
     * @return a random digit between 0 and 9
     */
    private int generateUniqueDigit() {
        Random random = new Random();
        return random.nextInt(10);
    }

    /**
     * Generates the unique number sequence by appending random digits.
     */
    private void generateUniqueNumber() {
        uniqueNumber = new UniqueNumberBuilder(this.sequenceLength);

        for (int i = 0; i < this.sequenceLength; i++)
            uniqueNumber.append(generateUniqueDigit());
    }

    /**
     * Converts the generated unique number to an {@code int}.
     *
     * @return the unique number as an integer
     */
    public int toInteger() {
        return uniqueNumber.toInteger();
    }

    /**
     * Converts the generated unique number to a {@code long}.
     *
     * @return the unique number as a long
     */
    public long toLong() {
        return uniqueNumber.toLong();
    }

    /**
     * Converts the generated unique number to a {@code double}.
     *
     * @return the unique number as a double
     */
    public double toDouble() {
        return uniqueNumber.toDouble();
    }

    /**
     * Converts the generated unique number to a character array.
     *
     * @return the unique number as a character array
     */
    public char[] toArray() {
        return uniqueNumber.toArray();
    }

    /**
     * Returns a string representation of the unique number as an array.
     *
     * @return the unique number as a string array
     */
    public String toStringArray() {
        return Arrays.toString(uniqueNumber.toArray());
    }

    /**
     * Returns a string representation of the unique number.
     *
     * @return the unique number as a string
     */
    @Override
    public String toString() {
        return uniqueNumber.toString();
    }
}
