package Converter;

public class ValidateNameFormat {

    private String formattedName;
    private int limit = Integer.MAX_VALUE; // Default limit to no limit
    public boolean isTheLimitReached = false;

    // Private constructor to prevent direct instantiation
    private ValidateNameFormat() {}

    // Static method to set the limit and return an instance
    public static ValidateNameFormat setLimit(int limit) {
        ValidateNameFormat instance = new ValidateNameFormat();
        instance.limit = limit;
        return instance;
    }

    // If provided a full name in one string
    public ValidateNameFormat setName(String fullName) {
        return withName(fullName);
    }

    // If provided a full name in one string
    public static ValidateNameFormat withName(String fullName) {
        ValidateNameFormat instance = new ValidateNameFormat();
        String[] spitedName = fullName.split(" ");
        return instance.setName(spitedName[0], spitedName[1]);
    }

    // Static method to create an instance and set the name, must be separated by a space, maximum 2 words
    public static ValidateNameFormat withName(String firstName, String lastName) {
        ValidateNameFormat instance = new ValidateNameFormat();
        return instance.setName(firstName, lastName);
    }

    // Instance method to set the name
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

    // Method to get the formatted name
    public String getName() {
        return formattedName;
    }
}
