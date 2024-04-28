/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class for storing the startup data for the application.
 * Contains favorites, settings and search history.
 */
public class StartupData {

    /**
     * Calculates a hash value for the startup configuration
     * @return hash value for startup configuration
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.favorites);
        hash = 43 * hash + Objects.hashCode(this.settings);
        hash = 43 * hash + Objects.hashCode(this.searchHistory);
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
        final StartupData other = (StartupData) obj;
        if (!Objects.equals(this.favorites, other.favorites)) {
            return false;
        }
        if (!Objects.equals(this.settings, other.settings)) {
            return false;
        }
        return Objects.equals(this.searchHistory, other.searchHistory);
    }
    
    /**
     * Favorites array.
     * Public to be usable with GSON
     */
    public ArrayList<String> favorites;

    /**
     * Settings object
     * Public to be usable with GSON
     */
    public Settings settings;

    /**
     * Search history array
     * Public to be usable with GSON
     */
    public ArrayList<String> searchHistory;
    
    /**
     * Constructor. Saves data to object.
     * @param settings settings object
     * @param favorites favorites array
     * @param searchHistory search history array
     */
    public StartupData(Settings settings, ArrayList<String> favorites, ArrayList<String> searchHistory) {
        this.settings = settings;
        this.favorites = favorites;
        this.searchHistory = searchHistory;
    }
    
}
