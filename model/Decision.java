package model;

public class Decision {

    private int activityId;
    private String action;
    private double impactScore;
    private String reason;

    public Decision(int activityId, String action,
                    double impactScore, String reason) {
        this.activityId = activityId;
        this.action = action;
        this.impactScore = impactScore;
        this.reason = reason;
    }

    public int getActivityId() {
        return activityId;
    }

    public String getAction() {
        return action;
    }

    public double getImpactScore() {
        return impactScore;
    }

    public String getReason() {
        return reason;
    }

    @Override
    public String toString() {
        return "Activity ID: " + activityId +
               " | Decision: " + action +
               " | Impact Score: " + String.format("%.2f", impactScore) +
               " | Reason: " + reason;
    }
}