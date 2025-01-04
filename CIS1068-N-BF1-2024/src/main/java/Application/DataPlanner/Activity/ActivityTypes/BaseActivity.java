package Application.DataPlanner.Activity.ActivityTypes;

import Application.DataPlanner.Activity.Activity;
import Application.DataPlanner.Activity.ActivityMetadata;

/*
* It is a default activity that the program can opt in if not defined otherwise, it has no functionality, it is used as a placeholder, to do not crash the program.
* */

public class BaseActivity extends Activity {

    public BaseActivity(ActivityMetadata metadata) {
        super(metadata);
    }

    @Override
    public int calculateFinalCostInPence() {
        return super.getBaseCostInPence() + getFee().get().toPence() + addonsCost();
    }

    public static final String classReference = "BAC-00";
}
