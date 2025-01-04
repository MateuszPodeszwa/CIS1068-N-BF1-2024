package Application.DataPlanner.Activity;

import Application.DataPlanner.Activity.ActivityAddons.AddOn;
import Utils.Converter.MoneyConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        this.FEE_PER_PENCE = validateFeePerPence((byte) 0); // Private fee per pence, max 85, a hard profit for the company to make
        this.MAXIMUM_NO_OF_ADDONS = AddOn.MAX_ADDONS; // Maximum number of addons
        this.activityAddons = addonListInitialiser(metadata.activityAddons());
    }

    // The location of individual activity, for better flexibility it is recommended to move it outside the scope.
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
    protected MoneyConverter getCostWithFee() {
        return new MoneyConverter().setBalance(this.baseCostInPence + getFee().get().toPence());
    }
    public String getReadableName() {
        return readableName;
    }
    public String getDescription() { return description;}
    public Location getLocation() { return location; }
    public LocalDateTime getDateAndTime() { return dateAndTime; }
    public int getBaseCostInPence() { return baseCostInPence; }
    public boolean isInsuranceMandatory() { return isInsuranceMandatory; }
    public List<AddOn> getActivityAddons() { return activityAddons; }

    // Setters
    public Activity setReadableName(String readableName) { this.readableName = readableName; return this; }
    public Activity setDescription(String description) { this.description = description; return this; }
    public Activity setLocation(Location location) { this.location = location; return this; }
    public Activity setDateAndTime(LocalDateTime dateAndTime) { this.dateAndTime = dateAndTime; return this; }
    public Activity setDurationInMinutes(int durationInMinutes) { this.durationInMinutes = durationInMinutes; return this; }
    public Activity setInsuranceMandatory(boolean insuranceMandatory) { isInsuranceMandatory = insuranceMandatory; return this; }
    public Activity setActivityAddons(List<AddOn> activityAddons) { this.activityAddons = activityAddons; return this; }

    // Addons
    private ArrayList<AddOn> addonListInitialiser(ArrayList<AddOn> addons) {
        if (addons.size() > MAXIMUM_NO_OF_ADDONS) {
            throw new IllegalArgumentException("Maximum number of addons reached, current " + addons.size() + " out of " + MAXIMUM_NO_OF_ADDONS);
        }
        for (int i = 0; i < addons.size(); i++) {
            for (int j = i + 1; j < addons.size(); j++) {
                if (addons.get(i).returnClass().equals(addons.get(j).returnClass())) {
                    throw new IllegalArgumentException("Duplicate addon found: " + addons.get(i).returnClass());
                }
            }
        }
    return new ArrayList<>(addons);

    }

    public void addAddon(AddOn addon) {
        if (activityAddons.size() < MAXIMUM_NO_OF_ADDONS)
            activityAddons.add(addon);
        else throw new IllegalArgumentException("Maximum number of addons reached, current " + activityAddons.size() + " out of " + MAXIMUM_NO_OF_ADDONS);
    }

    public void removeAddon(AddOn addon) {
        if (activityAddons.contains(addon))
            activityAddons.remove(addon);
        else throw new IllegalArgumentException("Addon " + addon + " not found in the list " + activityAddons);
    }

    public int addonsCost() {
        return activityAddons.stream().mapToInt(AddOn::getCost).sum();
    }

    // Abstract methods
    public abstract int calculateFinalCostInPence();

    public MoneyConverter getFee() {
        // Calculates total fee amount given the base cost
        double fee = (double) this.baseCostInPence * this.FEE_PER_PENCE / 100;
        int feeInPence = BigDecimal.valueOf(fee).setScale(0, RoundingMode.HALF_UP).intValue();
        return new MoneyConverter().setBalance(feeInPence);
    }

    // Private methods
    private byte validateFeePerPence(byte fee) {
        byte[] legalValuesRange = {0, 85};
        if (fee < legalValuesRange[0] || fee > legalValuesRange[1]) {
            throw new IllegalArgumentException("Fee per pound must be between " + legalValuesRange[0] + " and " + legalValuesRange[1]);
        }
        return fee;
    }

    // Public methods which are not setters and getters

/**
 * Prints debug information about the activity.
 * This includes details such as fee rate, standalone fee, base cost, total cost, duration, location, date and time, description, insurance status, code, and readable name.
 */
    public void debug() {
    System.out.println("DEBUG: " + this.getClass().getSimpleName());
    System.out.println("\f Fee Rate: " + this.FEE_PER_PENCE + "%" + " of " + new MoneyConverter().setBalance(this.baseCostInPence).get(MoneyConverter.countryShortNames.UK).toPence());
    System.out.println("\f Standalone Fee: " + getFee().get(MoneyConverter.countryShortNames.UK).toPound() + " (Pence: " + new MoneyConverter().setBalance(getFee().get().toPence()).get(MoneyConverter.countryShortNames.UK).toPence()  + ")");
    System.out.println("\f Base Cost: " + new MoneyConverter().setBalance(this.baseCostInPence).get(MoneyConverter.countryShortNames.UK).toPound() + " (Pence: " + new MoneyConverter().setBalance(this.baseCostInPence).get(MoneyConverter.countryShortNames.UK).toPence() + ")");
    System.out.println("\f Total Cost: " + new MoneyConverter().setBalance(calculateFinalCostInPence()).get(MoneyConverter.countryShortNames.UK).toPound() + " (Pence: " + new MoneyConverter().setBalance(calculateFinalCostInPence()).get(MoneyConverter.countryShortNames.UK).toPence() + ")");
    System.out.println("\n\f Duration: " + this.durationInMinutes + " minutes" + " (" + (this.durationInMinutes / 60 <= 1 ? this.durationInMinutes / 60 + " hour" : this.durationInMinutes / 60 + " hours") + ")");
    System.out.println("\f Location: " + this.location);
    System.out.println("\f Date and Time: " + this.dateAndTime.toLocalDate() + " at " + this.dateAndTime.toLocalTime());
    System.out.println("\n\f Description: " + this.description);
    System.out.println("\f Insurance Mandatory: " + (this.isInsuranceMandatory ? "Yes" : "No"));
    System.out.println("\f Code: " + this.code);
    System.out.println("\f Readable Name: " + this.readableName);
}

    // Fields
    private final String code;
    private final byte MAXIMUM_NO_OF_ADDONS;
    private final byte FEE_PER_PENCE; // Fee per one pound, max 85
    private List<AddOn> activityAddons;
    private String readableName;
    private String description;
    private Location location;
    private LocalDateTime dateAndTime;
    private final int baseCostInPence;
    private int durationInMinutes;
    private boolean isInsuranceMandatory;
}
