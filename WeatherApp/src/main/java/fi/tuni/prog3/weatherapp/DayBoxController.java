package fi.tuni.prog3.weatherapp;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;


/**
 * Controller for daily forecast data box. Inherits from WeatherBoxController which
 * contains the common elements of all weather data boxes.
 * @author Oskari Heinonen
 */
public class DayBoxController extends WeatherBoxController {

    private static final int LOW_PRESSURE_THRESHOLD = 1011;
    private static final int HIGH_PRESSURE_THRESHOLD = 1015;
    

    @FXML
    private Text highTemp, lowTemp, condition, pressure, precipProb, humidity;
    
    @FXML
    private ImageView barometerIcon;

    /**
     * Empty constructor
     */
    public DayBoxController() {
        super();
    }

    /**
     * Set day's low temperature
     * @param temp teperature
     */
    public void setLowTemp(float temp) {
        this.lowTemp.setText(String.format("%d %s", Math.round(temp),
                             WeatherApp.getUnitT().toString()));
    }

    /**
     * Set day's high temperature
     * @param temp temperature
     */
    public void setHighTemp(float temp) {
        this.highTemp.setText(String.format("%d %s", 
                              Math.round(temp), WeatherApp.getUnitT().toString()));
    }

    /**
     * Sets the decription text of the weather condition
     * @param condition description text
     */
    public void setCondition(String condition) {
        this.condition.setText(condition);
    }

    /**
     * Sets air pressure to be displayed and modifys the icon's
     * indicator to visualize considerably higher or lower pressure compared to
     * the normal pressure.
     * @param pressure pressure in hPa
     */
    public void setPressure(float pressure) {
        this.pressure.setText(String.format("%.0f hPa", pressure));
        if (pressure < LOW_PRESSURE_THRESHOLD) {
            barometerIcon.setImage(new Image(getClass().getResourceAsStream(
                WeatherApp.BAROMETER_HIGH_IM_PATH)));
            barometerIcon.setScaleX(-1);
        }
        else if (pressure > HIGH_PRESSURE_THRESHOLD) {
            barometerIcon.setImage(new Image(getClass().getResourceAsStream(
                WeatherApp.BAROMETER_HIGH_IM_PATH)));
        }
        else {
            barometerIcon.setImage(new Image(getClass().getResourceAsStream(
                WeatherApp.BAROMETER_IM_PATH)));
        }
    }

    /**
     * Sets the precipitation probability to be displyed
     * @param precipProb probability in percentage
     */
    public void setPrecipProb(float precipProb) {
        this.precipProb.setText(String.format("%.0f %%", precipProb));
    }

    /**
     * Sets the humidity to be displayed
     * @param humidity humidity in percentage
     */
    public void setHumidity(int humidity) {
        this.humidity.setText(String.format("%d %%", humidity));
    }

}

