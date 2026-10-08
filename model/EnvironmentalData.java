package model;

public class EnvironmentalData {

    private double carbonIntensity;
    private double temperature;
    private double renewableAvailability;
    private double waterStress;

    public EnvironmentalData(double carbonIntensity,
                             double temperature,
                             double renewableAvailability,
                             double waterStress) {

        this.carbonIntensity = carbonIntensity;
        this.temperature = temperature;
        this.renewableAvailability = renewableAvailability;
        this.waterStress = waterStress;
    }

    public double getCarbonIntensity() {
        return carbonIntensity;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getRenewableAvailability() {
        return renewableAvailability;
    }

    public double getWaterStress() {
        return waterStress;
    }

    @Override
    public String toString() {
        return "Carbon Intensity: " + carbonIntensity +
               " | Temperature: " + temperature +
               "°C | Renewable Availability: " +
               renewableAvailability + "%" +
               " | Water Stress: " + waterStress;
    }
}
