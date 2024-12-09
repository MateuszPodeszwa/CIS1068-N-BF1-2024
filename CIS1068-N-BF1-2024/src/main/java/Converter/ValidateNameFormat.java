package Converter;

/**
 * The {@code ValidateNameFormat} class provides functionality for validating and formatting
 * a given person's name. It allows setting a character limit on the final formatted name
 * and handles the conversion of the provided first and last name into a consistent and
 * standard format, with the first letter capitalised and subsequent letters in lowercase.
 *
 * <p>
 * Usage example:
 * <pre>{@code
 * ValidateNameFormat formatter = ValidateNameFormat.withName("john", "doe").setLimit(10);
 * String formatted = formatter.getName(); // "J Doe"
 * boolean limitReached = formatter.isTheLimitReached; // true if trimmed
 * }</pre>
 */
public class ValidateNameFormat {

    /** The formatted name after applying the given rules. */
    private String formattedName;

    /** The maximum length limit for the formatted name. Defaults to no limit. */
    private int limit = Integer.MAX_VALUE;

    /** Indicates if the limit has been reached and the name was trimmed. */
    public boolean isTheLimitReached = false;

    /**
     * Private constructor to prevent direct instantiation.
     * Use static methods {@link #withName(String, String)} or {@link #withName(String)}
     * to create an instance.
     */
    private ValidateNameFormat() {}

    /**
     * Sets a limit on the maximum length of the formatted name and returns a new instance.
     *
     * @param limit the maximum number of characters allowed for the formatted name
     * @return a new {@code ValidateNameFormat} instance with the specified limit
     */
    public static ValidateNameFormat setLimit(int limit) {
        ValidateNameFormat instance = new ValidateNameFormat();
        instance.limit = limit;
        return instance;
    }

    /**
     * Sets the full name provided as a single string. The name is expected
     * to contain two words: first name and last name.
     *
     * @param fullName the full name as a single {@code String}, e.g. "John Doe"
     * @return this {@code ValidateNameFormat} instance
     * @throws UnsupportedOperationException if the provided name is invalid or too short
     */
    public ValidateNameFormat setName(String fullName) {
        return withName(fullName);
    }

    /**
     * Creates a new {@code ValidateNameFormat} instance by parsing the provided full name
     * string. The name should contain exactly two words separated by a space, representing
     * the first and last name.
     *
     * @param fullName the full name as a single {@code String}, e.g. "John Doe"
     * @return a new {@code ValidateNameFormat} instance with the given name
     * @throws UnsupportedOperationException if the provided name is invalid or too short
     */
    public static ValidateNameFormat withName(String fullName) {
        ValidateNameFormat instance = new ValidateNameFormat();
        String[] spitedName = fullName.split(" ");
        return instance.setName(spitedName[0], spitedName[1]);
    }

    /**
     * Creates a new {@code ValidateNameFormat} instance by setting the provided first and last names.
     * Both names must be at least two characters long.
     *
     * @param firstName the first name, e.g. "John"
     * @param lastName the last name, e.g. "Doe"
     * @return a new {@code ValidateNameFormat} instance with the given first and last names
     * @throws UnsupportedOperationException if either {@code firstName} or {@code lastName}
     *         is {@code null} or too short
     */
    public static ValidateNameFormat withName(String firstName, String lastName) {
        ValidateNameFormat instance = new ValidateNameFormat();
        return instance.setName(firstName, lastName);
    }

    /**
     * Sets the provided first and last names after validating and formatting them.
     * The formatted name will have the first character of the first name capitalised
     * and the first character of the last name capitalised, with the remaining letters
     * in lowercase. If the resulting formatted name exceeds the set limit, it will be trimmed.
     *
     * @param firstName the first name to validate and format
     * @param lastName the last name to validate and format
     * @return this {@code ValidateNameFormat} instance with the updated formatted name
     * @throws UnsupportedOperationException if {@code firstName} or {@code lastName}
     *         is {@code null} or shorter than two characters
     */
    public ValidateNameFormat setName(String firstName, String lastName) {
        if (firstName == null || firstName.length() < 2)
            throw new UnsupportedOperationException("firstName is null or too short");
        if (lastName == null || lastName.length() < 2)
            throw new UnsupportedOperationException("lastName is null or too short");

        String firstNameInitial = firstName.trim().substring(0, 1).toUpperCase();
        String lastNameFormatted = lastName.trim().substring(0, 1).toUpperCase()
                + lastName.trim().substring(1).toLowerCase();
        String name = firstNameInitial + " " + lastNameFormatted;

        if (name.length() > limit) {
            formattedName = name.substring(0, limit);
            isTheLimitReached = true;
        } else {
            formattedName = name;
            isTheLimitReached = false;
        }
        return this;
    }

    /**
     * Returns the formatted name. If a limit was specified and reached,
     * the returned name may be truncated.
     *
     * @return the formatted name as a {@code String}
     */
    public String getName() {
        return formattedName;
    }
}
