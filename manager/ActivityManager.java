package manager;

import datastructures.MyHashMap;
import datastructures.MyLinkedList;
import datastructures.MyQueue;
import datastructures.MyStack;
import engine.DecisionEngine;
import model.CollegeActivity;
import model.Decision;
import model.EnvironmentalData;

public class ActivityManager {

    // =========================================================
    // DATA STRUCTURES
    // =========================================================

    private MyLinkedList activities;

    private MyHashMap activityMap;

    private MyQueue pendingQueue;

    private MyStack decisionHistory;

    private DecisionEngine decisionEngine;

    private int completedCount;


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ActivityManager() {

        activities = new MyLinkedList();

        activityMap = new MyHashMap();

        pendingQueue = new MyQueue();

        decisionHistory = new MyStack();

        decisionEngine = new DecisionEngine();

        completedCount = 0;
    }


    // =========================================================
    // ADD ACTIVITY
    // =========================================================

    public void addActivity(CollegeActivity activity) {

        if (activity == null) {
            return;
        }

        activities.add(activity);

        activityMap.put(
                activity.getActivityId(),
                activity
        );

        pendingQueue.enqueue(activity);

        System.out.println(
                "Activity added successfully."
        );
    }


    // =========================================================
    // SEARCH ACTIVITY
    // =========================================================

    public CollegeActivity searchActivity(int id) {

        return activityMap.get(id);
    }


    // =========================================================
    // DELETE ACTIVITY
    // =========================================================

    public void deleteActivity(int id) {

        CollegeActivity activity =
                activityMap.get(id);

        if (activity == null) {

            System.out.println(
                    "Activity not found."
            );

            return;
        }


        // Remove from Linked List
        activities.delete(id);


        // Remove from HashMap
        activityMap.remove(id);


        // Remove from pending Queue
        pendingQueue.removeById(id);


        System.out.println(
                "Activity deleted successfully."
        );
    }


    // =========================================================
    // DISPLAY ALL ACTIVITIES
    // =========================================================

    public void displayAllActivities() {

        if (activities.isEmpty()) {

            System.out.println(
                    "No activities available."
            );

            return;
        }


        System.out.println(
                "\n===== ALL COLLEGE ACTIVITIES ====="
        );

        activities.display();
    }


    // =========================================================
    // PROCESS NEXT ACTIVITY
    // =========================================================

    public void processNextActivity(
            EnvironmentalData environment) {

        if (pendingQueue.isEmpty()) {

            System.out.println(
                    "No pending activities."
            );

            return;
        }


        // FIFO operation
        CollegeActivity activity =
                pendingQueue.dequeue();


        // Calculate decision
        Decision decision =
                decisionEngine.makeDecision(
                        activity,
                        environment
                );


        // Update activity status
        activity.setStatus(
                decision.getAction()
        );


        // Store decision in Stack
        decisionHistory.push(decision);


        completedCount++;


        // Display result

        System.out.println(
                "\n===== ECOIMPACT DECISION ====="
        );

        System.out.println(
                "Activity: "
                + activity.getActivityName()
        );

        System.out.println(
                "Decision: "
                + decision.getAction()
        );

        System.out.println(
                "Impact Score: "
                + String.format(
                        "%.2f",
                        decision.getImpactScore()
                )
        );

        System.out.println(
                "Reason: "
                + decision.getReason()
        );
    }


    // =========================================================
    // DISPLAY PENDING ACTIVITIES
    // =========================================================

    public void displayPendingActivities() {

        System.out.println(
                "\n===== PENDING ACTIVITIES ====="
        );


        if (pendingQueue.isEmpty()) {

            System.out.println(
                    "No pending activities."
            );

            return;
        }


        pendingQueue.display();
    }


    // =========================================================
    // DISPLAY DECISION HISTORY
    // =========================================================

    public void displayDecisionHistory() {

        System.out.println(
                "\n===== DECISION HISTORY ====="
        );


        if (decisionHistory.isEmpty()) {

            System.out.println(
                    "No decisions available."
            );

            return;
        }


        decisionHistory.display();
    }


    // =========================================================
    // GET TOTAL ACTIVITIES
    // =========================================================

    public int getTotalActivities() {

        return activities.size();
    }


    // =========================================================
    // GET COMPLETED COUNT
    // =========================================================

    public int getCompletedCount() {

        return completedCount;
    }


    // =========================================================
    // GET PENDING COUNT
    // =========================================================

    public int getPendingCount() {

        return pendingQueue.size();
    }


    // =========================================================
    // GET ALL ACTIVITIES
    // Used by FileStorage
    // =========================================================

    public CollegeActivity[] getAllActivities() {

        return activities.toArray();
    }


    // =========================================================
    // GET PENDING ACTIVITIES
    // Used by FileStorage
    // =========================================================

    public CollegeActivity[] getPendingActivities() {

        return pendingQueue.toArray();
    }


    // =========================================================
    // GET DECISION HISTORY
    // Used by FileStorage
    // =========================================================

    public Decision[] getDecisionHistory() {

        return decisionHistory.toArray();
    }


    // =========================================================
    // RESTORE COUNTERS
    // Used when loading saved data
    // =========================================================

    public void restoreCounters(
            int totalActivities,
            int completedActivities) {

        /*
         * totalActivities does not need to be directly stored
         * because the Linked List size automatically represents
         * the number of activities after restoration.
         */

        completedCount =
                completedActivities;
    }


    // =========================================================
    // RESTORE ACTIVITY
    // Used by FileStorage
    // =========================================================

    public void restoreActivity(
            CollegeActivity activity) {

        if (activity == null) {
            return;
        }


        activities.add(activity);


        activityMap.put(
                activity.getActivityId(),
                activity
        );
    }


    // =========================================================
    // RESTORE PENDING ACTIVITY
    // Used by FileStorage
    // =========================================================

    public void restorePending(
            CollegeActivity activity) {

        if (activity == null) {
            return;
        }


        pendingQueue.enqueue(activity);
    }


    // =========================================================
    // RESTORE DECISION
    // Used by FileStorage
    // =========================================================

    public void restoreDecision(
            Decision decision) {

        if (decision == null) {
            return;
        }


        decisionHistory.push(decision);
    }
}