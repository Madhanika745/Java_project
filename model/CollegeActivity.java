package model;

public class CollegeActivity {

    private int activityId;
    private String activityName;
    private String activityType;

    private int duration;
    private int resourceRequirement;
    private int equipmentRequirement;

    private int deadline;
    private int urgency;

    private boolean rescheduleAllowed;
    private String status;

    public CollegeActivity(int activityId, String activityName,
                           String activityType, int duration,
                           int resourceRequirement,
                           int equipmentRequirement,
                           int deadline, int urgency,
                           boolean rescheduleAllowed) {

        this.activityId = activityId;
        this.activityName = activityName;
        this.activityType = activityType;
        this.duration = duration;
        this.resourceRequirement = resourceRequirement;
        this.equipmentRequirement = equipmentRequirement;
        this.deadline = deadline;
        this.urgency = urgency;
        this.rescheduleAllowed = rescheduleAllowed;
        this.status = "PENDING";
    }

    public int getActivityId() {
        return activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getActivityType() {
        return activityType;
    }

    public int getDuration() {
        return duration;
    }

    public int getResourceRequirement() {
        return resourceRequirement;
    }

    public int getEquipmentRequirement() {
        return equipmentRequirement;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getUrgency() {
        return urgency;
    }

    public boolean isRescheduleAllowed() {
        return rescheduleAllowed;
    }

    public String getStatus() {
        return status;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setResourceRequirement(int resourceRequirement) {
        this.resourceRequirement = resourceRequirement;
    }

    public void setEquipmentRequirement(int equipmentRequirement) {
        this.equipmentRequirement = equipmentRequirement;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public void setUrgency(int urgency) {
        this.urgency = urgency;
    }

    public void setRescheduleAllowed(boolean rescheduleAllowed) {
        this.rescheduleAllowed = rescheduleAllowed;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Activity ID: " + activityId +
               " | Name: " + activityName +
               " | Type: " + activityType +
               " | Duration: " + duration + " hrs" +
               " | Resources: " + resourceRequirement +
               " | Equipment: " + equipmentRequirement +
               " | Deadline: " + deadline + " hrs" +
               " | Urgency: " + urgency +
               " | Reschedule: " + rescheduleAllowed +
               " | Status: " + status;
    }
}