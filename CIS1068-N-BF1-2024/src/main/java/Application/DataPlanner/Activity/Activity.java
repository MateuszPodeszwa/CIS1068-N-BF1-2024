package Application.DataPlanner.Activity;

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

    public Activity(ActivityMetadata metadata) {
        this.code = metadata.referenceNumber();
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
        ONLINE,
        OUTDOORS
    };

    // Getters
    public int getDurationInMinutes() {
        return durationInMinutes;
    }
    public String getCode() {
        return this.code;
    }
    public String getReadableName() {
        return readableName;
    }
    public String getDescription() { return description;}
    public Location getLocation() { return location; }
    public LocalDateTime getDateAndTime() { return dateAndTime; }
    public int getBaseCostInPence() { return baseCostInPence; }
    public boolean isInsuranceMandatory() { return isInsuranceMandatory; }

    // Setters
    public void setReadableName(String readableName) { this.readableName = readableName; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(Location location) { this.location = location; }
    public void setDateAndTime(LocalDateTime dateAndTime) { this.dateAndTime = dateAndTime; }
    public void setBaseCostInPence(int baseCostInPence) { this.baseCostInPence = baseCostInPence; }
    public void setDurationInMinutes(int durationInMinutes) { this.durationInMinutes = durationInMinutes; }
    public void setInsuranceMandatory(boolean insuranceMandatory) { isInsuranceMandatory = insuranceMandatory; }

    // Abstract methods
    public abstract int calculateBaseCostInPence();

    // Fields
    private final String code;
    private String readableName;
    private String description;
    private Location location;
    private LocalDateTime dateAndTime;
    private int baseCostInPence;
    private int durationInMinutes;
    private boolean isInsuranceMandatory;
}
