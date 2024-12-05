/*
 * Copyright (c) 2024
 * Author: Mateusz Podeszwa
 * All rights reserved.
 */

package Randomiser;

import java.time.LocalDate;
import java.time.Month;

/**
 * The {@code GenerateReferenceNumber} class provides functionality to generate a unique reference number
 * consisting of an identifier letter, a seed number, and a quarter label based on the specified or current month.
 * The reference number can be customised in terms of its length and the month used for generation.
 *
 * <p>The format of the generated reference number is as follows:
 * <pre>
 * [Identifier Letter][Seed Number][Quarter Label]
 * </pre>
 * where:
 * <ul>
 *   <li><b>Identifier Letter</b>: A specified or randomly generated uppercase letter.</li>
 *   <li><b>Seed Number</b>: A unique number of specified length.</li>
 *   <li><b>Quarter Label</b>: A letter representing the quarter of the year ('A' for Q1, 'B' for Q2, etc.).</li>
 * </ul>
 *
 * @author
 *     Mateusz Podeszwa
 * @version 1.0
 * @since 2024
 */
public class GenerateReferenceNumber {
    private Month month;
    private int referenceLength;

    /**
     * Constructs a {@code GenerateReferenceNumber} object with the specified month.
     *
     * @param month the month to be used in generating the reference number.
     */
    public GenerateReferenceNumber(Month month) {
        this.month = month;
        this.referenceLength = 4;
    }

    /**
     * Constructs a {@code GenerateReferenceNumber} object using the current month.
     */
    public GenerateReferenceNumber() {
        this.month = LocalDate.now().getMonth();
        this.referenceLength = 4;
    }

    /**
     * Returns the month used in generating the reference number.
     *
     * @return the month.
     */
    public Month getMonth() {
        return this.month;
    }

    /**
     * Returns the length of the reference number.
     *
     * @return the reference length.
     */
    public int getReferenceLength() {
        return this.referenceLength;
    }

    /**
     * Sets the length of the reference number.
     *
     * @param referenceLength the desired length of the reference number; must be greater than 1.
     * @throws IllegalArgumentException if {@code referenceLength} is less than or equal to 1.
     */
    public void setReferenceLength(int referenceLength) {
        if (referenceLength > 1)
            this.referenceLength = referenceLength;
        else
            throw new IllegalArgumentException("Reference length must be greater than 1.");
    }

    /**
     * Returns the actual current month.
     *
     * @return the current month.
     */
    public Month getActualMonth() {
        return LocalDate.now().getMonth();
    }

    /**
     * Sets the month to be used in generating the reference number.
     *
     * @param month the month to set.
     */
    public void setMonth(Month month) {
        this.month = month;
    }

    /**
     * Generates a reference number using the specified identifier letter.
     *
     * @param identifierLetter the letter to be used as an identifier in the reference number.
     * @return the generated reference number.
     */
    public String generateReferenceNumber(char identifierLetter) {
        String seedNumber = new GenerateUniqueNumber(this.referenceLength).toString();
        char quarterLabel = getQuarterLabel(this.month.firstMonthOfQuarter());
        identifierLetter = Character.toUpperCase(identifierLetter);

        return String.valueOf(identifierLetter).concat(seedNumber).concat(String.valueOf(quarterLabel));
    }

    /**
     * Generates a reference number using a randomly generated identifier letter.
     *
     * @return the generated reference number.
     */
    public String generateReferenceNumber() {
        String seedNumber = new GenerateUniqueNumber(this.referenceLength).toString();
        char quarterLabel = getQuarterLabel(this.month.firstMonthOfQuarter());
        char identifierLetter = Character.toUpperCase(getRandomChar());

        return String.valueOf(identifierLetter)
                .concat(seedNumber)
                .concat(String.valueOf(quarterLabel))
                .trim();
    }

    /**
     * Generates a random lowercase character.
     *
     * @return a random lowercase character.
     */
    private char getRandomChar() {
        return (char) ('a' + new GenerateUniqueNumber(1).toInteger());
    }

    /**
     * Returns the quarter label corresponding to the given first month of the quarter.
     *
     * @param firstMonthOfQuarter the first month of the quarter.
     * @return the quarter label ('A' for Q1, 'B' for Q2, 'C' for Q3, 'D' for Q4).
     * @throws IllegalArgumentException if the month is invalid.
     */
    private static char getQuarterLabel(Month firstMonthOfQuarter) {
        return switch (firstMonthOfQuarter) {
            case JANUARY -> 'A';
            case APRIL -> 'B';
            case JULY -> 'C';
            case OCTOBER -> 'D';
            default -> throw new IllegalArgumentException("Invalid month: " + firstMonthOfQuarter);
        };
    }
}
