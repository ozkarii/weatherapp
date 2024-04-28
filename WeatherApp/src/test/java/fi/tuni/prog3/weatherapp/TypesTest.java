package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TypesTest {

    @Test
    public void testTempUnitToString() {
        assertEquals("°K", TempUnit.KELVIN.toString());
        assertEquals("°C", TempUnit.CELSIUS.toString());
        assertEquals("°F", TempUnit.FAHRENHEIT.toString());
    }

    @Test
    public void testTemperatureCurrent() {
        TemperatureCurrent temperature = new TemperatureCurrent(
                           25.0f, -2.0f, 102.0f, 0);

        assertEquals(25.0f, temperature.getTemp());
        assertEquals(-2.0f, temperature.getTempMin());
        assertEquals(102.0f, temperature.getTempMax());
        assertEquals(0, temperature.getFeelsLike());
    }

    @Test
    public void testTemperatureHour() {
        TemperatureHour temperature = new TemperatureHour(
                           -12.0f, 2.0f, 0, 213);
        assertEquals(-12.0f, temperature.getTemp());
        assertEquals(2.0f, temperature.getTempMin());
        assertEquals(0, temperature.getTempMax());
        assertEquals(213, temperature.getFeelsLike());
    }

    @Test
    public void testTemperatureDay() {
        TemperatureDay tempDay = new TemperatureDay(
                                 10.0f, 20.0f,
                                 -15.0f, 12.0f,
                                 -8.0f, 22.0f,
                                 0f, 21.0f,
                                 -16.0f, 13.0f);

        assertEquals(10.0f, tempDay.getTempMorn());
        assertEquals(20.0f, tempDay.getTempDay());
        assertEquals(-15.0f, tempDay.getTempEve());
        assertEquals(12.0f, tempDay.getTempNight());
        assertEquals(-8.0f, tempDay.getTempMin());
        assertEquals(22.0f, tempDay.getTempMax());
        assertEquals(0, tempDay.getFeelsLikeMorn());
        assertEquals(21.0f, tempDay.getFeelsLikeDay());
        assertEquals(-16.0f, tempDay.getFeelsLikeEve());
        assertEquals(13.0f, tempDay.getFeelsLikeNight());
    }

    @Test
    public void testPrecipitationCurrent() {
        PrecipitationCurrent precipitation = new PrecipitationCurrent(1.0f, 0.5f);

        assertEquals(1.0f, precipitation.getRain1h());
        assertEquals(0.5f, precipitation.getSnow1h());
        
        PrecipitationCurrent precipitation2 = new PrecipitationCurrent(0.0f, 0.0f);
        
        assertEquals(0, precipitation2.getRain1h());
        assertEquals(0, precipitation2.getSnow1h());
    }

    @Test
    public void testPrecipitationCurrentThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PrecipitationCurrent(-1.0f, 0.5f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new PrecipitationCurrent(1.0f, -0.5f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new PrecipitationCurrent(-2.0f, -3.5f);
        });
    }

    @Test
    public void testPrecipitationHour() {
        // Test normal case
        PrecipitationHour precipitation = new PrecipitationHour(1.0f, 0.5f, 0.2f);
        assertEquals(1.0f, precipitation.getRain1h());
        assertEquals(0.5f, precipitation.getSnow1h());
        assertEquals(20.0f, precipitation.getProbability());

        // Test case with 0
        PrecipitationHour precipitation2 = new PrecipitationHour(0.0f, 0.0f, 0.0f);
        assertEquals(0.0f, precipitation2.getRain1h());
        assertEquals(0.0f, precipitation2.getSnow1h());
        assertEquals(0.0f, precipitation2.getProbability());
    }

    @Test
    public void testPrecipitationHourThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PrecipitationHour(-1.0f, 0.5f, 0.2f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new PrecipitationHour(1.0f, -0.5f, 0.2f);
        }); 
        assertThrows(IllegalArgumentException.class, () -> {
            new PrecipitationHour(1.0f, 0.5f, -0.2f);
        });
    }

    @Test
    public void testPrecipitationDay() {
        // Test normal case
        PrecipitationDay precipitation = new PrecipitationDay(1.0f, 0.5f, 0.2f);
        assertEquals(1.0f, precipitation.getRain());
        assertEquals(0.5f, precipitation.getSnow());
        assertEquals(20.0f, precipitation.getProbability());

        // Test case with 0
        PrecipitationDay precipitation2 = new PrecipitationDay(0.0f, 0.0f, 0.0f);
        assertEquals(0.0f, precipitation2.getRain());
        assertEquals(0.0f, precipitation2.getSnow());
        assertEquals(0.0f, precipitation2.getProbability());
    }

    @Test
    public void testPrecipitationDayThrows() {
        assertThrows(IllegalArgumentException.class, () -> {
            new PrecipitationDay(-1.0f, 0.5f, 0.2f);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new PrecipitationDay(1.0f, -0.5f, 0.2f);
        }); 
        assertThrows(IllegalArgumentException.class, () -> {
            new PrecipitationDay(1.0f, 0.5f, -0.2f);
        });
    }

    @Test
    public void testCoordGet() {
        Coord coord = new Coord(1.0f, 2.0f);
        assertEquals(1.0f, coord.getLon());
        assertEquals(2.0f, coord.getLat());
    }

    @Test
    public void testCoordEquals() {
        Coord coord1 = new Coord(1.0f, 2.0f);
        Coord coord2 = new Coord(1.0f, 2.0f);
        Coord coord3 = new Coord(2.0f, 1.0f);
        
        assertTrue(coord1.equals(coord2));
        assertFalse(coord1.equals(coord3));

        // test with negative coordinates
        Coord coord4 = new Coord(-1.0f, -2.0f);
        Coord coord5 = new Coord(-1.0f, -2.0f);
        Coord coord6 = new Coord(10.0f, 61.0f);

        assertTrue(coord4.equals(coord5));
        assertFalse(coord4.equals(coord6));

    }

    @Test
    public void testToString() {
        Coord coord = new Coord(1.0f, -2.0f);
        assertEquals("-2.0 1.0", coord.toString());
    }

    @Test
    public void testWindGet() {
        Wind wind = new Wind(1.0f, 180);
        assertEquals(1.0f, wind.getSpeed());
        assertEquals(180, wind.getDeg());
    }

    @Test
    public void testWindThorws() {
        // Test exception for negative speed
        assertThrows(IllegalArgumentException.class, () -> {
            new Wind(-1.0f, 180);
        });

        // Test exception for negative degrees
        assertThrows(IllegalArgumentException.class, () -> {
            new Wind(1.0f, -180);
        });
    }
}

