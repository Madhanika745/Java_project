package engine;

import model.CollegeActivity;
import model.EnvironmentalData;

public class ImpactCalculator {

    public double calculateImpact(CollegeActivity activity,
                                  EnvironmentalData environment) {

        double resourceImpact =
                activity.getResourceRequirement() * 0.5;

        double equipmentImpact =
                activity.getEquipmentRequirement() * 0.7;

        double durationImpact =
                activity.getDuration() * 2;

        double carbonImpact =
                environment.getCarbonIntensity() * 0.4;

        double temperatureImpact =
                environment.getTemperature() * 0.8;

        double waterImpact =
                environment.getWaterStress() * 30;

        double renewableBenefit =
                environment.getRenewableAvailability() * 0.3;

        double score =
                resourceImpact +
                equipmentImpact +
                durationImpact +
                carbonImpact +
                temperatureImpact +
                waterImpact -
                renewableBenefit;

        return Math.max(0, score);
    }
}
