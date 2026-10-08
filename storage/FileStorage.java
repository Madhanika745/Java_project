package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import manager.ActivityManager;
import model.CollegeActivity;
import model.Decision;

public class FileStorage {

    // =========================================================
    // FILE SETTINGS
    // =========================================================

    private static final String DIR = "data";

    private static final String DATA_FILE =
            DIR + File.separator + "ecoimpact.dat";

    private static final String TEMP_FILE =
            DATA_FILE + ".tmp";

    private static final String BACKUP_FILE =
            DATA_FILE + ".bak";

    private static final String HEADER =
            "ECOIMPACT-DATA|1";

    private static final String FOOTER =
            "END";

    private static final String SEP =
            "|";


    // =========================================================
    // SAVE DATA
    // =========================================================

    public static void save(ActivityManager manager) {

        File directory = new File(DIR);

        // Create data folder if it does not exist
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File temp = new File(TEMP_FILE);
        File target = new File(DATA_FILE);

        try {

            // -----------------------------------------------------
            // Create temporary file
            // -----------------------------------------------------

            try (
                FileOutputStream fos =
                        new FileOutputStream(temp);

                BufferedWriter out =
                        new BufferedWriter(
                                new OutputStreamWriter(
                                        fos,
                                        StandardCharsets.UTF_8))
            ) {

                // -------------------------------------------------
                // HEADER
                // -------------------------------------------------

                out.write(HEADER);
                out.newLine();


                // -------------------------------------------------
                // COUNTERS
                // -------------------------------------------------

                out.write(
                        "C"
                        + SEP
                        + manager.getTotalActivities()
                        + SEP
                        + manager.getCompletedCount()
                );

                out.newLine();


                // -------------------------------------------------
                // SAVE ALL ACTIVITIES
                // -------------------------------------------------

                CollegeActivity[] allActivities =
                        manager.getAllActivities();

                for (CollegeActivity activity : allActivities) {

                    out.write(
                            "A"
                            + SEP
                            + activity.getActivityId()
                            + SEP
                            + clean(activity.getActivityName())
                            + SEP
                            + clean(activity.getActivityType())
                            + SEP
                            + activity.getDuration()
                            + SEP
                            + activity.getResourceRequirement()
                            + SEP
                            + activity.getEquipmentRequirement()
                            + SEP
                            + activity.getDeadline()
                            + SEP
                            + activity.getUrgency()
                            + SEP
                            + activity.isRescheduleAllowed()
                            + SEP
                            + clean(activity.getStatus())
                    );

                    out.newLine();
                }


                // -------------------------------------------------
                // SAVE PENDING QUEUE
                // -------------------------------------------------

                CollegeActivity[] pendingActivities =
                        manager.getPendingActivities();

                for (CollegeActivity activity :
                        pendingActivities) {

                    out.write(
                            "P"
                            + SEP
                            + activity.getActivityId()
                    );

                    out.newLine();
                }


                // -------------------------------------------------
                // SAVE DECISION HISTORY
                // -------------------------------------------------

                Decision[] decisions =
                        manager.getDecisionHistory();

                for (Decision decision : decisions) {

                    out.write(
                            "D"
                            + SEP
                            + decision.getActivityId()
                            + SEP
                            + clean(decision.getAction())
                            + SEP
                            + decision.getImpactScore()
                            + SEP
                            + clean(decision.getReason())
                    );

                    out.newLine();
                }


                // -------------------------------------------------
                // FOOTER
                // -------------------------------------------------

                out.write(FOOTER);
                out.newLine();


                // Force data into the file
                out.flush();

                fos.getFD().sync();
            }


            // -----------------------------------------------------
            // Replace old file with new file
            // -----------------------------------------------------

            moveIntoPlace(temp, target);


            System.out.println(
                    "EcoImpact data saved successfully."
            );


        } catch (IOException e) {

            System.out.println(
                    "Warning: could not save data ("
                    + e.getMessage()
                    + ")"
            );

            temp.delete();
        }
    }


    // =========================================================
    // MOVE TEMP FILE INTO PLACE
    // =========================================================

    private static void moveIntoPlace(
            File temp,
            File target) throws IOException {

        try {

            Files.move(
                    temp.toPath(),
                    target.toPath(),
                    StandardCopyOption.ATOMIC_MOVE
            );

        } catch (AtomicMoveNotSupportedException e) {

            Files.move(
                    temp.toPath(),
                    target.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
    }


    // =========================================================
    // LOAD DATA
    // =========================================================

    public static void load(ActivityManager manager) {

        // -----------------------------------------------------
        // Remove leftover temporary file
        // -----------------------------------------------------

        File temp = new File(TEMP_FILE);

        if (temp.exists()) {
            temp.delete();
        }


        // -----------------------------------------------------
        // Check whether saved file exists
        // -----------------------------------------------------

        File file = new File(DATA_FILE);

        if (!file.exists()) {

            System.out.println(
                    "No previous EcoImpact data found."
            );

            return;
        }


        int activities = 0;
        int pending = 0;
        int decisions = 0;
        int skipped = 0;

        boolean headerOk = false;
        boolean complete = false;


        // =====================================================
        // READ FILE
        // =====================================================

        try (
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream(file),
                                    StandardCharsets.UTF_8))
        ) {

            String line;

            boolean firstLine = true;


            while ((line = in.readLine()) != null) {

                // ------------------------------------------------
                // Ignore empty lines
                // ------------------------------------------------

                if (line.trim().isEmpty()) {
                    continue;
                }


                // ------------------------------------------------
                // CHECK HEADER
                // ------------------------------------------------

                if (firstLine) {

                    firstLine = false;

                    if (line.equals(HEADER)) {

                        headerOk = true;

                        continue;
                    }


                    System.out.println(
                            "Warning: "
                            + DATA_FILE
                            + " is not a recognised "
                            + "EcoImpact file."
                    );

                    return;
                }


                // ------------------------------------------------
                // CHECK FOOTER
                // ------------------------------------------------

                if (line.equals(FOOTER)) {

                    complete = true;

                    continue;
                }


                // ------------------------------------------------
                // SPLIT LINE
                // ------------------------------------------------

                String[] p =
                        line.split("\\|", -1);


                try {

                    switch (p[0]) {


                        // ========================================
                        // COUNTERS
                        // ========================================

                        case "C":

                            if (p.length != 3) {

                                skipped++;

                                break;
                            }


                            manager.restoreCounters(
                                    Integer.parseInt(p[1]),
                                    Integer.parseInt(p[2])
                            );

                            break;


                        // ========================================
                        // ACTIVITY
                        // ========================================

                        case "A":

                            /*
                             * Format:
                             *
                             * A | ID | NAME | TYPE | DURATION |
                             * RESOURCE | EQUIPMENT | DEADLINE |
                             * URGENCY | RESCHEDULE | STATUS
                             */

                            if (p.length != 11) {

                                skipped++;

                                break;
                            }


                            CollegeActivity activity =
                                    new CollegeActivity(

                                            Integer.parseInt(p[1]),

                                            p[2],

                                            p[3],

                                            Integer.parseInt(p[4]),

                                            Integer.parseInt(p[5]),

                                            Integer.parseInt(p[6]),

                                            Integer.parseInt(p[7]),

                                            Integer.parseInt(p[8]),

                                            Boolean.parseBoolean(p[9])
                                    );


                            // Restore activity status
                            activity.setStatus(p[10]);


                            // Put activity back into
                            // LinkedList and HashMap
                            manager.restoreActivity(
                                    activity
                            );


                            activities++;

                            break;


                        // ========================================
                        // PENDING QUEUE
                        // ========================================

                        case "P":

                            if (p.length != 2) {

                                skipped++;

                                break;
                            }


                            int activityId =
                                    Integer.parseInt(p[1]);


                            /*
                             * Search the HashMap so that the
                             * queue uses the same activity
                             * object already stored in the
                             * LinkedList and HashMap.
                             */

                            CollegeActivity pendingActivity =
                                    manager.searchActivity(
                                            activityId
                                    );


                            if (pendingActivity == null) {

                                skipped++;

                                break;
                            }


                            manager.restorePending(
                                    pendingActivity
                            );


                            pending++;

                            break;


                        // ========================================
                        // DECISION HISTORY
                        // ========================================

                        case "D":

                            /*
                             * Format:
                             *
                             * D | ACTIVITY_ID | ACTION |
                             * IMPACT_SCORE | REASON
                             */

                            if (p.length != 5) {

                                skipped++;

                                break;
                            }


                            Decision decision =
                                    new Decision(

                                            Integer.parseInt(p[1]),

                                            p[2],

                                            Double.parseDouble(p[3]),

                                            p[4]
                                    );


                            manager.restoreDecision(
                                    decision
                            );


                            decisions++;

                            break;


                        // ========================================
                        // UNKNOWN RECORD
                        // ========================================

                        default:

                            skipped++;

                            break;
                    }


                } catch (NumberFormatException e) {

                    skipped++;
                }
            }


        } catch (IOException e) {

            System.out.println(
                    "Warning: could not load data ("
                    + e.getMessage()
                    + ")"
            );

            return;
        }


        // =====================================================
        // DISPLAY LOAD INFORMATION
        // =====================================================

        System.out.println(
                "Loaded "
                + activities
                + " activity(s), "
                + pending
                + " pending, "
                + decisions
                + " decision(s) from previous session."
        );


        // =====================================================
        // BACKUP DAMAGED FILE
        // =====================================================

        if (
                skipped > 0
                || !complete
                || !headerOk
        ) {

            backUp(file);


            if (skipped > 0) {

                System.out.println(
                        "Warning: "
                        + skipped
                        + " damaged line(s) "
                        + "in the data file were skipped."
                );
            }


            if (!complete) {

                System.out.println(
                        "Warning: the data file "
                        + "looks incomplete."
                );
            }


            System.out.println(
                    "A copy of the original file "
                    + "was kept as "
                    + BACKUP_FILE
            );
        }
    }


    // =========================================================
    // CREATE BACKUP
    // =========================================================

    private static void backUp(File file) {

        try {

            Files.copy(
                    file.toPath(),
                    new File(BACKUP_FILE).toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (IOException e) {

            System.out.println(
                    "Warning: could not create backup ("
                    + e.getMessage()
                    + ")"
            );
        }
    }


    // =========================================================
    // CLEAN TEXT
    // =========================================================

    /*
     * Prevents special characters from breaking
     * the file format.
     */

    private static String clean(String text) {

        if (text == null) {
            return "";
        }

        return text
                .replace("|", "/")
                .replace("\r", " ")
                .replace("\n", " ");
    }
}