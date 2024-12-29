/*
 * Copyright (c) 2024
 * Author: Mateusz Podeszwa
 * All rights reserved.
 */

package Main;

import Application.DataPlanner.Activity.Activity;
import static Application.DataPlanner.Activity.Activity.Location;
import Application.DataPlanner.Activity.ActivityFactory;
import Application.DataPlanner.Activity.ActivityMetadata;
import Application.DataPlanner.Activity.ActivityRegistry;
import Application.DataPlanner.Activity.ActivityTypes.OrigamiActivity;
import Application.DataPlanner.Activity.ActivityTypes.SASCourseActivity;
import Utils.Converter.MoneyConverter;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Step 1: Create (or load) the registry.
        ActivityRegistry registry = new ActivityRegistry();

        /*
         * By default, your ActivityRegistry might already contain
         * some entries in its constructor.
         * Optionally, you can manually add more:
         */
        registry.addActivity(
                new ActivityMetadata(
                        registry.generateIndividualReference("CAB32", OrigamiActivity.classReference),
                        "Origami Workshop",
                        "Learn paper folding techniques",
                        Location.OUTDOORS,
                        LocalDateTime.of(2025, 2, 20, 14, 50),
                        25_00,
                        60,
                        true)
        );

        registry.addActivity(
                new ActivityMetadata(
                        registry.generateIndividualReference("AAS2", OrigamiActivity.classReference),
                        "Origami Workshop 2",
                        "Learn paper folding techniques 2",
                        Activity.Location.HOME,
                        LocalDateTime.now().plusDays(22),
                        10200,        // £10.00
                        160,          // 60 minutes
                        false)
        );

        registry.addActivity(
                new ActivityMetadata(
                        registry.generateIndividualReference("C3B32", SASCourseActivity.classReference),
                        "SAS Workshop",
                        "Learn SAS techniques",
                        Activity.Location.OFFICE,
                        LocalDateTime.now().plusDays(12),
                        200,        // £10.00
                        10,          // 60 minutes
                        true)
        );

        System.out.println("DEBUG: " + registry.listAllActivitiesOfAKind("SAS"));

        // Step 2: Create the factory, passing the registr
        ActivityFactory factory = new ActivityFactory(registry);

        // Step 3: Create activities using the factory
        Activity origamiAct = factory.initialiseActivity(OrigamiActivity.class, "CAB32");

        System.out.println("DEBUG: " + origamiAct.getReadableName());
        System.out.println("DEBUG: " + origamiAct.getCode());
        System.out.println("DEBUG: " + origamiAct.getDurationInMinutes());
        System.out.println("DEBUG: " + origamiAct.getBaseCostInPence());
        System.out.println("DEBUG: " + origamiAct.getLocation());
        System.out.println("DEBUG: " + origamiAct.getDateAndTime());
        System.out.println("DEBUG: " + origamiAct.isInsuranceMandatory());

        // (Optional) Step 4: Possibly pass these activities into an itinerary or further processing
    }
}
