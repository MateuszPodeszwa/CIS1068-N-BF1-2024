package Application.DataPlanner.Activity;

import Utils.Randomiser.GenerateReferenceNumber;

import java.time.LocalDateTime;

import static Application.DataPlanner.Activity.Activity.Location;

/*
* It is a plain data holder object that contains all basics attribute needed to create an Activity.
* */
public record ActivityMetadata(GenerateReferenceNumber referenceNumber, String readableName, String description, Location location, LocalDateTime dateAndTime, int baseCostInPence, int durationInMinutes, boolean isInsuranceMandatory) {
    public ActivityMetadata {
        if (durationInMinutes < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
    }
}
