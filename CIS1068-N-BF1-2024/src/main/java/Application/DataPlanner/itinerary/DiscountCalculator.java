package Application.DataPlanner.itinerary;

public class DiscountCalculator {

    private final int noOfActivities;
    private final int noOfAttendees;

    public DiscountCalculator(int noOfActivities, int noOfAttendees) {
        if (noOfActivities < 0 || noOfAttendees < 0)
            throw new IllegalArgumentException("Number of activities and attendees must be positive");

        this.noOfActivities = noOfActivities;
        this.noOfAttendees = noOfAttendees;
    }

    // Returns the percentage of a discount to be applied
    public int calculateDiscount() {
        switch (noOfAttendees) {
            case 1: case 2:
                if /**/ (noOfActivities < 10) return 0;
                else if (noOfActivities < 20) return 5;
                else /*                    */ return 8;

            case 3: case 4: case 5:
                if /**/ (noOfActivities < 10) return 5;
                else if (noOfActivities < 20) return 8;
                else /*                    */ return 12;

            default:
                if /**/ (noOfActivities < 10) return 10;
                else if (noOfActivities < 20) return 12;
                else /*                    */ return 14;
        }
    }

}
