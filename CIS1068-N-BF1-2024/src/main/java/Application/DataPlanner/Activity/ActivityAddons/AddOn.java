package Application.DataPlanner.Activity.ActivityAddons;

public sealed interface AddOn permits InsuranceAddon, PhotographyAddon, TravelAddon {

    /**
     * Returns the cost of the addon in pence. This solution minimises repeated code across AddOn classes.
     *
     * @return the cost of the addon in pence
     */
    default int getCost() {
        try { return this.getClass().getDeclaredField("ADDON_COST").getInt(this); }
        catch (NoSuchFieldException | IllegalAccessException e) {
            System.err.println("Error: " + e.getMessage());
            return 0;
        }
    };

    default Class<? extends AddOn> returnClass() {
        return this.getClass();
    }; // Returns the ID of the addon

    int MAX_ADDONS = 3; // Maximum number of addons allowed per one activity
}