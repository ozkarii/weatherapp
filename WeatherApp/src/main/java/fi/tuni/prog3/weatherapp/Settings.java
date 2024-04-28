package fi.tuni.prog3.weatherapp;

import java.util.Objects;

/**
 * Class for storing the settings for the application.
 */
public class Settings {

    /**
     * Calculates a hash value for the settings
     * @return hash value for settings
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.city);
        hash = 11 * hash + Objects.hashCode(this.unitSystem);
        return hash;
    }

    /**
     * Overridden equals to compare class using values
     * @param obj object to compare
     * @return true if the objects are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Settings other = (Settings) obj;
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        return Objects.equals(this.unitSystem, other.unitSystem);
    }

    /**
     * Name for place in settings
     * Public to be usable with GSON
     */
    public String city;

    /**
     * UnitSystem string
     * Public to be usable with GSON
     */
    public String unitSystem;

    /**
     * Constructor. Saves city and unit system to object
     * @param city city to be saved
     * @param unitSystem unit system to be saved
     */
    public Settings(String city, String unitSystem) {
        this.city = city;
        this.unitSystem = unitSystem;
    }
}
