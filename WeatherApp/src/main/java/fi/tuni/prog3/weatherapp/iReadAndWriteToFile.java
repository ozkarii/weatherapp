package fi.tuni.prog3.weatherapp;

/**
 * Interface with methods to read from a file and write to a file.
 */
public interface iReadAndWriteToFile {

    /**
     * Reads JSON from file.
     * @param filename the name of the file to read from.
     * @return StartupData object
     * @throws Exception if the method e.g, cannot find the file
     */
    public StartupData readFromFile(String filename) throws Exception;
    /**
     * Write the student progress as JSON into file.
     * @param startupData the data to write.
     * @param filename the name of the file to write to.
     * @return true if the write was successful, otherwise false.
     * @throws Exception if the method e.g., cannot write to a file.
     */
    public boolean writeToFile(StartupData startupData, String filename) throws Exception;
}
