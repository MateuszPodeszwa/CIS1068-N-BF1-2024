package Utils.Bank;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

import static Utils.Converter.MoneyConverter.countryShortNames.UK;

// This class utility is responsible for generating receipts.
public final class Recipt {

    public static String generateRecipt(BankAccount account) {
        StringBuilder userInformation = new StringBuilder();
        StringBuilder transactionInformation = new StringBuilder();
        String lineSeparator = System.lineSeparator();

        userInformation
                .append("=-=-=-=-=-= Receipt =-=-=-=-=-=")
                .append(lineSeparator)
                .append("Account Number: ")
                .append(account.ACCOUNT_NUMBER)
                .append(lineSeparator)
                .append(account.ACCOUNT_HOLDER)
                .append(lineSeparator)
                .append("Balance: ")
                .append(account.getFormattedBalance())
                .append(lineSeparator)
                .append("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=")
                .append(lineSeparator)
                .append(lineSeparator);

        transactionInformation
                .append("=-=-= Payment Information =-=-=");

        AtomicInteger iterator = new AtomicInteger();
        account.getTransactionHistory().forEach(transaction -> {

            transactionInformation
                    .append(lineSeparator)
                    .append(iterator.incrementAndGet())
                    .append(". ")
                    .append(transaction.description())
                    .append(" ")
                    .append(transaction.referenceNumber().startsWith("A") ? "+" + transaction.transferAmount().get(UK).toPound() : "-" + transaction.transferAmount().get(UK).toPound())
                    .append(" ( ")
                    .append(transaction.transferAmount().get(UK).toPence())
                    .append(" )")
                    .append(lineSeparator)
                    .append("New Balance: ")
                    .append(transaction.newBalance().get(UK).toPound())
                    .append(" ( ")
                    .append(transaction.newBalance().get(UK).toPence())
                    .append(" )")
                    .append(lineSeparator)
                    .append("Date: ")
                    .append(transaction.dateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy 'at' HH:mm:ss")))
                    .append(lineSeparator)
                    .append("Reference Number: ")
                    .append(transaction.referenceNumber())
                    .append(lineSeparator);

        });
        return userInformation.append(transactionInformation).toString();
    }
}
