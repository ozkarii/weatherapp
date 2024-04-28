package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.*;

/**
 * Class for storing WeatherData for a single HOUR. Inherits WeatherData.
 * @author Oskari Heinonen
 */
public class WeatherDataHour extends WeatherData {

    private TemperatureHour temp;
    private PrecipitationHour precipitation;
    
    /**
     * Constructs a WeatherDataHour object.
     * @param time           Time of the data in unix UTC time
     * @param id             Weather condition id
     * @param iconId         Icon id of the weather condition
     * @param description    Description of the weather condition
     * @param city           Name of the city
     * @param coord          Coordinates of the city
     * @param temp           Temperature data
     * @param pressure       Atmospheric pressure on the sea level in hPa
     * @param humidity       Humidity-%
     * @param wind           Wind data
     * @param clouds         Cloudiness-%
     * @param precipitation  Precipitation data
     * @param sunrise        Sunrise time in unix UTC time
     * @param sunset         Sunset time in unix UTC time
     * @throws IllegalArgumentException  if a parameter that shouldn't be negative is negative
     */
    public WeatherDataHour(long time, int id, String iconId, String description,
                           String city, Coord coord, TemperatureHour temp, int pressure,
                           int humidity, Wind wind, int clouds, 
                           PrecipitationHour precipitation, long sunrise,
                           long sunset) throws IllegalArgumentException
    {
        super(time, id, iconId, description, city, coord, pressure, humidity, wind, clouds, sunrise, sunset);
        this.temp = temp;
        this.precipitation = precipitation;
    }

    /**
     * Get the temperature data.
     * @return temperature data
     */
    public TemperatureHour getTemperature() {
        return temp;
    }
    /**
     * Get the precipitation data.
     * @return precipitation data
     */
    public PrecipitationHour getPrecipitation() {
        return precipitation;
    }
}
