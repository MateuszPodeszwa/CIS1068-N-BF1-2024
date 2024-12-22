package Application.DataPlanner.Activity;

import Utils.Randomiser.GenerateReferenceNumber;

import java.time.LocalDateTime;

// Defines the contract for each activity
public abstract class Activity {
    // Each Activity must have defined:
    /*
    * 1. Base Cost
    * 2. Title
    * 3. Location
    * 4. Date and Time
    * 5. Expected Duration
    * */

    // The minimum Details for an activity are:
    /*
    * 1. Code
    * 2. Readable Name
    * 3. Description
    * 4. Date and Time
    * 5. Duration in Minutes
    * 6. Bool: isInsuranceMandatory?
    * 7. Base Cost in Pences
    * */

    /*
    * The base Activity class should assume the simplest scenario.
    * If every activity has at least a base cost and some standard fields, then no complexity is introduced here.
    * This ensures that Activity objects are easy to handle by the rest of the system (like itineraries and pricing).
    * */

    protected Activity(ActivityMetadata metadata) {
        this.code = metadata.referenceNumber().generateReferenceNumber('G'); // if referenceNumber generates a code
        this.readableName = metadata.readableName();
        this.description = metadata.description();
        this.location = metadata.location();
        this.dateAndTime = metadata.dateAndTime();
        this.baseCostInPence = metadata.baseCostInPence();
        this.durationInMinutes = metadata.durationInMinutes();
        this.isInsuranceMandatory = metadata.isInsuranceMandatory();
    }


    public enum Location {
        HOME,
        OFFICE,
        OTHER;
    }

    private String code;
    private String readableName;
    private String description;
    private Location location;
    private LocalDateTime dateAndTime;
    private int baseCostInPence;
    private int durationInMinutes;
    private boolean isInsuranceMandatory;
}
