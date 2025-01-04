package Application.DataPlanner.itinerary;

public enum ItineraryAddon {

    ACCOMMODATION(450_00),
    LUNCH(10_00),
    COFFEE(5_00);

    private final int price;

    ItineraryAddon(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
