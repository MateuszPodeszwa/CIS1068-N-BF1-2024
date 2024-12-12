package Utils.Bank;

// This Interface provide what bank account can do, list of all transaction methods
public interface Transactions {

    // Due to the initial requirements the amount will be provided in the lowest currency, ie pence or cents.
    // For the purpose of this particular project this interface is not necessary, but it will be useful while expanding the program.
    double TRANSACTION_FEE = 0.00;
    int MINIMUM_TRANSACTION_AMOUNT = Integer.MIN_VALUE;
    int MAXIMUM_TRANSACTION_AMOUNT = Integer.MAX_VALUE;
    // Each BankAccount will have to: addFunds, removeFunds
    void addFunds(int amount, String description);
    void removeFunds(int amount, String description);
}
