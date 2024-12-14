package Main;

import java.io.*;


import java.util.*;

public class Testing {
    // Updated version of the decoder that handles comments after numeric values.
    public static void main(String[] args) {
        File file = new File("src/Documents/package.txt");

        List<Activity> activities = new ArrayList<>();
        Pricing lessThan10 = new Pricing();
        Pricing tenToNineteen = new Pricing();
        Pricing twentyPlus = new Pricing();
        List<Addon> activityAddons = new ArrayList<>();
        List<Addon> itineraryAddons = new ArrayList<>();

        boolean inActivities = false;
        boolean inActivity = false;
        boolean inPricingScheme = false;
        boolean inLessThan10 = false;
        boolean inTenToNineteen = false;
        boolean inTwentyPlus = false;
        boolean inAddons = false;
        boolean inActivityAddons = false;
        boolean inItineraryAddons = false;

        Activity currentActivity = null;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("[") && line.endsWith("]")) {
                    String section = line.substring(1, line.length() - 1);
                    switch (section) {
                        case "ACTIVITIES" -> inActivities = true;
                        case "END_ACTIVITIES" -> inActivities = false;
                        case "ACTIVITY" -> {
                            inActivity = true;
                            currentActivity = new Activity();
                        }
                        case "END_ACTIVITY" -> {
                            inActivity = false;
                            if (currentActivity != null) activities.add(currentActivity);
                            currentActivity = null;
                        }
                        case "PRICING_SCHEME" -> inPricingScheme = true;
                        case "END_PRICING_SCHEME" -> inPricingScheme = false;
                        case "LESS_THAN_10_ATTENDEES" -> inLessThan10 = true;
                        case "END_LESS_THAN_10_ATTENDEES" -> inLessThan10 = false;
                        case "TEN_TO_NINETEEN_ATTENDEES" -> inTenToNineteen = true;
                        case "END_TEN_TO_NINETEEN_ATTENDEES" -> inTenToNineteen = false;
                        case "TWENTY_PLUS_ATTENDEES" -> inTwentyPlus = true;
                        case "END_TWENTY_PLUS_ATTENDEES" -> inTwentyPlus = false;
                        case "ADDONS" -> inAddons = true;
                        case "END_ADDONS" -> inAddons = false;
                        case "ACTIVITY_ADDONS" -> inActivityAddons = true;
                        case "END_ACTIVITY_ADDONS" -> inActivityAddons = false;
                        case "ITINERARY_ADDONS" -> inItineraryAddons = true;
                        case "END_ITINERARY_ADDONS" -> inItineraryAddons = false;
                    }
                    continue;
                }

                // Parse lines outside of section headers
                if (inActivity) {
                    int colonIndex = line.indexOf(':');
                    int eqIndex = line.indexOf('=');
                    if (colonIndex > 0 && eqIndex > colonIndex) {
                        String key = line.substring(0, colonIndex).trim();
                        String type = line.substring(colonIndex + 1, eqIndex).trim();
                        String value = line.substring(eqIndex + 1).trim();

                        // Handle types
                        switch (key) {
                            case "CODE" -> currentActivity.code = value;
                            case "LONG_NAME" -> currentActivity.longName = value;
                            case "DESCRIPTION" -> currentActivity.description = value;
                            case "GENERAL_DESCRIPTION" -> currentActivity.generalDescription = value;
                            case "DATE" -> currentActivity.date = value;
                            case "TIME" -> currentActivity.time = value;
                            case "DURATION_MINUTES" -> currentActivity.durationMinutes = Integer.parseInt(value);
                            case "IS_INSURANCE_MANDATORY" -> currentActivity.isInsuranceMandatory = Boolean.parseBoolean(value);
                            case "LOCATION" -> currentActivity.location = value;
                            case "BASE_COST_PENCE" -> currentActivity.baseCostPence = Integer.parseInt(value);
                            case "EXPECTED_DURATION" -> currentActivity.expectedDuration = value;
                        }
                    }
                } else if (inPricingScheme) {
                    int colonIndex = line.indexOf(':');
                    int eqIndex = line.indexOf('=');
                    if (colonIndex > 0 && eqIndex > colonIndex) {
                        String key = line.substring(0, colonIndex).trim();
                        // type can be ignored for now as we know it's INT
                        // from the instructions
                        // remove comment if exists
                        String valStr = line.substring(eqIndex + 1).trim();
                        int commentIndex = valStr.indexOf(';');
                        if (commentIndex != -1) {
                            valStr = valStr.substring(0, commentIndex).trim();
                        }
                        int intVal = Integer.parseInt(valStr);

                        if (inLessThan10) {
                            switch (key) {
                                case "ACTIVITIES_1_2" -> lessThan10.activities_1_2 = intVal;
                                case "ACTIVITIES_3_5" -> lessThan10.activities_3_5 = intVal;
                                case "ACTIVITIES_6_PLUS" -> lessThan10.activities_6_plus = intVal;
                            }
                        } else if (inTenToNineteen) {
                            switch (key) {
                                case "ACTIVITIES_1_2" -> tenToNineteen.activities_1_2 = intVal;
                                case "ACTIVITIES_3_5" -> tenToNineteen.activities_3_5 = intVal;
                                case "ACTIVITIES_6_PLUS" -> tenToNineteen.activities_6_plus = intVal;
                            }
                        } else if (inTwentyPlus) {
                            switch (key) {
                                case "ACTIVITIES_1_2" -> twentyPlus.activities_1_2 = intVal;
                                case "ACTIVITIES_3_5" -> twentyPlus.activities_3_5 = intVal;
                                case "ACTIVITIES_6_PLUS" -> twentyPlus.activities_6_plus = intVal;
                            }
                        }
                    }
                } else if (inAddons) {
                    // For addons lines:
                    // ADDON_NAME:STRING=Something
                    // ADDON_COST_PENCE:INT=Value
                    // Pair them together
                    if (inActivityAddons || inItineraryAddons) {
                        if (line.startsWith("ADDON_NAME")) {
                            int eqIndex = line.indexOf('=');
                            String name = line.substring(eqIndex + 1).trim();
                            // next line expected for cost
                            String costLine = null;
                            try {
                                costLine = br.readLine();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (costLine != null) {
                                costLine = costLine.trim();
                                int costEq = costLine.indexOf('=');
                                int cost = Integer.parseInt(costLine.substring(costEq + 1).trim());
                                Addon addon = new Addon();
                                addon.name = name;
                                addon.costPence = cost;
                                if (inActivityAddons) {
                                    activityAddons.add(addon);
                                } else {
                                    itineraryAddons.add(addon);
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Print results
        System.out.println("ACTIVITIES:");
        for (Activity a : activities) {
            System.out.println("Code: " + a.code);
            System.out.println("Long Name: " + a.longName);
            System.out.println("Description: " + a.description);
            System.out.println("General Desc: " + a.generalDescription);
            System.out.println("Date: " + a.date);
            System.out.println("Time: " + a.time);
            System.out.println("Duration (min): " + a.durationMinutes);
            System.out.println("Insurance Mandatory: " + a.isInsuranceMandatory);
            System.out.println("Location: " + a.location);
            System.out.println("Base Cost (pence): " + a.baseCostPence);
            System.out.println("Expected Duration: " + a.expectedDuration);
            System.out.println("---------------------------------");
        }

        System.out.println("PRICING SCHEME:");
        System.out.println("Less than 10 attendees: 1-2=" + lessThan10.activities_1_2 + "%, 3-5=" + lessThan10.activities_3_5 + "%, 6+=" + lessThan10.activities_6_plus + "%");
        System.out.println("10 to 19 attendees: 1-2=" + tenToNineteen.activities_1_2 + "%, 3-5=" + tenToNineteen.activities_3_5 + "%, 6+=" + tenToNineteen.activities_6_plus + "%");
        System.out.println("20+ attendees: 1-2=" + twentyPlus.activities_1_2 + "%, 3-5=" + twentyPlus.activities_3_5 + "%, 6+=" + twentyPlus.activities_6_plus + "%");

        System.out.println("ACTIVITY ADDONS:");
        for (Addon ad : activityAddons) {
            System.out.println(ad.name + ": " + ad.costPence + " pence");
        }

        System.out.println("ITINERARY ADDONS:");
        for (Addon ad : itineraryAddons) {
            System.out.println(ad.name + ": " + ad.costPence + " pence");
        }
    }

    static class Activity {
        String code;
        String longName;
        String description;
        String generalDescription;
        String date;
        String time;
        int durationMinutes;
        boolean isInsuranceMandatory;
        String location;
        int baseCostPence;
        String expectedDuration;
    }

    static class Pricing {
        int activities_1_2;
        int activities_3_5;
        int activities_6_plus;
    }

    static class Addon {
        String name;
        int costPence;
    }
}
