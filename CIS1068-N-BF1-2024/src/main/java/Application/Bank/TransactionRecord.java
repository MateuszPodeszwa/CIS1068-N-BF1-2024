package Application.Bank;

import Utils.Converter.MoneyConverter;

import java.time.LocalDateTime;

import static java.lang.System.out;

// Stores details of each transaction made
public record TransactionRecord(String description, MoneyConverter transferAmount, MoneyConverter newBalance, LocalDateTime dateTime, String referenceNumber) {
    static {
        out.println("TransactionRecord class loaded");
    }

    public TransactionRecord {
        if (description.isEmpty())
            throw new IllegalArgumentException("Transaction description cannot be empty");
    }

    @Override
    public String toString() {
        return "TransactionRecord {" +
                "description = '" + description + '\'' +
                ", transferAmount = " + transferAmount.get(MoneyConverter.countryShortNames.UK).toPound() +
                ", newBalance = " + newBalance.get(MoneyConverter.countryShortNames.UK).toPound() +
                ", dateTime = " + dateTime.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss")) +
                ", referenceNumber = '" + referenceNumber + '\'' +
                '}';
    }
}
