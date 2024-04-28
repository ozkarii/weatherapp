package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.TempUnit;
import fi.tuni.prog3.weatherapp.Types.CityNotFoundException;

import javafx.application.Application;
import java.util.ArrayList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

/**
 * JavaFX Weather Application.
 * This class should have only one instance since it has many static fields and methods.
 */
public class WeatherApp extends Application {

    /** API key for the OpenWeatherMap API
     * Pass the key as a system property when running the program
     */
    public static final String API_KEY = System.getProperty("apiKey");
    
    /** Image format for weather images */
    public static final String WEATHER_IMAGE_FORMAT = ".png";
    
    /** Path for weather icons */
    public static final String WEATHER_ICONS_PATH = "/fi/tuni/prog3/weatherapp/weather_icons/";
    
    /** Path for normal barometer icon */
    public static final String BAROMETER_IM_PATH = "/fi/tuni/prog3/weatherapp/ui_icons/barometer.png";
    
    /** Path for barometer icon which indicates high */
    public static final String BAROMETER_HIGH_IM_PATH = "/fi/tuni/prog3/weatherapp/ui_icons/barometer_high.png";
    
    /** Path for search icon */
    public static final String SEARCH_ICON_FILE = "/fi/tuni/prog3/weatherapp/ui_icons/search.png";
    
    /** Path for favorites icon */
    public static final String FAVORITES_IM_FILE = "/fi/tuni/prog3/weatherapp/ui_icons/favorites.png";
    
    /** Path for empty favorites icon */
    public static final String NOT_FAVORITES_IM_FILE = "/fi/tuni/prog3/weatherapp/ui_icons/favorites_empty.png";
    
    /** Path for settings icon */
    public static final String STARTUP_FILE = "startupData.json";
    
    /** FXML file for the hourly weather box ui */
    public static final String HOUR_BOX_FILE = "HourBox.fxml";
    
    /** FXML file for the daily weather box ui */
    public static final String DAY_BOX_FILE = "DayBox.fxml";
    
    /** FXML file for the main app ui */
    public static final String APP_FXML_FILE = "WeatherApp.fxml";
    
    /** HTML file for the map */
    public static final String MAP_HTML_FILE = "map.html";
    

    private static Scene scene;
    private static WeatherState state;
    private static UIController uiController;
    private static ArrayList<String> favorites;
    private static TempUnit unitT = TempUnit.CELSIUS;
    private static ArrayList<String> searchHistory;
    
    /**
     * Initializes the attributes and launches the UI
     * @param stage the primary stage for the application
     * @throws Exception if the default location does not exist
     */
    @Override
    public void start(Stage stage) throws Exception {

        StartupState startupState = new StartupState();
        StartupData startupData = startupState.readFromFile();
        favorites = startupData.favorites;
        
        String location = "Tampere";
        if (startupData.settings.city != null) {
            location = startupData.settings.city;
        }
        String unitSystem = "Metric";
        if (startupData.settings.unitSystem != null) {
            unitSystem = startupData.settings.unitSystem;
        }
        
        unitT = unitT.toTempUnit(unitSystem);
        searchHistory = startupData.searchHistory;
        try {
            state = new WeatherState(location, unitT);
        } catch (CityNotFoundException e) {
            location = "Tampere";
            state = new WeatherState(location, unitT);
        }
        
        FXMLLoader appLoader = new FXMLLoader(getClass().getResource(APP_FXML_FILE));
        Parent root = appLoader.load();
        
        uiController = appLoader.getController();

        scene = new Scene(root, 1200, 764);
        stage.setScene(scene);
        stage.setTitle("Weather App");
        stage.setResizable(false);
                
        stage.setOnCloseRequest(e -> {
            try {
                StartupData std = new StartupData(new Settings(
                    state.getLocation(), unitT.getUnitSystem()), favorites, searchHistory);
                startupState.writeToFile(std);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        });
    
        stage.show();
    }

    /**
     * Returns the WeatherState object
     * @return WeatherState for the selected location
     */
    public static WeatherState getState() {
        return state;
    }

    /**
     * Returns the UIController object associated with the WeatherApp
     * @return UIController for the WeatherApp
     */
    public static UIController getUIController() {
        return uiController;
    }

    /**
     * Returns the selected temperature unit
     * @return Returns TempUnit for the selected unit system
     */
    public static TempUnit getUnitT() {
        return unitT;
    }

    /**
     * Sets new value for the TempUnit unitT
     * @param unitT the new temperature unit
     */
    public static void setUnitT(TempUnit unitT) {
        
        WeatherApp.unitT = unitT;
    }

    /**
     * Returns the list of the favorite locations
     * @return ArrayList of the favorite locations
     */
    public static ArrayList<String> getFavorites(){
        return favorites;
    }

    /**
     * Sets a new list to the favorites attribute
     * @param favorites ArrayList which contains the new favorite locations
     */
    public static void setFavorites(ArrayList<String> favorites) {
        WeatherApp.favorites = favorites;
    }
    
    /**
     * Creates a new WeatherState for the given location and the temperature 
     * unit of the attribute unitT
     * @param location the new location
     * @throws CityNotFoundException if the given location does not exist
     */
    public static void newWeatherState(String location) throws CityNotFoundException {

        state = new WeatherState(location, unitT);
    }
    
    /**
     * Returns the current weather data of the selected location
     * @return WeatherDataCurrent object for the current weather
     */
    public static WeatherDataCurrent getCurrentWeather() {
        return state.getCurrentWeather();
    }

    /**
     * Returns the searchHistory of the locations
     * @return search history of the locations as an ArrayList
     */
    public static ArrayList<String> getSearchHistory() {
        return searchHistory;
    }

    /**
     * Sets a new list for the searchHistory attribute
     * @param searchHistory the new search history of the locations as an ArrayList
     */
    public static void setSearchHistory(ArrayList<String> searchHistory) {
        WeatherApp.searchHistory = searchHistory;
    }
    
    /**
     * Launches the WeatherApp program by calling the start method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}