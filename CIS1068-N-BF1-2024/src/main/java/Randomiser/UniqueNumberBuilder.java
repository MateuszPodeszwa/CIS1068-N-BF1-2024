package Randomiser;

/**
 * The {@code UniqueNumberBuilder} class assists in constructing a unique number sequence.
 * It provides methods to convert the constructed sequence into various numeric types.
 * <p>
 * <b>Example usage:</b>
 * <pre>
 * UniqueNumberBuilder builder = new UniqueNumberBuilder(5);
 * builder.append(1);
 * builder.append(2);
 * int number = builder.toInteger();
 * </pre>
 *
 * @author
 * Mateusz Podeszwa
 * @version 1.0
 * @since 2024
 */
public class UniqueNumberBuilder {

    /**
     * The underlying {@code StringBuilder} used to construct the number sequence.
     */
    private final StringBuilder stringBuilder;

    /**
     * Constructs a {@code UniqueNumberBuilder} with a specified initial capacity.
     *
     * @param capacity the initial capacity of the builder
     */
    public UniqueNumberBuilder(int capacity) {
        stringBuilder = new StringBuilder(capacity);
    }

    /**
     * Appends the string representation of the specified object to this builder.
     *
     * @param obj the object to append
     */
    public void append(Object obj) {
        stringBuilder.append(obj);
    }

    /**
     * Converts the constructed number sequence to an {@code int}.
     *
     * @return the number sequence as an integer, or -1 if conversion fails
     */
    public int toInteger() {
        try {
            return Integer.parseInt(this.stringBuilder.toString());
        } catch (NumberFormatException e) {
            System.out.println(e);
            return -1;
        }
    }

    /**
     * Converts the constructed number sequence to a {@code long}.
     *
     * @return the number sequence as a long, or -1L if conversion fails
     */
    public long toLong() {
        try {
            return Long.parseLong(stringBuilder.toString());
        } catch (NumberFormatException e) {
            System.out.println(e);
            return -1L;
        }
    }

    /**
     * Converts the constructed number sequence to a {@code double}.
     *
     * @return the number sequence as a double, or -1.0 if conversion fails
     */
    public double toDouble() {
        try {
            return Double.parseDouble(this.toString());
        } catch (NumberFormatException e) {
            System.out.println(e);
            return -1.0;
        }
    }

    /**
     * Converts the constructed number sequence to a character array.
     *
     * @return the number sequence as a character array
     */
    public char[] toArray() {
        return this.toString().toCharArray();
    }

    /**
     * Returns the string representation of the constructed number sequence.
     *
     * @return the number sequence as a string
     */
    @Override
    public String toString() {
        return stringBuilder.toString();
    }
}
