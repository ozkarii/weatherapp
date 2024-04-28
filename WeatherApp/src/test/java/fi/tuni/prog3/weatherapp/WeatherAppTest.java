package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.*;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WeatherAppTest {
    
    public WeatherAppTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getState method, of class WeatherApp.
     */
    @Test
    public void testGetState() throws CityNotFoundException {
        System.out.println("getState");
        String location = "Tampere"; // KORJAA
        WeatherApp.newWeatherState(location);
        TempUnit unitT = WeatherApp.getUnitT();
        WeatherState instance = new WeatherState(location, unitT);
        String expResult = instance.getLocation();
        WeatherState state = WeatherApp.getState();
        String result = state.getLocation();
        assertEquals(expResult, result);
    }

    /**
     * Test of getUnitT method, of class WeatherApp.
     */
    @Test
    public void testGetUnitT() {
        System.out.println("getUnitT");
        Types.TempUnit expResult = TempUnit.CELSIUS;
        WeatherApp.setUnitT(expResult);
        Types.TempUnit result = WeatherApp.getUnitT();
        assertEquals(expResult, result);
    }

    /**
     * Test of setUnitT method, of class WeatherApp.
     */
    @Test
    public void testSetUnitT() {
        System.out.println("setUnitT");
        Types.TempUnit unitT = TempUnit.FAHRENHEIT;
        WeatherApp.setUnitT(unitT);
        Types.TempUnit result = WeatherApp.getUnitT();
        assertEquals(unitT, result);
    }

    /**
     * Test of getFavorites method, of class WeatherApp.
     */
    @Test
    public void testGetFavorites() {
        System.out.println("getFavorites");
        ArrayList<String> favorites = new ArrayList<String>();
        favorites.add("Tampere");
        favorites.add("Helsinki");
        WeatherApp.setFavorites(favorites);
        ArrayList<String> result = WeatherApp.getFavorites();
        assertEquals(favorites, result);
    }

    /**
     * Test of setFavorites method, of class WeatherApp.
     */
    @Test
    public void testSetFavorites() {
        System.out.println("setFavorites");
        ArrayList<String> favorites = new ArrayList<String>();
        favorites.add("Tampere");
        favorites.add("Helsinki");
        WeatherApp.setFavorites(favorites);
        ArrayList<String> result = WeatherApp.getFavorites();
        assertEquals(favorites, result);
    }

    /**
     * Test of newWeatherState method, of class WeatherApp.
     */
    @Test
    public void testNewWeatherState() throws Exception {
        System.out.println("newWeatherState");
        String location = "Tampere";
        WeatherState instance = new WeatherState(location, TempUnit.CELSIUS);
        String expResult = instance.getLocation();
        WeatherApp.newWeatherState(location);
        WeatherState state = WeatherApp.getState();
        String result = state.getLocation();
        assertEquals(expResult, result);
    }

    /**
     * Test of getCurrentWeather method, of class WeatherApp.
     */
    @Test
    public void testGetCurrentWeather() {
        System.out.println("getCurrentWeather");
        WeatherState state = WeatherApp.getState();
        WeatherDataCurrent expResult = state.getCurrentWeather();
        WeatherDataCurrent result = WeatherApp.getCurrentWeather();
        assertEquals(expResult, result);
    }

    /**
     * Test of getSearchHistory method, of class WeatherApp.
     */
    @Test
    public void testGetSearchHistory() {
        System.out.println("getSearchHistory");
        ArrayList<String> searchHistory = new ArrayList<String>();
        searchHistory.add("Tampere");
        searchHistory.add("Helsinki");
        searchHistory.add("Turku");
        WeatherApp.setSearchHistory(searchHistory);
        ArrayList<String> result = WeatherApp.getSearchHistory();
        assertEquals(searchHistory, result);
    }

    /**
     * Test of setSearchHistory method, of class WeatherApp.
     */
    @Test
    public void testSetSearchHistory() {
        System.out.println("setSearchHistory");
        ArrayList<String> searchHistory = new ArrayList<String>();
        searchHistory.add("Tampere");
        searchHistory.add("Helsinki");
        searchHistory.add("Turku");
        WeatherApp.setSearchHistory(searchHistory);
        ArrayList<String> result = WeatherApp.getSearchHistory();
        assertEquals(searchHistory, result);
    }
    
}
