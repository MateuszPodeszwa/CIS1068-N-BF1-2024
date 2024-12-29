package Application.DataPlanner.Activity.ActivityTypes;

import Application.DataPlanner.Activity.Activity;
import Application.DataPlanner.Activity.ActivityMetadata;

public class CookeryClassActivity extends Activity {

    public static final String classReference = "CCA-00";

    public CookeryClassActivity(ActivityMetadata metadata) {
        super(metadata);
    }

    @Override
    public int calculateBaseCostInPence() {
        return 0;
    }
}
