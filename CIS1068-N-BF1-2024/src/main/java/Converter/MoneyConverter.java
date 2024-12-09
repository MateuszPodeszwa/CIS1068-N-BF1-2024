package Converter;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * <p>
 * A flexible and future-proof money conversion utility. This class helps in converting
 * a raw integer money value (e.g., total pence or cents) into various formats, such as
 * strings with currency symbols or numeric double or integer representations.
 * </p>
 *
 * <p>
 * The {@code MoneyConverter} supports method chaining.
 * For example:
 * <pre>{@code
 *     MoneyConverter converter = new MoneyConverter();
 *     String pounds = converter.setBalance(250).get("UK").toPound(); // "£2.50"
 * }</pre>
 * </p>
 *
 * <p>
 * Author: Mateusz Podeszwa<br>
 * Year: 2024<br>
 * Version: 2.0
 * </p>
 */
public class MoneyConverter {

    // The raw money value in the lowest denomination (e.g., pence or cents).
    private int rawMoney;
    // Provides a default fallback integer value if none is set.
    private final Supplier<Integer> defaultSupplier;

    /**
     * Constructs a new {@code MoneyConverter} with a specified default supplier.
     * The supplier provides a fallback value if the raw money value is not set.
     *
     * @param defaultSupplier the supplier that provides a default raw money value when none is set
     */
    public MoneyConverter(Supplier<Integer> defaultSupplier) {
        this.defaultSupplier = defaultSupplier;
    }

    /**
     * Constructs a new {@code MoneyConverter} with a default supplier returning zero.
     * This means if raw money is not set, zero will be used as the fallback.
     */
    public MoneyConverter() {
        this.defaultSupplier = () -> 0;
    }

    /**
     * Sets the balance of this converter in the lowest monetary unit
     * (e.g. pence in the UK, cents in the US).
     *
     * @param rawMoney the raw money amount
     * @return this {@code MoneyConverter} instance for method chaining
     */
    public MoneyConverter setBalance(int rawMoney) {
        // Set the raw money value; maintenance note:
        // Here we assume input is always valid integer representing the smallest currency unit.
        this.rawMoney = rawMoney;
        return this;
    }

    /**
     * Retrieves a {@link RetrievedMoney} instance for numeric representations.
     * <p>
     * Use this method if you want to get purely numeric forms of the set balance.
     * For example, to convert the raw money into pence or pounds.
     * </p>
     *
     * @return a new {@code RetrievedMoney} object representing the numeric forms of the current raw money
     */
    public RetrievedMoney get() {
        // If rawMoney is not set, use the defaultSupplier value.
        return new RetrievedMoney(nullCheck().orElseGet(this.defaultSupplier));
    }

    /**
     * Retrieves a {@link DisplayedMoney} instance for string representations with currency symbols.
     * <p>
     * Use this method if you want a formatted, human-readable representation of the money value,
     * including currency symbols (e.g., £, $).
     * </p>
     *
     * @param country a string indicating the country format (e.g. "UK", "US", "PL")
     * @return a new {@code DisplayedMoney} object representing the formatted string form
     */
    public DisplayedMoney get(String country) {
        // If rawMoney is not set, use the defaultSupplier value.
        return new DisplayedMoney(nullCheck().orElseGet(this.defaultSupplier), country);
    }

    /**
     * Checks if the rawMoney value is set or not.
     * <p>
     * This private method ensures that we do not accidentally process a null
     * (or rather, an unset) money value.
     * </p>
     *
     * @return an {@link Optional} containing the rawMoney if present, otherwise empty
     */
    private Optional<Integer> nullCheck() {
        // Maintenance note:
        // We use Optional to cleanly handle absent values without risk of NullPointerException.
        return Optional.ofNullable(this.rawMoney);
    }

    /**
     * Represents displayed money with currency symbols for a given country.
     * <p>
     * This nested static class provides formatted string outputs
     * such as "£2.50" or "250p" depending on the requested format.
     * </p>
     */
    public static class DisplayedMoney {

        // The raw money amount in the smallest currency unit.
        private final int money;
        // The currency symbols array where CURRENCY[0] is the symbol for the main unit,
        // and CURRENCY[1] is the symbol for the fractional unit.
        private final String[] CURRENCY;

        /**
         * Constructs a {@code DisplayedMoney} object.
         *
         * @param money the raw money amount in the smallest unit
         * @param country the country code determining which currency symbols to use
         */
        private DisplayedMoney(int money, String country) {
            this.money = money;
            // Maintenance note:
            // Add more countries as needed.
            this.CURRENCY = switch (country) {
                case "UK" -> new String[] {"£", "p"};
                case "US" -> new String[] {"$", "c"};
                case "PL" -> new String[] {"PLN", "gr."};
                default -> new String[] {"", ""};
            };
        }

        /**
         * Converts the raw value into a string representing only the smallest unit,
         * e.g., pence or cents.
         *
         * @return a string of the form "250p", "250c", or equivalent fractional unit
         */
        public String toPence() {
            // Maintenance note:
            // We simply append the fractional unit symbol to the integer value.
            return this.money + this.CURRENCY[1];
        }

        /**
         * Converts the raw value into a string representing the main currency unit,
         * including the currency symbol. For example, "£2.50".
         *
         * @return a string with the main unit and symbol, formatted as a decimal
         */
        public String toPound() {
            // Dividing by 100.0 to convert from pence to pounds.
            // Maintenance note:
            // This will give a floating-point value, e.g. 250 -> £2.5
            return this.CURRENCY[0] + (this.money / 100.0);
        }
    }

    /**
     * Represents retrieved money in numeric formats without currency symbols.
     * <p>
     * This nested static class provides methods to retrieve the raw money
     * as an integer (pence) or as a double (pounds).
     * </p>
     */
    public static class RetrievedMoney {

        // The raw money amount in the smallest currency unit.
        private final int money;

        /**
         * Constructs a {@code RetrievedMoney} object.
         *
         * @param money the raw money amount in the smallest unit
         */
        public RetrievedMoney(int money) {
            this.money = money;
        }

        /**
         * Retrieves the money value as an integer representing the smallest unit.
         * <p>
         * For example, if the money is set as 250 (representing 250 pence),
         * this will return 250.
         * </p>
         *
         * @return the raw money in smallest units (e.g., pence)
         */
        public int toPence() {
            // Straight integer return for calculations or further use.
            return this.money;
        }

        /**
         * Retrieves the money value as a double representing the main currency unit.
         * <p>
         * For example, if the money is 250 pence, this returns 2.50 as a double.
         * </p>
         *
         * @return the money converted to the main unit as a double (e.g., 2.50 for 250 pence)
         */
        public double toPound() {
            // Maintenance note:
            // Dividing by 100.0 ensures correct conversion to main unit currency.
            return this.money / 100.0;
        }

        /**
         * Retrieves the money value as an integer main currency unit,
         * optionally rounded to the nearest whole number.
         * <p>
         * If {@code state} is {@code true}, it rounds the value.
         * If {@code state} is {@code false}, it truncates.
         * </p>
         *
         * @param state {@code true} to round the value, {@code false} to truncate
         * @return the integer main currency unit value, rounded or truncated
         */
        public int toPound(boolean state) {
            // Maintenance note:
            // We utilise Math.round() when state is true.
            if (state) {
                return (int) Math.round(toPound());
            }
            return (int) toPound();
        }
    }
}
