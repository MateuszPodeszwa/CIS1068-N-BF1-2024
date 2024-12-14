package Application.Bank;

import static java.math.BigDecimal.valueOf;

// This Interface provide what bank account can do, list of all transaction methods
public interface Transactions {

    // Due to the initial requirements the amount will be provided in the lowest currency, ie pence or cents.
    // For the purpose of this particular project this interface is not necessary, but it will be useful while expanding the program.
    double TRANSACTION_FEE = 0.05; // 0.05 = 5%
    int MINIMUM_TRANSACTION_AMOUNT = Integer.MIN_VALUE;
    int MAXIMUM_TRANSACTION_AMOUNT = Integer.MAX_VALUE;
    // Each BankAccount will have to: addFunds, removeFunds
    void addFunds(int amount, String description);
    void removeFunds(int amount, String description);

    default Transactions removeFundsWithTransactionFee(int amount, String description) {
        removeFunds(amount - valueOf(TRANSACTION_FEE).multiply(valueOf(amount)).intValue(), description);
        return this;
    };
}
