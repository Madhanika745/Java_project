package main;

import java.util.Scanner;

import manager.ActivityManager;
import model.CollegeActivity;
import model.EnvironmentalData;
import storage.FileStorage;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ActivityManager manager = new ActivityManager();

        // Load activities saved from the previous session
        FileStorage.load(manager);

        // Sample environmental conditions
        EnvironmentalData environment =
                new EnvironmentalData(
                        60,     // Carbon intensity
                        32,     // Temperature
                        40,     // Renewable availability
                        30      // Water stress
                );

        int choice;

        do {

            System.out.println("\n=================================");
            System.out.println("        ECOIMPACT");
            System.out.println(" Green Campus Activity Manager");
            System.out.println("=================================");

            System.out.println("1. Add College Activity");
            System.out.println("2. Search Activity");
            System.out.println("3. Delete Activity");
            System.out.println("4. Process Next Activity");
            System.out.println("5. View Pending Activities");
            System.out.println("6. View All Activities");
            System.out.println("7. View Decision History");
            System.out.println("8. View Environmental Conditions");
            System.out.println("9. View Summary");
            System.out.println("10. Exit");

            System.out.print("\nEnter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    addActivity(sc, manager);
                    FileStorage.save(manager);
                    break;

                case 2:
                    searchActivity(sc, manager);
                    break;

                case 3:
                    deleteActivity(sc, manager);
                    FileStorage.save(manager);
                    break;

                case 4:
                    manager.processNextActivity(environment);
                    FileStorage.save(manager);
                    break;

                case 5:
                    manager.displayPendingActivities();
                    break;

                case 6:
                    manager.displayAllActivities();
                    break;

                case 7:
                    manager.displayDecisionHistory();
                    break;

                case 8:
                    displayEnvironment(environment);
                    break;

                case 9:
                    displaySummary(manager);
                    break;

                case 10:
                    FileStorage.save(manager);
                    System.out.println("\nExiting EcoImpact...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 10);

        sc.close();
    }


    // ==========================================
    // ADD ACTIVITY
    // ==========================================

    public static void addActivity(
            Scanner sc,
            ActivityManager manager) {

        System.out.println("\n===== ADD COLLEGE ACTIVITY =====");

        System.out.print("Enter Activity ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Activity Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Activity Type: ");
        String type = sc.nextLine();

        System.out.print("Enter Duration (hours): ");
        int duration = sc.nextInt();

        System.out.print("Enter Resource Requirement: ");
        int resourceRequirement = sc.nextInt();

        System.out.print("Enter Equipment Requirement: ");
        int equipmentRequirement = sc.nextInt();

        System.out.print("Enter Deadline (days): ");
        int deadline = sc.nextInt();

        System.out.print("Enter Urgency (1-3): ");
        int urgency = sc.nextInt();

        System.out.print("Can activity be rescheduled? (true/false): ");
        boolean rescheduleAllowed = sc.nextBoolean();

        CollegeActivity activity =
                new CollegeActivity(
                        id,
                        name,
                        type,
                        duration,
                        resourceRequirement,
                        equipmentRequirement,
                        deadline,
                        urgency,
                        rescheduleAllowed
                );

        manager.addActivity(activity);
    }


    // ==========================================
    // SEARCH ACTIVITY
    // ==========================================

    public static void searchActivity(
            Scanner sc,
            ActivityManager manager) {

        System.out.println("\n===== SEARCH ACTIVITY =====");

        System.out.print("Enter Activity ID: ");
        int id = sc.nextInt();

        CollegeActivity activity =
                manager.searchActivity(id);

        if (activity == null) {

            System.out.println("Activity not found.");

        } else {

            System.out.println("\nActivity Found:");
            System.out.println(activity);
        }
    }


    // ==========================================
    // DELETE ACTIVITY
    // ==========================================

    public static void deleteActivity(
            Scanner sc,
            ActivityManager manager) {

        System.out.println("\n===== DELETE ACTIVITY =====");

        System.out.print("Enter Activity ID: ");
        int id = sc.nextInt();

        manager.deleteActivity(id);
    }


    // ==========================================
    // ENVIRONMENT
    // ==========================================

    public static void displayEnvironment(
            EnvironmentalData environment) {

        System.out.println("\n===== ENVIRONMENTAL CONDITIONS =====");

        System.out.println(
                "Carbon Intensity: "
                + environment.getCarbonIntensity());

        System.out.println(
                "Temperature: "
                + environment.getTemperature());

        System.out.println(
                "Renewable Availability: "
                + environment.getRenewableAvailability());

        System.out.println(
                "Water Stress: "
                + environment.getWaterStress());
    }


    // ==========================================
    // SUMMARY
    // ==========================================

    public static void displaySummary(
            ActivityManager manager) {

        System.out.println("\n===== ECOIMPACT SUMMARY =====");

        System.out.println(
                "Total Activities: "
                + manager.getTotalActivities());

        System.out.println(
                "Processed Activities: "
                + manager.getCompletedCount());

        System.out.println(
                "Pending Activities: "
                + manager.getPendingCount());
    }
}