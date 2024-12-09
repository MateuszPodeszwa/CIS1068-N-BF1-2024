/*
 * Copyright (c) 2024
 * Author: Mateusz Podeszwa
 * All rights reserved.
 */

package Main;

import Converter.MoneyConverter;
import Converter.ValidateNameFormat;
import Randomiser.GenerateReferenceNumber;

import static java.lang.System.out;
import java.math.BigDecimal;
import java.math.MathContext;

/**
 * The {@code Main} class is the entry point of the application.
 * <p>
 * It showcases how to use various classes such as {@link GenerateReferenceNumber},
 * {@link ValidateNameFormat}, and {@link MoneyConverter}. The main method
 * demonstrates generating a random reference number, validating and formatting user names,
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
     * A static field holding the full user name.
     * <p>
     * This field is used to demonstrate the {@link ValidateNameFormat} class.
     * </p>
     */
    private static final String fullUserName = "Mateusz Podeszwa";

    /**
     * The main method serves as the entry point for the application.
     * <p>
     * Steps demonstrated:
     * <ol>
     *   <li>Creating a {@link GenerateReferenceNumber} object and printing out generated reference numbers.</li>
     *   <li>Using {@link ValidateNameFormat} to apply company formatting rules to a user name,
     *       enforcing a maximum character limit.</li>
     *   <li>Demonstrating usage of {@link MoneyConverter} to convert a numeric value into a
     *       formatted currency string.</li>
     * </ol>
     * </p>
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
        out.println(ValidateNameFormat.setLimit(5).setName(fullUserName).getName());

        /*
         * Demonstrating MoneyConverter usage with various chained methods.
         * For example:
         *  converterLong.setBalance(2565).get("UK").toPound(); returns "£25.65" as a string.
         *  converterLong.setBalance(2565).get("UK").toPence(); returns "2565p"
         *  converterLong.setBalance(2565).get().toPound(); returns a double value (25.65)
         * If setBalance is not called, the converter uses a default value (0).
         */
        MoneyConverter converterLong = new MoneyConverter();
        // Print the formatted pound value (e.g. £25.65) for the provided raw balance (2565 pence).
        out.println(converterLong.setBalance(2565).get("UK").toPound());
    }
}
