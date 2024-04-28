package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.*;


/**
 * Abstract class for storing weather data from different APIs.
 * Requires Types
 * @author Oskari Heinonen
 */
public abstract class WeatherData {
    
    private long time; // "dt", unix UTC time
    private int id; // weather condition id
    private String iconId;
    private String description;
    private String city;  // "name"
    private Coord coord;
    
    private int pressure, humidity, clouds; // cloud-%
    private Wind wind;
    private long sunrise, sunset;

    /**
     * Constructs a WeatherData object.
     * @param time         Time of the data in unix UTC time
     * @param id           Weather condition id
     * @param iconId       Icon id of the weather condition
     * @param description  Description of the weather condition
     * @param city         Name of the city
     * @param coord        Coordinates of the city
     * @param pressure     Atmospheric pressure on the sea level in hPa
     * @param humidity     Humidity-%
     * @param wind         Wind data
     * @param clouds       Cloudiness-%
     * @param sunrise      Sunrise time in unix UTC time
     * @param sunset       Sunset time in unix UTC time
     * @throws IllegalArgumentException  if a parameter that shouldn't be negative is negative
     */
    public WeatherData(long time, int id, String iconId, String description, String city, Coord coord, 
                       int pressure, int humidity, Wind wind, int clouds, long sunrise, long sunset) 
        throws IllegalArgumentException
    {
        if (time < 0 || id < 0 || pressure < 0 || humidity < 0 || clouds < 0 || sunrise < 0 || sunset < 0) {
            throw new IllegalArgumentException("Negative values not allowed.");
        }
        this.time = time;
        this.id = id;
        this.iconId = iconId;
        this.description = description;
        this.city = city;
        this.coord = coord;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind = wind;
        this.clouds = clouds;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }
    
    /**
     * Get the time of the data in unix UTC time.
     * @return time of the data.
     */
    public long getTime() {
        return time;
    }
    /**
     * Get the weather condition id.
     * @return weather condition id.
     */
    public int getId() {
        return id;
    }
    /**
     * Get the icon id of the weather condition.
     * @return icon id.
     */
    public String getIconId() {
        return iconId;
    }
    /**
     * Get the description of the weather condition.
     * @return description of the weather condition.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Get the name of the city.
     * @return name of the city.
     */
    public String getCity() {
        return city;
    }
    /**
     * Get the coordinates of the city.
     * @return coordinates of the city.
     */
    public Coord getCoord() {
        return coord;
    }
    /**
     * Get the atmospheric pressure on the sea level in hPa.
     * @return atmospheric pressure.
     */
    public int getPressure() {
        return pressure;
    }
    /**
     * Get the humidity-%.
     * @return humidity-%.
     */
    public int getHumidity() {
        return humidity;
    }
    /**
     * Get the wind data.
     * @return wind data.
     */
    public Wind getWind() {
        return wind;
    }
    /**
     * Get the cloudiness-%.
     * @return cloudiness-%.
     */
    public int getClouds() {
        return clouds;
    }
    /**
     * Get the sunrise time in unix UTC time.
     * @return sunrise time.
     */
    public long getSunrise() {
        return sunrise;
    }
    /**
     * Get the sunset time in unix UTC time.
     * @return sunset time.
     */
    public long getSunset() {
        return sunset;
    }
}
