package Converter;

import java.util.Optional;
import java.util.function.Supplier;

public class MoneyConverter {
    private Integer rawMoney;
    private final Supplier<Integer> defaultSupplier;
    
    public MoneyConverter(Supplier<Integer> defaultSupplier) {
        this.defaultSupplier = defaultSupplier;
    }

    public MoneyConverter setBalance(int rawMoney) {
        this.rawMoney = rawMoney;
        return this;
    }
    
    public RetrievedMoney get() {
        return new RetrievedMoney(nullCheck().orElseGet(this.defaultSupplier));
    }
    
    public DisplayedMoney get(String country) {
        return new DisplayedMoney(nullCheck().orElseGet(this.defaultSupplier), country);
    }
    
    private Optional<Integer> nullCheck() {
        return Optional.ofNullable(this.rawMoney);
    }
    
    public class DisplayedMoney {
        private final int money;
        private final String[] CURRENCY;

        private DisplayedMoney(int money, String country) {
            this.money = money;
            this.CURRENCY = switch (country) {
                case "UK" -> new String[] {"£", "p"};
                case "US" -> new String[] {"$", "c"};
                default -> new String[] {"$", "c"};
            };
        }

        public String toPence() {
            // Using CURRENCY[1] for pence-like symbol
            return this.money + this.CURRENCY[1];
        }

        public String toPound() {
            // Using CURRENCY[0] for pound-like symbol
            return this.CURRENCY[0] + (this.money / 100.0);
        }
    }
    
    public class RetrievedMoney {
        
        private final int money;
        
        public RetrievedMoney(int money) {
            this.money = money;
        }
        
        public int toPence() {
            return this.money;
        }

        public double toPounds() {
            return this.money / 100.0;
        }
        
    }
}
