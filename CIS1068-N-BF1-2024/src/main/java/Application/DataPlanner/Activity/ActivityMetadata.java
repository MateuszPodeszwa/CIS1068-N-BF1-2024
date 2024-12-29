package Application.DataPlanner.Activity;

import Utils.Randomiser.GenerateReferenceNumber;

import java.time.LocalDateTime;

import static Application.DataPlanner.Activity.Activity.Location;

/*
* It is a plain data holder object that contains all basics attribute needed to create an Activity. All information stored here.
* */
public record ActivityMetadata(
        String referenceNumber, // General Activity assigned code, each activity type has its own ID - Helps to assign activity type
        String readableName,
        String description,
        Location location, // Enum
        LocalDateTime dateAndTime,
        int baseCostInPence,
        int durationInMinutes,
        boolean isInsuranceMandatory) {
    public ActivityMetadata {
        if (durationInMinutes < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
    }
}
