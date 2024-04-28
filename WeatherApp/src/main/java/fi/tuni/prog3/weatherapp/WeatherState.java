package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.*;

import java.util.ArrayList;


/**
 * Class to store weather all weather data. It fetches data from the API
 * through JsonParser. 
 */
public class WeatherState {
    
    private Coord coord;
    private String location;
    private WeatherDataCurrent currentWeather;
    private ArrayList<WeatherDataDay> dailyForecast;
    private ArrayList<WeatherDataHour> hourlyForecast;

    /**
     * Constructor which fetches weather data for given location in a given temp
     * unit and stores it.
     * @param location string to be used for searching a location for the weather
     * @param unitT temperature unit to be used in the weather data
     * @throws CityNotFoundException if the wanted location cannot be found
     */
    public WeatherState(String location, TempUnit unitT) throws CityNotFoundException {
        JsonParser parser = new JsonParser();
        coord = parser.lookUpLocation(location);
        currentWeather = parser.getCurrentWeather(coord.getLat(), coord.getLon(), unitT);
        hourlyForecast = parser.getForecastHourly(coord.getLat(), coord.getLon(), unitT);
        dailyForecast = parser.getForecastDaily(coord.getLat(), coord.getLon(), unitT);
        
        // The given city name is formatted correctly for the UI
        String[] parts = location.toLowerCase().split("\\s+|-");
        StringBuilder formattedCityName = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                String formattedPart = Character.toUpperCase(part.charAt(0)) + part.substring(1);
                if (location.contains("-")) {
                    formattedCityName.append(formattedPart).append("-");
                } else {
                    formattedCityName.append(formattedPart).append(" ");
                }
                
            }
        }
        Character lastChar = formattedCityName.charAt(formattedCityName.length()-1);
        if (lastChar.toString().equals("-")) {
            formattedCityName.deleteCharAt(formattedCityName.length()-1);
        }
        String formattedLoc = formattedCityName.toString().trim();
        this.location = formattedLoc;
    }

    /**
     * Returns the coordinates of the weather state's location
     * @return coordinate object
     */
    public Coord getCoord() {
        return coord;
    }

    /**
     * Returns the name of the weather state's location
     * @return location string
     */
    public String getLocation() {
        return location;
    }

    /**
     * Returns the weather data object for current weather
     * @return current weather data
     */
    public WeatherDataCurrent getCurrentWeather() {
        return currentWeather;
    }

    /**
     * Returns an hourly weather forecast object by index. Indexing starts
     * from the most recent hour's forecast available.
     * @param hourIndex hourly forecast's index
     * @return weather data for the desired hour
     * @throws IndexOutOfBoundsException if the data for given index does not exist
     */
    public WeatherDataHour getWeatherHour(int hourIndex) throws IndexOutOfBoundsException {
        return hourlyForecast.get(hourIndex);
    }

    /**
     * Returns a daily weather forecast object by index. Indexing starts
     * from the current day's forecast.
     * @param dayIndex daily forecast's index
     * @return weather data for the desired day
     * @throws IndexOutOfBoundsException if the data for given index does not exist
     */
    public WeatherDataDay getWeatherDay(int dayIndex) throws IndexOutOfBoundsException{
        return dailyForecast.get(dayIndex);
    }

    /**
     * Returns the number of hourly forecasts available
     * @return number of hourly forecasts
     */
    public int getHourForecastCount() {
        return hourlyForecast.size();
    }

    /**
     * Returns the number of daily forecasts available
     * @return number of daily forecasts
     */
    public int getDailyForecastCount() {
        return dailyForecast.size();
    }

}