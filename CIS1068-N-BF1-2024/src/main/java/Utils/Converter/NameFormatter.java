package Utils.Converter;

import java.util.Objects;

/**
 * The {@code NameFormatter} class provides functionality for validating and formatting
 * a person's name. It allows setting a character limit on the final formatted name
 * and ensures a consistent, standard format:
 *
 * - First name: first letter capitalised, single initial only.
 * - Last name: first letter capitalised, remaining letters lowercased.
 * - Optionally apply a length limit, truncating if exceeded.
 *
 * <p>
 * Usage:
 * <pre>{@code
 * NameFormatter formatter = NameFormatter.create()
 *     .withName("john", "doe")
 *     .withLimit(10);
 * String formatted = formatter.getName(); // "J Doe"
 * boolean truncated = formatter.isLimitReached(); // true if trimmed
 * }</pre>
 */
public final class NameFormatter {

    private String formattedName;
    private String unformattedName;
    private int limit = Integer.MAX_VALUE;
    private boolean limitReached = false;

    /**
     * Private constructor to enforce the use of static factory methods.
     */
    private NameFormatter() {
        // Use static factory methods.
    }

    /**
     * Creates a new {@code NameFormatter} instance.
     *
     * @return a new {@code NameFormatter}
     */
    public static NameFormatter create() {
        return new NameFormatter();
    }

    /**
     * Sets a limit on the maximum length of the formatted name.
     * If the formatted name exceeds this limit, it will be truncated.
     *
     * @param limit the maximum number of characters allowed
     * @return this {@code NameFormatter} instance for chaining
     * @throws IllegalArgumentException if limit is less than 1
     */
    public NameFormatter withLimit(int limit) {
        if (limit < 1) {
            throw new IllegalArgumentException("Limit must be at least 1");
        }
        this.limit = limit;
        applyLimitIfNecessary();
        return this;
    }

    /**
     * Sets the name using a full name string, expecting exactly two words:
     * first name and last name separated by a space.
     *
     * @param fullName the full name, e.g. "John Doe"
     * @return this {@code NameFormatter} instance for chaining
     * @throws IllegalArgumentException if the name is invalid or doesn't contain exactly two parts
     */
    public NameFormatter withName(String fullName) {
        Objects.requireNonNull(fullName, "Full name cannot be null");
        String[] parts = fullName.trim().split("\\s+");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Full name must contain exactly two words: first and last name");
        }
        return withName(parts[0], parts[1]);
    }

    /**
     * Sets the name using separate first and last names.
     *
     * @param firstName the first name, e.g. "John"
     * @param lastName  the last name, e.g. "Doe"
     * @return this {@code NameFormatter} instance for chaining
     * @throws IllegalArgumentException if firstName or lastName is invalid
     */
    public NameFormatter withName(String firstName, String lastName) {
        validateNamePart(firstName, "First name");
        validateNamePart(lastName, "Last name");

        String fnInitial = firstName.trim().substring(0, 1).toUpperCase();
        String lnFormatted = lastName.trim().substring(0, 1).toUpperCase()
                + lastName.trim().substring(1).toLowerCase();
        this.formattedName = fnInitial + " " + lnFormatted;

        this.unformattedName = firstName + " " + lastName;
        applyLimitIfNecessary();
        return this;
    }

    /**
     * Returns the formatted name. If a limit was set and the name
     * exceeded that limit, the returned name is truncated.
     *
     * @return the formatted name as a {@code String}
     */
    public String getName() {
        return formattedName;
    }

    public String unformattedName() {
        return unformattedName;
    }

    /**
     * Returns true if the name was truncated to fit the given limit.
     *
     * @return true if the limit was reached and truncation occurred, false otherwise
     */
    public boolean isLimitReached() {
        return limitReached;
    }

    private void validateNamePart(String namePart, String fieldName) {
        if (namePart == null || namePart.trim().length() < 2) {
            throw new IllegalArgumentException(fieldName + " must be at least two characters long");
        }
    }

    private void applyLimitIfNecessary() {
        if (formattedName != null && formattedName.length() > limit) {
            formattedName = formattedName.substring(0, limit);
            limitReached = true;
        } else {
            limitReached = false;
        }
    }
}
