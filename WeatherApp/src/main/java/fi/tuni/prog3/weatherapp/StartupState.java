/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.weatherapp;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for interfacing with the startup configuration file.
 */
public class StartupState implements iReadAndWriteToFile {

    /**
     * Empty constructor
     */
    public StartupState() {}

    /**
     * Reads JSON from the application's startup file.
     * @return startup configuration
     * @throws Exception if reading the file was not successful
     */
    public StartupData readFromFile() throws Exception {
        
        return readFromFile(WeatherApp.STARTUP_FILE);
    }
    
    /**
     * Reads JSON from the given file.
     * @param filename file to read from
     * @return StartupData object
     * @throws Exception if the method e.g, cannot find the file
     */
    @Override
    public StartupData readFromFile(String filename) throws Exception {
        
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = stringBuilder.toString();
        Gson gson = new Gson();
        return gson.fromJson(jsonString, StartupData.class);
    }
    
    /**
     * Writes JSON to application's startup file.
     * @param startupData startup configuration
     * @return true if writing was successful
     * @throws Exception if writing the file was not successful
     */
    
    public boolean writeToFile(StartupData startupData) throws Exception {
        return writeToFile(startupData, WeatherApp.STARTUP_FILE);
    }
    
    /**
     * Writes the given startup config to the given JSON file.
     * @param startupData startup configuration
     * @return true if writing was successful
     * @throws Exception if writing the file was not successful
     */
    @Override
    public boolean writeToFile(StartupData startupData, String filename) throws Exception {
        Gson gson = new Gson();
        String json = gson.toJson(startupData);

        // Write JSON to file
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
}
