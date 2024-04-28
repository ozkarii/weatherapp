package fi.tuni.prog3.weatherapp;
import fi.tuni.prog3.weatherapp.Types.*;
import java.util.ArrayList;

/**
 * Interface for extracting data from the OpenWeatherMap API.
 */
public interface iAPI {

    /**
     * Returns coordinates for a location.
     * @param loc Name of the location for which coordinates should be fetched.
     * @return Coord.
     * @throws fi.tuni.prog3.weatherapp.Types.CityNotFoundException if the city is not found.
     */
    public Coord lookUpLocation(String loc) throws CityNotFoundException;

    /**
     * Returns the current weather for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @param unitT desired unit of temperature
     * @return current weather data
     */
    public WeatherDataCurrent getCurrentWeather(double lat, double lon, TempUnit unitT);

    /**
     * Returns a hourly forecast for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @param unitT desired unit of temperature
     * @return hourly forecast for the given coordinates in the given TempUnit
     */
    public ArrayList<WeatherDataHour> getForecastHourly(double lat, double lon, TempUnit unitT);
    
     /**
     * Returns a daily forecast for the given coordinates.
     * @param lat The latitude of the location.
     * @param lon The longitude of the location.
     * @param unitT desired unit of temperature
     * @return daily forecast for the given coordinates in the given TempUnit
     */
    public ArrayList<WeatherDataDay> getForecastDaily(double lat, double lon, TempUnit unitT);
}
