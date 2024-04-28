package fi.tuni.prog3.weatherapp;

import javafx.fxml.FXML;
import javafx.scene.text.Text;


/**
 * Controller for hourly forecast data box. 
 * Inherits from WeatherBoxController which contains the common elements
 * for all weather data boxes.
 * @author Oskari Heinonen
 */
public class HourBoxController extends WeatherBoxController {

    @FXML
    private Text time;
    
    /**
     * Empty constructor
     */
    public HourBoxController() {
        super();
    }

    /**
     * Sets the time string to be displayed
     * @param timeString time
     */
    public void setTime(String timeString) {
        this.time.setText(timeString);
    }

}
