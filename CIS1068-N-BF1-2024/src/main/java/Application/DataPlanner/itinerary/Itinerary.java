package Application.DataPlanner.itinerary;
import Application.DataPlanner.Activity.Activity;
import Utils.Converter.MoneyConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Itinerary class represents a list of activities and itinerary addons.
 * It provides methods to add, remove, and retrieve activities and itinerary addons.
 * It also provides methods to calculate the total price of the itinerary, located in a PricingService class.
 *
 * @see PricingService
 * @see Activity
 * @see ItineraryAddon
 */
public class Itinerary {
/**
     * List of all activities in the itinerary, initialized as an ArrayList in the constructor.
     * This list contains Activity objects.
     *
     * @see Activity
     */
    private final List<Activity> activities;
    /**
     * List of itinerary addons.
     * This list contains `ItineraryAddon` objects which are enumerations.
     *
     * @see ItineraryAddon
     */
    private List<ItineraryAddon> itineraryAddons; // DO NOT MAKE IT FINAL!
    /**
     * Default constructor for the Itinerary class.
     * Initializes the activities and itineraryAddons lists.
     * Requires user to add activities and itineraryAddons manually, in the method call.
     */
    public Itinerary () {
        // WARNING! Do not change ArrayList to other List implementations!
        this.activities = new ArrayList<>();
        // Possible use Arrays with initialised length of ItineraryAddon.values().length
        this.itineraryAddons = new ArrayList<>();
    }
    /**
     * Constructs an Itinerary with a list of activities.
     *
     * Example Usage:
     * <pre>
     * {@code
     * List<Activity> activities = Arrays.asList(
     *     new Activity("ACT01"),
     *     new Activity("ACT02")
     * );
     * Itinerary itinerary = new Itinerary(activities);
     * }
     * </pre>
     *
     * @param activities the list of activities to initialize the itinerary with
     * @throws IllegalArgumentException if any activity already exists in the itinerary
     */
    public Itinerary(List<Activity> activities) {
        // Possible use Arrays with initialised length of activities.size()
        this.activities = new ArrayList<>();
        // Possible use Arrays with initialised length of ItineraryAddon.values().length
        this.itineraryAddons = new ArrayList<>();
        // Catches exception thrown by addActivities method
        try { addActivities(activities); } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            // TODO: Possible Implementation: Add a method to handle this exception
        }
    }
    /**
     * Adds an activity to the itinerary. Checks if the activity already exists in the itinerary.
     *
     * Example Usage:
     * <pre>{@code
     * Itinerary week34Activities = new Itinerary();
     * week34Activities.addActivity(week34Activities.initialiseActivity(OrigamiActivity.class, "ORG01"));
     *
     * // Or by passing an already initialised activity object:
     * Activity act1 = week34Activities.initialiseActivity(OrigamiActivity.class, "ORG01");
     * week34Activities.addActivity(act1);
     * }</pre>
     *
     * @param activity the activity to be added
     * @throws IllegalArgumentException if the activity already exists in the itinerary
     */
    public void addActivity(Activity activity) {
        if (doesActivityExist(activity))
            this.activities.add(activity);
        else throw new IllegalArgumentException("Activity " + activity.getCode() + " already exists in the itinerary (size) " + activities.size());
    }
    /**
 * Adds multiple activities to the itinerary. Works similarly to {@link #addActivity} method.
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * Itinerary week34to35_2025Package = new Itinerary();
 * List<Activity> activities = Arrays.asList(
 *     week34Activities.initialiseActivity(OrigamiActivity.class, "ORG01"),
 *     week34Activities.initialiseActivity(HikingActivity.class, "HIK02")
 * );
 * week34to35_2025Package.addActivities(activities);
 *
 * // OR
 * week34to35_2025Package.addActivities(List.of(
 *      week34Activities.initialiseActivity(OrigamiActivity.class, "ORG01"),
 * ));
 * }
 * </pre>
 *
 * @param activities the list of activities to be added
 * @throws IllegalArgumentException if any activity already exists in the itinerary
 */
    public void addActivities(List<Activity> activities) {
        for (Activity activity : activities)
            if (doesActivityExist(activity))
                this.activities.add(activity);
            else throw new IllegalArgumentException("Activity " + activity.getCode() + " already exists in the itinerary (size) " + activities.size());
    }
    /**
     * Returns a list of all activities in the itinerary.
     *
     * Example Usage:
     * <pre>{@code
     * Itinerary week34to35_2025Package = new Itinerary();
     * List<Activity> activities = week34to35_2025Package.getActivities();
     * }</pre>
     *
     * @return a list of all activities in the itinerary
     */
    public List<Activity> getActivities() {
        return this.activities;
    }
    /**
     * Checks if an activity already exists in the itinerary. Private method used by {@link #addActivity} and {@link #addActivities(List)} method.
     *
     * @param activity the activity to check
     * @return true if the activity does not exist in the itinerary, false otherwise
     */
    private boolean doesActivityExist(Activity activity) {
        for (Activity a : activities)
            if (a.getCode().equals(activity.getCode()))
                return false;
        return true;
    }

    public boolean checkForDuplicates() {
        for (int i = 0; i < activities.size(); i++) /* AND */ for (int j = i + 1; j < activities.size(); j++)
                if (activities.get(i).getCode().equals(activities.get(j).getCode()))
                    return true;
        return false;
    }

    public void removeActivity(Activity activity) {
        for (int i = 0; i < activities.size(); i++)
            if (activities.get(i).getCode().equals(activity.getCode())) {
                activities.remove(i);
                return;
            }
    }

    public void removeActivity(String activityCode) {
        for (int i = 0; i < activities.size(); i++)
            if (activities.get(i).getCode().equals(activityCode)) {
                activities.remove(i);
                return;
            }
    }

    public MoneyConverter finalPriceDiscounted(int noOfAttendees) {
        int price =  (finalPriceBeforeDiscount() - (finalPriceBeforeDiscount() * (new DiscountCalculator(activities.size(), noOfAttendees).calculateDiscount() / 100)));
        return new MoneyConverter(() -> 1).setBalance(price);
    }

    public int priceOfAllActivityAddons() {
        return activities.stream().mapToInt(Activity::addonsCost).sum();
    }

    public int priceOfAllItineraryAddons() {
        return itineraryAddons.stream().mapToInt(ItineraryAddon::getPrice).sum();
    }

    public int getNumberOfActivities() {
        return activities.size();
    }
    public int getNumberOfItineraryAddons() {
        return itineraryAddons.size();
    }

    public void addItineraryAddon(ItineraryAddon itineraryAddon) {
        if (itineraryAddons.contains(itineraryAddon)) {
            throw new IllegalArgumentException("ItineraryAddon " + itineraryAddon + " already exists in the itinerary.");
        }
        if (itineraryAddons.size() >= ItineraryAddon.values().length) {
            throw new IllegalArgumentException("Cannot add more ItineraryAddons. Maximum limit reached.");
        }
        this.itineraryAddons.add(itineraryAddon);
    }

    public void removeItineraryAddon(ItineraryAddon itineraryAddon) {
        if (!itineraryAddons.contains(itineraryAddon)) {
            throw new IllegalArgumentException("ItineraryAddon " + itineraryAddon + " does not exist in the itinerary.");
        }
        this.itineraryAddons.remove(itineraryAddon);
    }

    public List<ItineraryAddon> getItineraryAddons() {
        return this.itineraryAddons;
    }

    /* 30min of breaking my keyboard... */

    public int finalPriceBeforeDiscount() {
        return activities.stream().mapToInt(Activity::calculateFinalCostInPence).sum();
    }

    public int priceOfAllActivities() {
        return activities.stream().mapToInt(Activity::getBaseCostInPence).sum();
    }

    public Activity getActivityByCode(String activityCode) {
        return activities
                .stream().filter(activity -> activity.getCode().equals(activityCode)).findFirst().orElseThrow(
                        () -> new IllegalArgumentException("Activity with code " + activityCode + " not found in the itinerary. The closest match is: "
                                + activities.stream().filter(activity -> activity.getCode().contains(activityCode)).findFirst().map(Activity::getCode).orElse("NaN")));
        // Hopefully I will never have to come back to this code again.
    }

    public List<Activity> getActivitiesByType(Class<?> activityType) {
        return activities.stream().filter(activity -> activity.getClass().equals(activityType)).collect(Collectors.toList());
    }


}
