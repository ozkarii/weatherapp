package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class WeatherDataDayTest {

    static WeatherDataDay data;

    @BeforeAll
    static void init() {
        PrecipitationDay precip = new PrecipitationDay(1f, 0.1f, 0.5f);
        TemperatureDay temp = new TemperatureDay(1f, 2f, 3f, 4f,
                                                 5f, 6f, 7f, 8f, 9f, 10f);
        Wind wind = new Wind(1, 2);
        Coord coord = new Coord(1f, 2f);
        data = new WeatherDataDay(3L, 2, "02d", "Clear", "Hervanta", 
                                  coord, temp, 1000, 24, wind, 2, precip, 123L, 12121L);
        
    }

    @Test
    void testGetPrecipitation() {
        PrecipitationDay expectedPrecip = new PrecipitationDay(1f, 0.1f, 0.5f);
        assertEquals(expectedPrecip.getRain(), data.getPrecipitation().getRain());
        assertEquals(expectedPrecip.getSnow(), data.getPrecipitation().getSnow());
    }

    @Test
    void testGetTemperature() {
        TemperatureDay expectedTemp = new TemperatureDay(1f, 2f, 3f, 4f, 5f,
                                                         6f, 7f, 8f, 9f, 10f);
        assertEquals(expectedTemp.getTempDay(), data.getTemperature().getTempDay());
        assertEquals(expectedTemp.getTempMin(), data.getTemperature().getTempMin());
        assertEquals(expectedTemp.getTempMax(), data.getTemperature().getTempMax());
        assertEquals(expectedTemp.getTempNight(), data.getTemperature().getTempNight());
        assertEquals(expectedTemp.getTempEve(), data.getTemperature().getTempEve());
        assertEquals(expectedTemp.getTempMorn(), data.getTemperature().getTempMorn());
        assertEquals(expectedTemp.getFeelsLikeDay(), data.getTemperature().getFeelsLikeDay());
        assertEquals(expectedTemp.getFeelsLikeNight(), data.getTemperature().getFeelsLikeNight());
        assertEquals(expectedTemp.getFeelsLikeEve(), data.getTemperature().getFeelsLikeEve());
        assertEquals(expectedTemp.getFeelsLikeMorn(), data.getTemperature().getFeelsLikeMorn());
    }
}
