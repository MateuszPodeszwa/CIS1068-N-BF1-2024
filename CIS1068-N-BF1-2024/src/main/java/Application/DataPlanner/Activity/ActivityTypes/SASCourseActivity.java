package Application.DataPlanner.Activity.ActivityTypes;

import Application.DataPlanner.Activity.Activity;
import Application.DataPlanner.Activity.ActivityMetadata;
import Utils.Randomiser.GenerateReferenceNumber;

import java.time.Month;

public class SASCourseActivity extends Activity {

    public static final String classReference = "SAS-00";
    private static final int BASE_COST_IN_PENCE = 0;

    public SASCourseActivity(ActivityMetadata metadata) {
        super(metadata);
    }

    @Override
    public int calculateFinalCostInPence() {
        return super.getBaseCostInPence() + getFee().get().toPence() + addonsCost();
    }

}
