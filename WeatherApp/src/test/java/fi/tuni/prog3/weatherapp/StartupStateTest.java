/*
 * 
 * 
 */
package fi.tuni.prog3.weatherapp;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * 
 */
public class StartupStateTest {
    
    public StartupStateTest() {
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
     * Test of readFromFile method, of class StartupState.
     * @param favorites
     * @param searchHistory
     * @param settings
     * @param filename
     * @throws java.lang.Exception
     */
    @ParameterizedTest
    @MethodSource("testReadFromData")
    public void testReadFromFile(ArrayList<String> favorites, 
            ArrayList<String> searchHistory, Settings settings, String filename) throws Exception {
        System.out.println("readFromFile");
        StartupState instance = new StartupState();
        StartupData expResult = new StartupData(settings, favorites, searchHistory);
        StartupData result = instance.readFromFile(filename);
        assertEquals(expResult, result);
        
    }
    
    /**
     *
     * @return
     */
    static Stream<Arguments> testReadFromData() {
        ArrayList<String> favorites = new ArrayList<String>();
        favorites.add("Helsinki");
        favorites.add("Tampere");
        ArrayList<String> favoritesEmpty = new ArrayList<String>();
        
        ArrayList<String> searchHistory = new ArrayList<String>();
        searchHistory.add("Vantaa");
        searchHistory.add("Alavus");
        ArrayList<String> searchHistoryEmpty = new ArrayList<String>();
        
        Settings settings = new Settings("Hervanta", "Metric");
        return Stream.of(
            Arguments.of(favorites, searchHistory, settings, "startupDataTest.json"),
            Arguments.of(favoritesEmpty, searchHistory, settings, "startupDataTest2.json"),
            Arguments.of(favorites, searchHistoryEmpty, settings, "startupDataTest3.json")
        );
    }
    /**
     * Test of writeToFile method, of class StartupState.
     * @param favorites
     * @param searchHistory
     * @param settings
     * @param filename
     * @throws java.lang.Exception
     */
    @ParameterizedTest
    @MethodSource("testReadFromData")
    public void testWriteToFile(ArrayList<String> favorites, 
            ArrayList<String> searchHistory, Settings settings, String filename) throws Exception {
        System.out.println("writeToFile");
        StartupState instance = new StartupState();
        StartupData expResult = new StartupData(settings, favorites, searchHistory);
        instance.writeToFile(expResult, filename);
        StartupData result = instance.readFromFile(filename);
        assertEquals(expResult, result);
    }
    
}
