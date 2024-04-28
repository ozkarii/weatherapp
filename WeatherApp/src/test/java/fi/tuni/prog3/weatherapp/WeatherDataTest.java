package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for abstract class WeatherData.
 * Instance of WeatherDataCurrent is created for testing.
 */
public class WeatherDataTest {

    static WeatherDataCurrent data;

    @BeforeAll
    static void init() {
        PrecipitationCurrent precip = new PrecipitationCurrent(1f, 0.1f); //PrecipitationCurrent(1, 2, 2, 2, 0.1f)
        TemperatureCurrent temp = new TemperatureCurrent(1f, 2f, 3f, 4f);
        Wind wind = new Wind(1, 2);
        Coord coord = new Coord(1f, 2f);
        data = new WeatherDataCurrent(3L, 2, "02d", "Clear", "Hervanta", coord, temp, 1000, 24, wind, 2, precip, 123L, 12121L);
    }
    
    @Test
    void testGetTime() {
        assertEquals(3, data.getTime());
    }

    @Test
    void testGetId() {
        assertEquals(2, data.getId());
    }

    @Test
    void testGetIconId() {
        assertEquals("02d", data.getIconId());
    }

    @Test
    void testGetDescription() {
        assertEquals("Clear", data.getDescription());
    }

    @Test
    void testGetCity() {
        assertEquals("Hervanta", data.getCity());
    }

    @Test
    void testGetCoord() {
        Coord expectedCoord = new Coord(1f, 2f);
        assertEquals(expectedCoord.getLon(), data.getCoord().getLon());
        assertEquals(expectedCoord.getLat(), data.getCoord().getLat());
        assertTrue(expectedCoord.equals(data.getCoord()));
    }

    @Test
    void testGetPressure() {
        assertEquals(1000, data.getPressure());
    }

    @Test
    void testGetHumidity() {
        assertEquals(24, data.getHumidity());
    }

    @Test
    void testGetWind() {
        Wind expectedWind = new Wind(1, 2);
        assertEquals(expectedWind.getSpeed(), data.getWind().getSpeed());
        assertEquals(expectedWind.getDeg(), data.getWind().getDeg());
    }

    @Test
    void testGetClouds() {
        assertEquals(2, data.getClouds());
    }

    @Test
    void testGetSunrise() {
        assertEquals(123L, data.getSunrise());
    }
    
    @Test
    void testGetSunset() {
        assertEquals(12121L, data.getSunset());
    }
        
}
