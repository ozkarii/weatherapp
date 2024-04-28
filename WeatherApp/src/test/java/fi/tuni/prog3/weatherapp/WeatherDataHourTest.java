package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class WeatherDataHourTest {

    static WeatherDataHour data;

    @BeforeAll
    static void init() {
        PrecipitationHour precip = new PrecipitationHour(1f, 0.1f, 0);
        TemperatureHour temp = new TemperatureHour(1f, 2f, 3f, 4f);
        Wind wind = new Wind(1, 2);
        Coord coord = new Coord(1f, 2f);
        data = new WeatherDataHour(3L, 2, "02d", "Clear", "Hervanta", coord, temp, 1000, 24, wind, 2, precip, 123L, 12121L);
        
    }

    @Test
    void testGetPrecipitation() {
        PrecipitationHour expectedPrecip = new PrecipitationHour(1f, 0.1f, 0);
        assertEquals(expectedPrecip.getRain1h(), data.getPrecipitation().getRain1h());
        assertEquals(expectedPrecip.getSnow1h(), data.getPrecipitation().getSnow1h());
    }

    @Test
    void testGetTemperature() {
        TemperatureHour expectedTemp = new TemperatureHour(1f, 2f, 3f, 4f);
        assertEquals(expectedTemp.getTemp(), data.getTemperature().getTemp());
        assertEquals(expectedTemp.getTempMin(), data.getTemperature().getTempMin());
        assertEquals(expectedTemp.getTempMax(), data.getTemperature().getTempMax());
        assertEquals(expectedTemp.getFeelsLike(), data.getTemperature().getFeelsLike());
    }
}
