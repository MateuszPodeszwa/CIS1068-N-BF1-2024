/*
 * Copyright (c) 2024
 * Author: Mateusz Podeszwa
 * All rights reserved.
 */

package Main;

import Application.DataPlanner.Activity.Activity;
import static Application.DataPlanner.Activity.Activity.Location;

import Application.DataPlanner.Activity.ActivityAddons.AddOn;
import Application.DataPlanner.Activity.ActivityAddons.InsuranceAddon;
import Application.DataPlanner.Activity.ActivityAddons.PhotographyAddon;
import Application.DataPlanner.Activity.ActivityAddons.TravelAddon;
import Application.DataPlanner.Activity.ActivityFactory;
import Application.DataPlanner.Activity.ActivityMetadata;
import Application.DataPlanner.Activity.ActivityRegistry;
import Application.DataPlanner.Activity.ActivityTypes.BaseActivity;
import Application.DataPlanner.Activity.ActivityTypes.CookeryClassActivity;
import Application.DataPlanner.Activity.ActivityTypes.OrigamiActivity;
import Application.DataPlanner.Activity.ActivityTypes.SASCourseActivity;
import Application.DataPlanner.itinerary.Itinerary;
import Application.DataPlanner.itinerary.ItineraryAddon;
import Utils.Converter.MoneyConverter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Step 1: Create (or load) the registry.
        ActivityRegistry week34_2025Register = new ActivityRegistry();
        ActivityRegistry week35_2025Register = new ActivityRegistry();

        /*
         * By default, your ActivityRegistry might already contain
         * some entries in its constructor.
         * Optionally, you can manually add more:
         */
        week34_2025Register.addActivity(
                new ActivityMetadata(
                        week34_2025Register.generateIndividualReference("ORG01", OrigamiActivity.classReference),
                        "Origami Workshop",
                        "Learn paper folding techniques",
                        Location.OUTDOORS,
                        LocalDateTime.of(2025, 4, 20, 8, 45),
                        61_58,
                        30,
                        true,
                        new ArrayList<>(List.of(
                                new InsuranceAddon(),
                                new PhotographyAddon(),
                                new TravelAddon()
                        )))
        );
        week34_2025Register.addActivity(
                new ActivityMetadata(
                        week34_2025Register.generateIndividualReference("ORG02", OrigamiActivity.classReference),
                        "Origami Workshop",
                        "Learn paper folding techniques",
                        Location.OUTDOORS,
                        LocalDateTime.of(2025, 4, 27, 8, 45),
                        61_58,
                        30,
                        true,
                        new ArrayList<>(List.of(
                                new PhotographyAddon(),
                                new TravelAddon()
                        )))
        );
        week34_2025Register.addActivity(
                new ActivityMetadata(
                        week34_2025Register.generateIndividualReference("SAS01", SASCourseActivity.classReference),
                        "SAS Programming Course",
                        "Learn how to use SAS for data analysis",
                        Location.ONLINE,
                        LocalDateTime.of(2024, 4, 22, 12, 0),
                        123_26,
                        70,
                        false,
                        new ArrayList<>(List.of(
                                new TravelAddon()
                        )))
        );

        week34_2025Register.addActivity(
                new ActivityMetadata(
                        week34_2025Register.generateIndividualReference("COK01", CookeryClassActivity.classReference),
                        "Cooking Class",
                        "Learn how to use plates for food analysis",
                        Location.HOME,
                        LocalDateTime.of(2025, 4, 29, 12, 30),
                        822_32,
                        120,
                        true,
                        new ArrayList<>(List.of(
                                new InsuranceAddon()
                        )))
        );

        week35_2025Register.addActivity(
                new ActivityMetadata(
                        week35_2025Register.generateIndividualReference("ORG03", OrigamiActivity.classReference),
                        "Origami Workshop",
                        "Learn paper folding techniques",
                        Location.OUTDOORS,
                        LocalDateTime.of(2025, 4, 28, 8, 45),
                        61_58,
                        30,
                        true,
                        new ArrayList<>(List.of(
                                new InsuranceAddon()
                        )))
        );

        System.out.println("DEBUG: " + week34_2025Register.listAllActivitiesOfAKind("SAS"));
        System.out.println(ActivityRegistry.getIndividualReferenceList());

        // Step 2: Create the factory, passing the register
        ActivityFactory week34Activities = new ActivityFactory(week34_2025Register);
        ActivityFactory week35Activities = new ActivityFactory(week35_2025Register);

        // Step 3: Create activities using the factory
        // Activity Monday34Activity = week34Activities.initialiseActivity(OrigamiActivity.class, "ORG01");

        Itinerary week34to35_2025Package = new Itinerary(List.of(
                week34Activities.initialiseActivity(OrigamiActivity.class, "ORG01"),
                week34Activities.initialiseActivity(OrigamiActivity.class, "ORG02"),
                week34Activities.initialiseActivity(SASCourseActivity.class, "SAS01"),
                week34Activities.initialiseActivity(CookeryClassActivity.class, "COK01"),
                week35Activities.initialiseActivity(OrigamiActivity.class, "ORG03")
        ));

        week34to35_2025Package.addItineraryAddon(ItineraryAddon.ACCOMMODATION);
        week34to35_2025Package.addItineraryAddon(ItineraryAddon.LUNCH);
        week34to35_2025Package.addItineraryAddon(ItineraryAddon.COFFEE);

        System.out.println(week34to35_2025Package.getItineraryAddons().stream().mapToInt(ItineraryAddon::getPrice).sum());



        // To add addons, now when you have done building each activity, you can call add addon method for each, and then update final cost

        // (Optional) Step 4: Possibly pass these activities into an itinerary or further processing

    }









    public void ifYouHaveEverEncounteredThatOneBizarreNullPointerExceptionInsideAnOverengineeredSpringApplicationThatUsesReflectionToDynamicallyGenerateProxiesForYourProxiesWhileSimultaneouslyThrowingClassNotFoundErrorsBecauseOfAMisconfiguredClassLoaderThenYouMightFindThisExceptionallyLongMethodNameMildlyEntertainingEvenThoughItSeverelyViolatesEVERY_NAMING_CONVENTIONEverConceivedButHeySometimesWeAllNeedToIndulgeInALittleRidiculousnessToRemindOurselvesThatJavaIsNOTJustACorporateWorkhorseButAlsoACanvasForOurMostUnhingedNamingAspirationsAndInsomniaDrivenCodingExperiments() {
        System.out.println(" It's an Easter egg! EVEN PROGRAMMERS HAVE SENSE OF HUMOUR! At least I really hope so...? :) ");
    }
}

















