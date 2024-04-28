package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class WeatherDataCurrentTest {

    static WeatherDataCurrent data;

    @BeforeAll
    static void init() {
        PrecipitationCurrent precip = new PrecipitationCurrent(1f, 0.1f);
        TemperatureCurrent temp = new TemperatureCurrent(1f, 2f, 3f, 4f);
        Wind wind = new Wind(1, 2);
        Coord coord = new Coord(1f, 2f);
        data = new WeatherDataCurrent(3L, 2, "02d", "Clear", "Hervanta", coord, temp, 1000, 24, wind, 2, precip, 123L, 12121L);
        
    }

    @Test
    void testGetPrecipitation() {
        PrecipitationCurrent expectedPrecip = new PrecipitationCurrent(1f, 0.1f);
        assertEquals(expectedPrecip.getRain1h(), data.getPrecipitation().getRain1h());
        assertEquals(expectedPrecip.getSnow1h(), data.getPrecipitation().getSnow1h());
    }

    @Test
    void testGetTemperature() {
        TemperatureCurrent expectedTemp = new TemperatureCurrent(1f, 2f, 3f, 4f);
        assertEquals(expectedTemp.getTemp(), data.getTemperature().getTemp());
        assertEquals(expectedTemp.getTempMin(), data.getTemperature().getTempMin());
        assertEquals(expectedTemp.getTempMax(), data.getTemperature().getTempMax());
        assertEquals(expectedTemp.getFeelsLike(), data.getTemperature().getFeelsLike());
    }
}
