package engine;

import model.CollegeActivity;
import model.Decision;
import model.EnvironmentalData;

public class DecisionEngine {

    private ImpactCalculator calculator;

    public DecisionEngine() {
        calculator = new ImpactCalculator();
    }

    public Decision makeDecision(CollegeActivity activity,
                                 EnvironmentalData environment) {

        double score =
                calculator.calculateImpact(activity, environment);

        String action;
        String reason;

        if (activity.getUrgency() == 3) {

            action = "ALLOW";
            reason = "Activity is highly urgent.";

        } else if (score < 50) {

            action = "ALLOW";
            reason = "Environmental impact is low.";

        } else if (score < 100 &&
                   activity.getDeadline() > activity.getDuration() + 3 &&
                   activity.isRescheduleAllowed()) {

            action = "DELAY";
            reason = "Environmental conditions are unfavorable and activity can be rescheduled.";

        } else if (score >= 100 &&
                   activity.isRescheduleAllowed()) {

            action = "REDIRECT";
            reason = "High environmental impact; consider another location or available facility.";

        } else {

            action = "REDUCE";
            reason = "Reduce resource or equipment usage.";
        }

        return new Decision(
                activity.getActivityId(),
                action,
                score,
                reason
        );
    }
}   