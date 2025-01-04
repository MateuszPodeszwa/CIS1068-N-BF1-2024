package Application.DataPlanner.Activity;

import Application.DataPlanner.Activity.ActivityTypes.BaseActivity;
import Application.DataPlanner.Activity.ActivityTypes.CookeryClassActivity;
import Application.DataPlanner.Activity.ActivityTypes.OrigamiActivity;
import Application.DataPlanner.Activity.ActivityTypes.SASCourseActivity;

import static java.lang.System.err;

public class ActivityFactory {
    /*
    When you want a new activity instance, you ask the ActivityFactory.
    The factory is configured at startup
    (possibly by reading a configuration file or registry)
    and knows which Activity class corresponds to each activity code.
    * */

    private final ActivityRegistry registry;

    public ActivityFactory(ActivityRegistry registry) {
        this.registry = registry;
    }

    private Activity createActivity(String code) throws NoSuchFieldException {
        ActivityMetadata metadata = registry.getMetadataMap().get(code);
        if (metadata == null) {
            throw new IllegalArgumentException("No such activity: " + code + " in the registry " + System.identityHashCode(registry));
        }

        return switch (metadata.referenceNumber().split("_")[0]) {
            case BaseActivity.classReference -> new BaseActivity(metadata);
            case CookeryClassActivity.classReference -> new CookeryClassActivity(metadata);
            case OrigamiActivity.classReference -> new OrigamiActivity(metadata);
            case SASCourseActivity.classReference -> new SASCourseActivity(metadata);
            default -> throw new IllegalArgumentException("No such activity: " + metadata.referenceNumber());
        };
    }

    public Activity initialiseActivity(Class<? extends Activity> theClass, String individualReferenceCode) {
        try
        {
            String activityReferenceCode = theClass.getDeclaredField("classReference").get(null).toString(); // Samples the general ID
            return this.createActivity(registry.uniqueReferenceGeneratorAndIdentifier(individualReferenceCode, activityReferenceCode));
        }
        catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException e)
        {
            err.println("Failed to create activity: " + e.getMessage()); // Only for debugging, can stay due to application having GUI
            throw new RuntimeException(e);
        }
    }
    
}
