/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import fi.tuni.prog3.weatherapp.Types.*;
import com.google.gson.*;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * Pulls data from openweathermap API, and saves it to different WeatherData 
 * objects.
 */
public class JsonParser implements iAPI {

    /**
     *
     */
    private String apiId = WeatherApp.API_KEY;

    /**
     * Empty constructor
     */
    public JsonParser() {}

    /**
     * Gets coords for location
     * @param loc location as a string. E.g. city name.
     * @return Coord
     * @throws CityNotFoundException if city is not found
     */
    @Override
    public Coord lookUpLocation(String loc) throws CityNotFoundException {
        
        String urlString = String.format(
                "http://pro.openweathermap.org/geo/1.0/direct?q=%s,0=1&appid=%s",
                loc, apiId);
        String json = this.pullFromAPI(urlString);
        if ("[]".equals(json)) {
            throw new CityNotFoundException("City " + loc + " not found in API");
        }
        
        // Type adapter to make gson usable with Coord class.
        Gson gson = new GsonBuilder().registerTypeAdapter(
                Coord.class, new CoordTypeAdapter()).create();
        Type listType = new TypeToken<List<Coord>>(){}.getType();
        List<Coord> coord = gson.fromJson(json, listType);
        
        return coord.get(0);

    }

    /**
     * Gets current weather for a location
     * @param lat latitude coordinate for the desired location
     * @param lon longitude coordinate for the desired location
     * @param unitT Unit system to be used for temperature data
     * @return WeatherDataCurrent object
     */
    @Override
    public WeatherDataCurrent getCurrentWeather(double lat, double lon, TempUnit unitT) {
        
        String urlString = String.format(
                "https://pro.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=%s",
                lat, lon, apiId, unitT.getUnitSystem());
        String json = this.pullFromAPI(urlString);
        // Type adapter to make gson usable with WeatherDataCurrent class.
        Gson gson = new GsonBuilder().registerTypeAdapter(
                WeatherDataCurrent.class, new WeatherDataCurrentDeserializerClass()).create();
        WeatherDataCurrent current = gson.fromJson(json, WeatherDataCurrent.class);
        
        return current;
    }

    /**
     * Gets hourly forecast weather for a location
     * @param lat latitude coordinate for the desired location
     * @param lon longitude coordinate for the desired location
     * @param unitT Unit system to be used for temperature data
     * @return ArrayList of WeatherDataHour objects
     */
    @Override
    public ArrayList<WeatherDataHour> getForecastHourly(double lat, double lon, TempUnit unitT) {
        String urlString = String.format(
                "https://pro.openweathermap.org/data/2.5/forecast/hourly?lat=%s&lon=%s&appid=%s&units=%s",
                lat, lon, apiId, unitT.getUnitSystem());
        String json = this.pullFromAPI(urlString);
        Coord coord = new Coord((float)lon,(float)lat);
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        JsonArray jsonArr = jsonElement.getAsJsonObject().get("list").getAsJsonArray();
        ArrayList<WeatherDataHour> weatherData = new ArrayList<WeatherDataHour>();
        
        String city = jsonElement.getAsJsonObject().get("city").getAsJsonObject()
                .get("name").getAsString();
        int sunrise = jsonElement.getAsJsonObject().get("city").getAsJsonObject()
                .get("sunrise").getAsInt();
        int sunset = jsonElement.getAsJsonObject().get("city").getAsJsonObject()
                .get("sunset").getAsInt();
        
        for (JsonElement element : jsonArr) {
            JsonObject jsonObject = element.getAsJsonObject();
            long time = jsonObject.get("dt").getAsLong();
            JsonObject weather = jsonObject.get("weather").getAsJsonArray()
                    .get(0).getAsJsonObject();
            
            int id = weather.get("id").getAsInt();
            String iconId = weather.get("icon").getAsString();
            String description = weather.get("description").getAsString();
            
            JsonObject main = jsonObject.get("main").getAsJsonObject();
            
            TemperatureHour temp = new TemperatureHour(main.get("temp").getAsFloat(),
                    main.get("temp_min").getAsFloat(), main.get("temp_max").getAsFloat(), 
                    main.get("feels_like").getAsFloat());
            
            int pressure = main.get("pressure").getAsInt();
            int humidity = main.get("humidity").getAsInt();
            JsonObject windObj = jsonObject.get("wind").getAsJsonObject();
            Wind wind = new Wind(windObj.get("speed").getAsFloat(), windObj.get("deg").getAsInt());
            int clouds = jsonObject.get("clouds").getAsJsonObject().get("all").getAsInt();
            float pop = jsonObject.get("pop").getAsFloat();
            float rain1h = 0;
            float snow1h = 0;
            if (jsonObject.has("rain")) {
                rain1h = jsonObject.get("rain").getAsJsonObject().get("1h").getAsFloat();
            } if (jsonObject.has("snow")) {
                snow1h = jsonObject.get("snow").getAsJsonObject().get("1h").getAsFloat();
            }
            PrecipitationHour precipitation = new PrecipitationHour (rain1h, snow1h, pop);
            WeatherDataHour hour = new WeatherDataHour(time,id,iconId,description
                    ,city,coord,temp,pressure,humidity,wind,clouds,precipitation,sunrise,sunset);
            
            weatherData.add(hour);
        }
        return weatherData;
    }

    /**
     * Gets hourly forecast weather for a location
     * @param lat latitude coordinate for the desired location
     * @param lon longitude coordinate for the desired location
     * @param unitT Unit system to be used for temperature data
     * @return ArrayList of WeatherDataDay objects
     */
    @Override
    public ArrayList<WeatherDataDay> getForecastDaily(double lat, double lon, TempUnit unitT) {
        String urlString = String.format(
                "https://pro.openweathermap.org/data/2.5/forecast/daily?lat=%s&lon=%s&cnt=16&appid=%s&units=%s",
                lat, lon, apiId, unitT.getUnitSystem());
        String json = this.pullFromAPI(urlString);
        Coord coord = new Coord((float)lon,(float)lat);
        Gson gson = new Gson();
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        // Get array containing daily data elements
        JsonArray jsonArr = jsonElement.getAsJsonObject().get("list").getAsJsonArray();
        ArrayList<WeatherDataDay> weatherData = new ArrayList<WeatherDataDay>();
        
        String city = jsonElement.getAsJsonObject().get("city").getAsJsonObject()
                .get("name").getAsString();
        // Go array containing daily data elements, and save them in 
        // ArrayList containing WeatherDataDay objects
        for (JsonElement element : jsonArr) {
            JsonObject jsonObject = element.getAsJsonObject();
            long time = jsonObject.get("dt").getAsLong();
            JsonObject weather = jsonObject.get("weather").getAsJsonArray()
                    .get(0).getAsJsonObject();
            
            int id = weather.get("id").getAsInt();
            String iconId = weather.get("icon").getAsString();
            String description = weather.get("description").getAsString();
            
            JsonObject tempObj = jsonObject.get("temp").getAsJsonObject();
            JsonObject feelsObj = jsonObject.get("feels_like").getAsJsonObject();

            TemperatureDay temp = new TemperatureDay(tempObj.get("morn").getAsFloat(),
                    tempObj.get("day").getAsFloat(), tempObj.get("eve").getAsFloat(), 
                    tempObj.get("night").getAsFloat(), tempObj.get("min").getAsFloat(),
                    tempObj.get("max").getAsFloat(), feelsObj.get("morn").getAsFloat(), 
                    feelsObj.get("day").getAsFloat(), feelsObj.get("eve").getAsFloat(), 
                    feelsObj.get("night").getAsFloat());
            
            int pressure = jsonObject.get("pressure").getAsInt();
            int humidity = jsonObject.get("humidity").getAsInt();
            
            Wind wind = new Wind(jsonObject.get("speed").getAsFloat(), 
                    jsonObject.get("deg").getAsInt());
            int clouds = jsonObject.get("clouds").getAsInt();
            
            int sunrise = jsonObject.get("sunrise").getAsInt();
            int sunset = jsonObject.get("sunset").getAsInt();
            
            float rain = 0;
            float snow = 0;
            if (jsonObject.has("rain")) {
                rain = jsonObject.get("rain").getAsFloat();
            } if (jsonObject.has("snow")) {
                snow = jsonObject.get("snow").getAsFloat();
            }
            float pop = jsonObject.get("pop").getAsFloat();
            
            PrecipitationDay precipitation = new PrecipitationDay(rain,snow, pop);
            
            weatherData.add(new WeatherDataDay(time, id, iconId, description, 
                            city, coord, temp, pressure, humidity, wind, clouds, 
                            precipitation, sunrise,
                            sunset));
        }   
        
        return weatherData;
    }
    
    /**
     * Pull String from API located at urlString
     * @param urlString URL to pull from
     * @return String from API
     */
    private String pullFromAPI(String urlString) {
        String json = "";
        try {
        @SuppressWarnings("deprecation")
        URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            json = response.toString();
        } catch (IOException e) {
            System.err.println("\n\n --- Could not connect to OpenWeatherMap API! --- \n\n");
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Type adapter to use Coord class with GSON
     */
    public class CoordTypeAdapter extends TypeAdapter<Coord> {

        /**
         * Empty constructor.
         */
        public CoordTypeAdapter() {}

        /**
         * Non implemented write adapter to write JSON from Coord
         * @param out JSON out
         * @param coord Coordinate to write
         * @throws IOException if writing fails
         */
        @Override
        public void write(JsonWriter out, Coord coord) throws IOException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        /**
         * Reader for Coord from JSON
         * @param in JSON in
         * @return Coord from JSON
         * @throws IOException if reading fails
         */
        @Override
        public Coord read(JsonReader in) throws IOException {
            in.beginObject();
            float lon = 0;
            float lat = 0;
            while (in.hasNext()) {
                String name = in.nextName();
                if ("lon".equals(name)) {
                    lon = (float) in.nextDouble();
                } else if ("lat".equals(name)) {
                    lat = (float) in.nextDouble();
                } else {
                    in.skipValue();
                }
            }
            in.endObject();
            return new Coord(lon, lat);
        }
    }
    
    /**
     * Class for deserializer for JSON containing data for WeatherDataCurrent 
     */
    public class WeatherDataCurrentDeserializerClass implements JsonDeserializer<WeatherDataCurrent> {

        /**
         * Empty constructor
         */
        public WeatherDataCurrentDeserializerClass() {}

        /**
         * Deserilizer for WeatherDataCurrent element in JSON
         * @param json JSON element
         * @param typeOfT Type of temperature
         * @param context JSON context
         * @return WeatherDataCurrent object
         * @throws JsonParseException if parsing fails
         */
        @Override
        public WeatherDataCurrent deserialize(JsonElement json, Type typeOfT, 
                JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            
            JsonObject weather = jsonObject.get("weather").getAsJsonArray().get(0).getAsJsonObject();
            int id = weather.get("id").getAsInt();
            String iconId = weather.get("icon").getAsString();
            String description = weather.get("description").getAsString();
            JsonObject main = jsonObject.get("main").getAsJsonObject();
            
            TemperatureCurrent tempCurrent = new TemperatureCurrent(main.get("temp").getAsFloat(),
                    main.get("temp_min").getAsFloat(), main.get("temp_max").getAsFloat(), 
                    main.get("feels_like").getAsFloat());
            
            JsonObject windObj = jsonObject.get("wind").getAsJsonObject();
            Wind wind = new Wind(windObj.get("speed").getAsFloat(), windObj.get("deg").getAsInt());

            int clouds = jsonObject.get("clouds").getAsJsonObject().get("all").getAsInt();
            
            int sunrise = jsonObject.get("sys").getAsJsonObject().get("sunrise").getAsInt();
            int sunset = jsonObject.get("sys").getAsJsonObject().get("sunset").getAsInt();
            
            JsonObject coordJsonObj = jsonObject.get("coord").getAsJsonObject();
            Coord coord = new Coord(coordJsonObj.get("lon").getAsFloat(),
                    coordJsonObj.get("lat").getAsFloat());
            
            float rain1h = 0;
            float snow1h = 0;
            if (jsonObject.has("rain")) {
                rain1h = jsonObject.get("rain").getAsJsonObject().get("1h").getAsFloat();
            } if (jsonObject.has("snow")) {
                snow1h = jsonObject.get("snow").getAsJsonObject().get("1h").getAsFloat();
            }
            
            PrecipitationCurrent precipitation = new PrecipitationCurrent (rain1h, snow1h);
            
            WeatherDataCurrent weatherDataCurrent = new WeatherDataCurrent(
                    jsonObject.get("dt").getAsLong(),
                    id,
                    iconId,
                    description,
                    jsonObject.get("name").getAsString(),
                    coord,
                    tempCurrent,
                    main.get("pressure").getAsInt(),
                    main.get("humidity").getAsInt(),
                    wind,
                    clouds,
                    precipitation,
                    sunrise,
                    sunset
            );
            return weatherDataCurrent;}
        }
}
