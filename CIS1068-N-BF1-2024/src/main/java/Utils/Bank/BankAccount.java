package Utils.Bank;

import Utils.Converter.MoneyConverter;
import Utils.Randomiser.GenerateReferenceNumber;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static Utils.Converter.MoneyConverter.countryShortNames.UK;

// This class represent a single BankAccount with linked user, holding some balance
public class BankAccount implements Transactions {

    MoneyConverter moneyDisplay = new MoneyConverter();

    private int balanceInPence;
    int MINIMUM_BANK_BALANCE = Integer.MIN_VALUE;
    int MAXIMUM_BANK_BALANCE = Integer.MAX_VALUE;
    protected final String ACCOUNT_HOLDER;
    protected final String ACCOUNT_NUMBER;

    // This is a list of objects that store details of individual transactions.
    private final List<TransactionRecord> transactionHistory;

    public BankAccount(String accountNumber, String accountHolder) {
        this.ACCOUNT_NUMBER = accountNumber;
        this.ACCOUNT_HOLDER = accountHolder;
        this.balanceInPence = 0;
        this.transactionHistory = new ArrayList<>();
    }

    public int getBalanceInPence() {
        return balanceInPence;
    }

    public String getFormattedBalance() {
        return moneyDisplay.setBalance(this.balanceInPence).get(UK).toPound();
    }

    public List<TransactionRecord> getTransactionHistory() {
        return transactionHistory;
    }

    /*
    * Transaction Interface Methods Implementation
    * */

    private void addToTransactionHistory(TransactionRecord transactionRecord) {
        transactionHistory.add(transactionRecord);
    }

    @Override
    public void addFunds(int amount, String description) {
        balanceInPence += amount;
        addToTransactionHistory(
                new TransactionRecord(
                        description,
                        new MoneyConverter(() -> amount).setBalance(amount),
                        new MoneyConverter(() -> balanceInPence).setBalance(balanceInPence),
                        LocalDateTime.now(),
                        new GenerateReferenceNumber(
                                LocalDate.now().getMonth(), 12)
                                .generateReferenceNumber('A'))
        );
    }

    @Override
    public void removeFunds(int amount, String description) {
        balanceInPence -= amount;
        addToTransactionHistory(
                new TransactionRecord(
                        description,
                        new MoneyConverter(() -> amount).setBalance(amount),
                        new MoneyConverter(() -> balanceInPence).setBalance(balanceInPence),
                        LocalDateTime.now(),
                        new GenerateReferenceNumber(
                                LocalDate.now().getMonth(), 12)
                                .generateReferenceNumber('R'))
        );
    }
}
