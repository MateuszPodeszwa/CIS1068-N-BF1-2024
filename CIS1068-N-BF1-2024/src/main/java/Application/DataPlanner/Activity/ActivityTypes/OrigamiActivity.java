package Application.DataPlanner.Activity.ActivityTypes;

import Application.DataPlanner.Activity.Activity;
import Application.DataPlanner.Activity.ActivityMetadata;

public class OrigamiActivity extends Activity {

    public static final String classReference = "ORG-00";

    public OrigamiActivity(ActivityMetadata metadata) {
        super(metadata);
    }

    @Override
    public int calculateBaseCostInPence() {
        return 0;
    }
}
