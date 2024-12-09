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
 * The {@code Main} class serves as the entry point of the application. It demonstrates
 * the usage of the {@link GenerateReferenceNumber} class by generating and printing
 * reference numbers.
 * <p>
 * This class imports the {@code GenerateReferenceNumber} from the {@code Randomiser} package
 * and utilises it within the {@code main} method.
 * </p>
 * <p>
 * Example usage:
 * <pre>{@code
 * GenerateReferenceNumber m = new GenerateReferenceNumber();
 * System.out.println(m.generateReferenceNumber());
 * }</pre>
 * <p>
 * Note: Ensure that the {@code Randomiser} package is correctly referenced in your project.
 * </p>
 *
 * @author
 *     Mateusz Podeszwa
 * @version 1.0
 * @since 2024
 */
public class Main {

    private static final String fullUserName = "Mateusz Podeszwa";
    /**
     * The main method serves as the entry point of the application. It creates an instance
     * of {@code GenerateReferenceNumber} and prints out two generated reference numbers.
     *
     * @param arg command-line arguments (not used).
     */
    public static void main(String[] arg) {

        /*
        * POSSIBLE IMPLEMENTATIONS OF GenerateReferenceNumber
        * Unless specified, the GenerateReferenceNumber will take the current month.
        * To select the month of liking, specify it in the Object Initialisation.
        *   GenerateReferenceNumber CurrentReferenceNumber = new GenerateReferenceNumber(Month.JULY);
        *   GenerateReferenceNumber CurrentReferenceNumber = new GenerateReferenceNumber(Month.DECEMBER);
        * The Class is using Java's Month API therefore it is possible to pass the argument without the Enumeration Month specifier.
        * To do so `import static java.time.Month.*;`
        *   GenerateReferenceNumber CurrentReferenceNumber = new GenerateReferenceNumber(DECEMBER);
        *   GenerateReferenceNumber CurrentReferenceNumber = new GenerateReferenceNumber(APRIL);
        *
        * To specify the length of the reference number use setter:
        *   CurrentReferenceNumber.setReferenceLength(20);
        * The .setReferenceNumber takes integer as an argument.
        * It is also possible to receive currently selected month, an actual current month, get the information about current reference length.
        *
        * To edit the reference number prefix character, please pass it as a char argument while calling .generateReferenceNumber method which is overloaded.
        *   CurrentReferenceNumber.generateReferenceNumber('T')
        * If not specified the .generateReferenceNumber will automatically and randomly generate a char alphabetic symbol.
        * For more information please visit the documentation.
        * */

        GenerateReferenceNumber CurrentReferenceNumber = new GenerateReferenceNumber();
        out.println(CurrentReferenceNumber.generateReferenceNumber());

        /*
        * POSSIBLE IMPLEMENTATION OF ValidateNameFormat.
        * ValidateNameFormat is a static clas meaning it can be referenced and used without declaring an object.
        *   import Converter.ValidateNameFormat;
        * The class uses method chaining providing a flexible and adaptable API for future use.
        * To validate if the name follows the company rule-set these are possible calls that are valid:
        *   1. The most simple implementation, to pass the name and format it to follow the company policy
        *      if passed: Mateusz Podeszwa, the method returns the object .getClass()
        *       ValidateNameFormat.withName("Adam", "PPAPAP")
        *      To receive the formated name, we can call .getName()
        *       ValidateNameFormat.withName("Adam", "PPAPAP").getName();
        *      The function will return A Ppapap
        *      Additionally the function withName is overloaded and accepts both firstName, secondName and fullName such as "Adam Ppapap".
        *      The function then splits the name where the space is.
        *      Additionally, it is possible to receive boolean value .isTheLimitReached which checks if the provided length limit is reached.
        *      Works only with .setLimit()
        *       ValidateNameFormat.withName("Adam", "PPAPAP").isTheLimitReached // Always return false
        *   2. To set a max length limit on a name to comply with the company policy. Call .setLimit method with takes int as an argument.
        *       ValidateNameFormat.setLimit(5).setName("Mateusz", "Podeszwa")
        *      As previously, the method returns an object. You could notice that we replaced .withName with the .setName it is because .withName is static.
        *      To receive the formated name use .getName method.
        *       ValidateNameFormat.setLimit(5).setName("Mateusz", "Podeszwa").getName();
        *      To check if the limit has been reached use .isLimitReached
        *       ValidateNameFormat.setLimit(5).setName("Mateusz", "Podeszwa").isLimitReached // Returns True as limit of 5 char has been reached
        *      If the limit has been reached and implemented the .getName() method, the name will be returned as a substring, with the maximum characters
        *      that was provided in the .setLimit. For example, if the limit is 5 and the initial name had 20 char, the returned name will be a substring of .substring(0, 5).
        *      The setLimit only works on the formated name, never on the raw name, for example, it would fork on M Podeszwa instead of passed argument Mateusz Podeszwa.
        * */

        out.println(ValidateNameFormat.setLimit(5).setName(fullUserName).getName());

        MoneyConverter converterLong = new MoneyConverter(() -> 0);
        out.println( converterLong.setBalance(2000).get("US").toPound() );
    }
}