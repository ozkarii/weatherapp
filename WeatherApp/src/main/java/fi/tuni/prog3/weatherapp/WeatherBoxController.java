package fi.tuni.prog3.weatherapp;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.effect.ColorAdjust;

/**
 * Parent class for weather data box controllers
 * Contains the common elements for all weather data boxes
 * @author Oskari Heinonen
 */
public abstract class WeatherBoxController {
    
    @FXML
    private Text date, temp, feelsLike, windSpd, precipAmount;

    @FXML
    private ImageView weatherIcon, feelsLikeIcon, windDirIcon;

    /**
     * Empty constructor
     */
    public WeatherBoxController() {
    }

    /**
     * Set date string to be displayed
     * @param dateString date
     */
    public void setDate(String dateString) {
        this.date.setText(dateString);
    }

    /**
     * Set temperature
     * @param temp temperature
     */
    public void setTemp(float temp) {
        this.temp.setText(String.format("%d %s",
                          Math.round(temp), WeatherApp.getUnitT().toString()));
    }

    /**
     * Set feels like temperature and color for the icon
     * @param feelsLike feels like temperature
     * @param refTemp reference temperature for comparison
     */
    public void setFeelsLike(float feelsLike, float refTemp) {
        
        this.feelsLike.setText(String.format("%d %s", Math.round(feelsLike),
                               WeatherApp.getUnitT().toString()));
        // The icon is red by default in FXML, set it to blue if feels like is lower 
        // than the indicated temperature
        if (feelsLike < refTemp) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setHue(-0.8);
            feelsLikeIcon.setEffect(colorAdjust);
            this.feelsLike.setFill(javafx.scene.paint.Color.BLUE);
        }
        // set to gray if equal
        else if (feelsLike == refTemp) {
            this.feelsLikeIcon.setEffect(null);
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(-1);
            this.feelsLikeIcon.setEffect(colorAdjust);
            this.feelsLike.setFill(javafx.scene.paint.Color.web("#3b3b3b"));
        }
    }

    /**
     * Set wind speed to be displayed
     * @param windSpd wind speed
     */
    public void setWindSpd(float windSpd) {
        this.windSpd.setText(String.format("%.0f", windSpd));
    }

    /**
     * Sets weather icon according to the icon id
     * @param iconId id
     */
    public void setWeatherIcon(String iconId) {
        Image image = new Image(getClass().getResourceAsStream(
            WeatherApp.WEATHER_ICONS_PATH + iconId + WeatherApp.WEATHER_IMAGE_FORMAT));
        weatherIcon.setImage(image);
    }

    /**
     * Sets wind direction to rotate the icon accordingly
     * @param windDir direction in degrees
     */
    public void setWindDir(int windDir) {
        windDirIcon.setRotate(windDir);
    }

    /**
     * Sets the precipitation amount to be displayed
     * @param precipAmount precipitation amount in mm
     */
    public void setPrecipAmount(float precipAmount) {
        this.precipAmount.setText(String.format("%.1f mm", precipAmount));
    }
}
