package fi.tuni.prog3.weatherapp;


/**
 * Class to define types for parameters of weather data. 
 * Some types are for parameters obtained from different APIs,
 * hence the Day, Hour and Current suffixes.
 */
public abstract class Types {

    /**
     * Enum for handling temperature units
     */
    public enum TempUnit {
        /** Kelvin */
        KELVIN,
        /** Celcius */
        CELSIUS,
        /** Fahrenheit */
        FAHRENHEIT;

        @Override
        public String toString() {
            switch (this) {
                case KELVIN:
                    return "°K";

                case CELSIUS:
                    return "°C";
                
                case FAHRENHEIT:
                    return "°F";

                default:
                    throw new IllegalArgumentException();
            }
        }
        
        /**
         * Converts strings "Metric" and "Imperial" to corresponding TempUnit.
         * If the string is not recognized, returns TempUnit.KELVIN.
         * @param unitT_string string to convert
         * @return corresponding TempUnit
         */
        public TempUnit toTempUnit(String unitT_string) {
            switch (unitT_string) {
                case "Metric":
                    return TempUnit.CELSIUS;

                case "Imperial":
                    return TempUnit.FAHRENHEIT;
                default:
                    return TempUnit.KELVIN;
            }
        }
        
        /**
         * Converts TempUnit to a string of it's unit system
         * @return string representation of TempUnit's unit system
         */
        public String getUnitSystem() {
            switch (this) {
                case KELVIN:
                    return "Standard";

                case CELSIUS:
                    return "Metric";
                
                case FAHRENHEIT:
                    return "Imperial";

                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Extends Exception to CityNotFoundException
     */
    public static class CityNotFoundException extends Exception {
        /**
         * Constructor for the exception
         * @param errorMessage error message string
         */
        public CityNotFoundException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * Class to define temperature parameters for current weather API call.
     */
    public static class TemperatureCurrent {
        
        private float temp, tempMin, tempMax, feelsLike;
        
        /**
         * Constructor for current weather's temperature.
         * @param temp current temperature
         * @param tempMin minimum temperature
         * @param tempMax maximum temperature
         * @param feelsLike feels like temperature
         */
        public TemperatureCurrent(float temp, float tempMin, 
                               float tempMax, float feelsLike) 
        {
            this.temp = temp;
            this.tempMin = tempMin;
            this.tempMax = tempMax;
            this.feelsLike = feelsLike;
        }

        /**
         * Get current temperature.
         * @return current temperature
         */
        public float getTemp() {
            return temp;
        }
        /**
         * Get minimum temperature.
         * @return minimum temperature
         */
        public float getTempMin() {
            return tempMin;
        }
        /**
         * Get maximum temperature.
         * @return maximum temperature
         */
        public float getTempMax() {
            return tempMax;
        }
        /**
         * Get feels like temperature.
         * @return feels like temperature
         */
        public float getFeelsLike() {
            return feelsLike;
        }
    }

    /**
     * Class to define temperature parameters for hourly weather API call.
     * This class is only an alias for the TemperatureCurrent class because 
     * they are equivevalent in terms of their parameters
     */
    public static class TemperatureHour extends TemperatureCurrent {
        /**
         * Constructor for hourly weather's temperature.
         * @param temp hour temperature
         * @param tempMin minimum temperature
         * @param tempMax maximum temperature
         * @param feelsLike feels like temperature
         */
        public TemperatureHour(float temp, float tempMin, float tempMax, float feelsLike) {
            super(temp, tempMin, tempMax, feelsLike);
        }
    }

    /**
     * Class to define temperature parameters for daily weather API call.
     */
    public static class TemperatureDay {

        private float tempMorn, tempDay, tempEve, tempNight, tempMin, tempMax, 
                      feelsLikeMorn, feelsLikeDay, feelsLikeEve, feelsLikeNight;

        /**
         * Constructor for a day's temperature.
         * @param tempMorn morning temperature
         * @param tempDay day temperature
         * @param tempEve evening temperature
         * @param tempNight night temperature
         * @param tempMin minimum temperature of the day
         * @param tempMax maximum temperature of the day
         * @param feelsLikeMorn feels like temperature in the morning
         * @param feelsLikeDay feels like temperature in the day
         * @param feelsLikeEve feels like temperature in the evening
         * @param feelsLikeNight feels like temperature in the night
         */
        public TemperatureDay(float tempMorn, float tempDay, float tempEve, float tempNight,
                              float tempMin, float tempMax, float feelsLikeMorn, 
                              float feelsLikeDay, float feelsLikeEve, float feelsLikeNight)
        {
            this.tempMorn = tempMorn;
            this.tempDay = tempDay;
            this.tempEve = tempEve;
            this.tempNight = tempNight;
            this.tempMin = tempMin;
            this.tempMax = tempMax;
            this.feelsLikeMorn = feelsLikeMorn;
            this.feelsLikeDay = feelsLikeDay;
            this.feelsLikeEve = feelsLikeEve;
            this.feelsLikeNight = feelsLikeNight;
        }

        /**
         * Get morning temperature.
         * @return morning temperature
         */
        public float getTempMorn() {
            return tempMorn;
        }
        /**
         * Get day temperature.
         * @return day temperature
         */
        public float getTempDay() {
            return tempDay;
        }
        /**
         * Get evening temperature.
         * @return evening temperature
         */
        public float getTempEve() {
            return tempEve;
        }
        /**
         * Get night temperature.
         * @return night temperature
         */
        public float getTempNight() {
            return tempNight;
        }
        /**
         * Get minimum temperature of the day.
         * @return minimum temperature of the day
         */
        public float getTempMin() {
            return tempMin;
        }
        /**
         * Get maximum temperature of the day.
         * @return maximum temperature of the day
         */
        public float getTempMax() {
            return tempMax;
        }
        /**
         * Get feels like temperature in the morning.
         * @return feels like temperature in the morning
         */
        public float getFeelsLikeMorn() {
            return feelsLikeMorn;
        }
        /**
         * Get feels like temperature in the day.
         * @return feels like temperature in the day
         */
        public float getFeelsLikeDay() {
            return feelsLikeDay;
        }
        /**
         * Get feels like temperature in the evening.
         * @return feels like temperature in the evening
         */
        public float getFeelsLikeEve() {
            return feelsLikeEve;
        }
        /**
         * Get feels like temperature in the night.
         * @return feels like temperature in the night
         */
        public float getFeelsLikeNight() {
            return feelsLikeNight;
        }
    }

    /**
     * Class to define precipitation parameters for current weather API call.
     */
    public static class PrecipitationCurrent {
        
        private float rain1h, snow1h; // mm
        
        /**
         * Constructor for current precipitation.
         * @param rain1h rain amount for the last hour in mm
         * @param snow1h snow amount for the last hour in mm
         * @throws IllegalArgumentException if a parameter is negative
         */
        public PrecipitationCurrent (float rain1h,  float snow1h)
            throws IllegalArgumentException
        {
            if (rain1h < 0 ||  snow1h < 0) {
                throw new IllegalArgumentException("Negative values not allowed.");
            }
            this.rain1h = rain1h;
            this.snow1h = snow1h;
        }
        /**
         * Get rain volume in mm in the last hour.
         * @return rain volume in the last hour
         */
        public float getRain1h() {
            return rain1h;
        }
        /**
         * Get snow volume in mm in the last hour.
         * @return snow volume in the last hour
         */
        public float getSnow1h() {
            return snow1h;
        }
    }

    /**
     * Class to define precipitation parameters for hourly weather API call.
     */
    public static class PrecipitationHour {
        
        private float rain1h, snow1h; // mm
        private float probability; // %
        
        /**
         * Constructor for hourly precipitation.
         * @param rain1h rain for the hour in mm
         * @param snow1h snow for the hour in mm
         * @param probability rain probability for the hour between 0 and 1
         * @throws IllegalArgumentException if a parameter is negative
         */
        public PrecipitationHour(float rain1h, float snow1h, float probability) 
            throws IllegalArgumentException 
        {
            if (rain1h < 0 || snow1h < 0 || probability < 0) {
                throw new IllegalArgumentException("Negative values not allowed.");
            }
            this.rain1h = rain1h;
            this.snow1h = snow1h;
            this.probability = 100 * probability;
        }
        
        /**
         * Get rain volume for the last hour.
         * @return rain volume for the last hour in mm
         */
        public float getRain1h() {
            return rain1h;
        }
        /**
         * Get snow volume for the last hour.
         * @return snow volume for the last hour in mm
         */
        public float getSnow1h() {
            return snow1h;
        }
        /**
         * Get precipitation probability (for rain or snow).
         * @return precipitation probability in %
         */
        public float getProbability() {
            return probability;
        }
    }

    /**
     * Class to define precipitation parameters for daily weather API call.
     */
    public static class PrecipitationDay {
        
        private float rain, snow; // mm
        private float probability; // %
        
        /**
         * Constructor for daily precipitation.
         * @param rain rain for the day in mm
         * @param snow snow for the day in mm
         * @param probability precipitation probability for the day between 0 and 1
         */
        public PrecipitationDay(float rain, float snow, float probability) throws IllegalArgumentException {
            if (rain < 0 || snow < 0 || probability < 0) {
                throw new IllegalArgumentException("Negative values not allowed.");
            }
            this.rain = rain;
            this.snow = snow;
            this.probability = 100 * probability;
        }
        
        /**
         * Get rain volume for the day.
         * @return rain volume for the day in mm
         */
        public float getRain() {
            return rain;
        }
        
        /**
         * Get snow volume for the day.
         * @return snow volume for the day in mm
         */
        public float getSnow() {
            return snow;
        }
        
        /**
         * Get precipitation probability (for rain or snow).
         * @return precipitation probability in %
         */
        public float getProbability() {
            return probability;
        }
    }

    /**
     * Defines a coordinate type, in terms of latitude and longitude.
     */
    public static class Coord {
        
        private float lon, lat;
    
        /**
         * Constructor for a coordinate.
         * @param lon longitude
         * @param lat latitude
         */
        public Coord(float lon, float lat) {
            this.lon = lon;
            this.lat = lat;
        }

        /**
         * Get longitude.
         * @return longitude
         */
        public float getLon() {
            return lon;
        }
        /**
         * Get latitude.
         * @return latitude
         */
        public float getLat() {
            return lat;
        }

        /**
         * Compares this object to the specified object. 
         * The result is true if and only if the argument is not
         * null and is a Coord object that represents the same coordinates as 
         * this object.
         *
         * @param obj the object to compare this Coord against
         * @return true if the given object represents a Coord equivalent to this 
         *         Coord, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Coord other = (Coord) obj;
            return Float.compare(other.getLon(), lon) == 0 &&
                   Float.compare(other.getLat(), lat) == 0;
        }

        /**
         * Returns a string representation of the coordinate: "lat lon"
         */
        @Override
        public String toString() {
            return (lat + " " + lon);
        }

    }

    /**
     * Defines a type for wind data.
     */
    public static class Wind {
        
        private float speed;
        private int deg;
    
        /**
         * Constructor for wind data.
         * @param speed wind speed
         * @param deg wind direction in degrees
         * @throws IllegalArgumentException if a parameter is negative
         */
        public Wind(float speed, int deg) throws IllegalArgumentException {
            if (speed < 0 || deg < 0) {
                throw new IllegalArgumentException("Negative values not allowed.");
            }
            this.speed = speed;
            this.deg = deg;
        }

        /**
         * Get wind speed.
         * @return wind speed
         */
        public float getSpeed() {
            return speed;
        }
        /**
         * Get wind direction in degrees.
         * @return wind direction in degrees
         */
        public int getDeg() {
            return deg;
        }
    }
}
