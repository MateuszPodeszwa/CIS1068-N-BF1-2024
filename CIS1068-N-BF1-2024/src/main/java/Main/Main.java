/*
 * Copyright (c) 2024
 * Author: Mateusz Podeszwa
 * All rights reserved.
 */

package Main;

import Utils.Bank.Bank;
import Utils.Bank.BankAccount;
import Utils.Bank.Recipt;
import Utils.Converter.MoneyConverter;
import Utils.Converter.NameFormatter;
import Utils.Randomiser.GenerateReferenceNumber;

import static Utils.Converter.MoneyConverter.countryShortNames.UK;
import static java.lang.System.out;

/**
 * The {@code Main} class is the entry point of the application.
 * <p>
 * It showcases how to use various classes such as {@link GenerateReferenceNumber},
 * {@link NameFormatter}, and {@link MoneyConverter}. The main method
 * demonstrates generating a random reference number, validating and formatting usernames,
 * and converting monetary values into different formats.
 * </p>
 *
 * <p>
 * Author: Mateusz Podeszwa<br>
 * Year: 2024<br>
 * Version: 1.0
 * </p>
 */
public class Main {

    /**
     * A static field holding the full username.
     * <p>
     * This field is used to demonstrate the {@link NameFormatter} class.
     * </p>
     */
    private static final String FULL_USER_NAME = "Mateusz Podeszwa";

    /**
     * The main method serves as the entry point for the application.-
     * <p>
     * Steps demonstrated:
     * <ol>
     *   <li>Creating a {@link GenerateReferenceNumber} object and printing out generated reference numbers.</li>
     *   <li>Using {@link NameFormatter} to apply company formatting rules to a username,
     *       enforcing a maximum character limit.</li>
     *   <li>Demonstrating usage of {@link MoneyConverter} to convert a numeric value into a
     *       formatted currency string.</li>
     * </ol>
     *
     * @param arg command-line arguments (not used)
     */
    public static void main(String[] arg) {

        /*
         * Generating a reference number using GenerateReferenceNumber.
         * This demonstrates how to create a default reference number with no month specified.
         * For customisation, pass a specified month during object creation or use setters to configure.
         */
        GenerateReferenceNumber CurrentReferenceNumber = new GenerateReferenceNumber();
        // Print the generated reference number to the console
        out.println(CurrentReferenceNumber.generateReferenceNumber());

        /*
         * Demonstrating ValidateNameFormat usage.
         * The class is static, so no object instantiation is required.
         * By calling setLimit(5), any name provided will be truncated to 5 characters after formatting.
         * The getName() method retrieves the formatted name.
         * This shows how the name is adjusted based on company-defined rules and length limits.
         */
        NameFormatter formatter = NameFormatter.create()
                .withName("john", "doe")
                .withLimit(5);

        System.out.println("Formatted: " + formatter.getName()); // "J Doe"
        System.out.println("Limit reached? " + formatter.isLimitReached()); // true

        /*
         * Demonstrating MoneyConverter usage with various chained methods.
         * For example:
         *  converterLong.setBalance(2565).get(UK).toPound(); returns "£25.65" as a string.
         *  converterLong.setBalance(2565).get(UK).toPence(); returns "2565p"
         *  converterLong.setBalance(2565).get().toPound(); returns a double value (25.65)
         * If setBalance is not called, the converter uses a default value (0).
         */
        MoneyConverter converterLong = new MoneyConverter();
        // Print the formatted pound value (e.g. £25.65) for the provided raw balance (2565 pence).
        //out.println(converterLong.setBalance(2565).get(UK).toPound());

        // Create a bank account
        Bank myBank = new Bank();
        String bankMP = myBank.createAccount( NameFormatter.create().withName("Mateusz", "Podeszwa").unformattedName());
        String bankAM = myBank.createAccount( NameFormatter.create().withName("Anna", "Mustermann").unformattedName());
        String bankJD = myBank.createAccount( NameFormatter.create().withName("John", "Doe").unformattedName());
        String bankJB = myBank.createAccount( NameFormatter.create().withName("Jane", "Brown").unformattedName());

        BankAccount accountMP = myBank.getAccount(bankMP);
        BankAccount accountAM = myBank.getAccount(bankAM);
        BankAccount accountJD = myBank.getAccount(bankJD);
        BankAccount accountJB = myBank.getAccount(bankJB);

        accountMP.addFunds(50, "First Reference");
        accountMP.addFunds(1275, "Payslip");
        accountMP.removeFunds(260, "Food");
        accountMP.removeFunds(3203, "Gas");

        accountAM.addFunds(11200, "First Reference");
        accountAM.addFunds(1420, "Second Reference");
        accountAM.removeFunds(4000, "Third Reference");

        accountJD.addFunds(10050, "First Reference");
        accountJD.removeFunds(101300, "Second Reference");

        accountJB.addFunds(105300, "First Reference");
        accountJB.removeFunds(4030, "Second Reference");

        //accountMP.getTransactionHistory().forEach(System.out::println);
        //myBank.printAllAccountsSummary();
        out.println(Recipt.generateRecipt(accountMP));
    }
}
