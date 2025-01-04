package Application.DataPlanner.Activity;
/*
* It is a simple register of all activities bound by its reference number, or ID
* It uses ActivityMetadata Record Class to store all information
* */

import Utils.Randomiser.GenerateReferenceNumber;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/*
* This class creates a register of all activities to easily access them, it bounds unique ID with each activity.*/
public class ActivityRegistry {

    private final Map<String, ActivityMetadata> metadataMap;
    private static ArrayList<String> individualReferenceList;
    static AtomicInteger activityCounter = new AtomicInteger(0);

    public ActivityRegistry() {
        metadataMap = new HashMap<>();
        individualReferenceList = new ArrayList<>();
        /*
        * There is a space if you want to read activities from file or database, you can add them here
        * */
    }

    public Map<String, ActivityMetadata> getMetadataMap() {
        return this.metadataMap;
    }

    public void addActivity(ActivityMetadata activityMetadata) {
        metadataMap.put(activityMetadata.referenceNumber(), activityMetadata);
        System.out.println("DEBUG Activity [" + activityCounter.incrementAndGet() + "]: " + activityMetadata.referenceNumber() + " " + metadataMap.get(activityMetadata.referenceNumber()).readableName());
    }

    public String generateIndividualReference(String individualReference, String ActivityReferenceCode) {
        // Adds each individual reference to the list, so it can keep track of all individual references and make sure these stay unique
        if (individualReferenceList.contains(individualReference)) {
            throw new IllegalArgumentException("Individual reference already exists " + individualReference + " please provide a unique reference for " + ActivityReferenceCode + " activity");
        } else {
            individualReferenceList.add(individualReference);
            return uniqueReferenceGeneratorAndIdentifier(individualReference, ActivityReferenceCode);
        }
    }

    public static void flushIndividualReferenceList() {
        individualReferenceList.clear();
    }
    public static ArrayList<String> getIndividualReferenceList() {return individualReferenceList;}
    public static AtomicInteger getActivityCounter() {return activityCounter;}
    public static int getTotalActivities() {return activityCounter.get();}
    public static void flushAtomicInteger() {activityCounter.set(0);}

    /* This function takes an argument of activity type such as SASCourse etc. and then lists all available activities of this kind in the map.*/
    public Map<String, ActivityMetadata> listAllActivitiesOfAKind(String ActivityReferenceCode) {
        Map<String, ActivityMetadata> activitiesOfAKind = new HashMap<>();

        for (Map.Entry<String, ActivityMetadata> entry : metadataMap.entrySet()) {
            if (entry.getKey().contains(ActivityReferenceCode)) {
                activitiesOfAKind.put(entry.getKey(), entry.getValue());
            }
        }
        return activitiesOfAKind;
    }

    /*
    * This method bounds common activity type reference with the unique individual reference ID. In the format: CommonReference_IndividualReference
    * */
    public String uniqueReferenceGeneratorAndIdentifier(String individualReference, String ActivityReferenceCode) {
        return ActivityReferenceCode.concat("_").concat(individualReference);
    }
}
