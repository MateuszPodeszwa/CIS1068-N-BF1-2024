package Application.DataPlanner.Activity;

import Application.DataPlanner.Activity.ActivityAddons.AddOn;
import Utils.Randomiser.GenerateReferenceNumber;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static Application.DataPlanner.Activity.Activity.Location;

/*
* It is a plain data holder object that contains all basics attribute needed to create an Activity. All information stored here.
* */
public record ActivityMetadata
        (
            String referenceNumber, // General Activity assigned code, each activity type has its own ID - Helps to assign activity type
            String readableName,
            String description,
            Location location, // Enum
            LocalDateTime dateAndTime,
            int baseCostInPence,
            int durationInMinutes,
            boolean isInsuranceMandatory,
            ArrayList<AddOn> activityAddons
        )
{
    public ActivityMetadata {
        if (durationInMinutes < 0) {
            throw new IllegalArgumentException("Duration cannot be negative");
        }
        if (baseCostInPence < 0) {
            throw new IllegalArgumentException("Cost cannot be negative");
        }
        if (referenceNumber == null || referenceNumber.isBlank()) {
            throw new IllegalArgumentException("Reference number cannot be null or empty");
        }
        if  (activityAddons.size() > AddOn.MAX_ADDONS) {
            throw new IllegalArgumentException("Maximum number of addons reached");
        }
    }
}
