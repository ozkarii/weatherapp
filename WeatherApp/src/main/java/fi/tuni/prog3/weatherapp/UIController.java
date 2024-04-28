package fi.tuni.prog3.weatherapp;

import fi.tuni.prog3.weatherapp.Types.*;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.Instant;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/**
 * Main controller for the UI.
 * Requires WeatherApp's static methods to work
 */
public class UIController {
        

    @FXML
    private ComboBox<String> searchField;
    
    @FXML
    private Button settingsBtn, mapRefreshBtn;
    
    @FXML
    private ImageView favoritesImView, currentWindIcon, currentWeatherIcon,
                       currentBarometerIcon, currentFeelsLikeIcon;

    @FXML
    private WebView mapView;
    
    @FXML
    private Text locationText, currentCond, currentTemp, currentFeelsLike, 
                 currentWindSpd, currentHumidity, currentPressure;

    @FXML
    private AnchorPane rootPane;
 
    @FXML
    private ListView<VBox> hourlyBoxesContainer, dailyBoxesContainer;

    private Image favoritesIm;
    private Image notFavoritesIm;

    private ArrayList<DayBox> dayBoxes = new ArrayList<>();
    private ArrayList<HourBox> hourBoxes = new ArrayList<>();
    
    /**
     * Constructor for attribute initialization
     */
    public UIController() {
        this.favoritesIm = new Image(getClass().getResourceAsStream(WeatherApp.FAVORITES_IM_FILE));
        this.notFavoritesIm = new Image(getClass().getResourceAsStream(WeatherApp.NOT_FAVORITES_IM_FILE));
    }

    /**
     * Method for handling the ActionEvent of pressing the settings button. 
     * Opens a new settings window.
     */
    @FXML
    private void settingsBtnPressed() {
        TempUnit unitT = WeatherApp.getUnitT();
        settingsBtn.setDisable(true);
        Stage settingsStage = new Stage();
        settingsStage.setTitle("Settings");
        VBox vbox = new VBox(50);
        HBox hboxT = new HBox(10);
        Scene settingsScene = new Scene(vbox, 200, 100);

        settingsStage.setScene(settingsScene);
        Text unitsT = new Text("Unit system:");
        hboxT.getChildren().add(unitsT);

        ChoiceBox<String> choiceBoxT = new ChoiceBox<>();
        choiceBoxT.getItems().addAll("Metric", "Imperial", "Standard");
        choiceBoxT.setValue(unitT.getUnitSystem());
        hboxT.getChildren().add(choiceBoxT);

        vbox.getChildren().add(hboxT);

        Button close = new Button("Save and close");
        vbox.getChildren().add(close);

        settingsStage.initModality(Modality.WINDOW_MODAL);

        settingsStage.show();

        close.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                TempUnit newUnit = unitT.toTempUnit(choiceBoxT.getValue());
                if (! newUnit.equals(unitT)) {
                    WeatherApp.setUnitT(newUnit);                
                    try {
                        WeatherApp.newWeatherState(WeatherApp.getState().getLocation());
                    } catch (CityNotFoundException ex) {
                        
                    }
                    updateCurrent();
                    updateDaily();
                    updateHourly();
                }
                settingsBtn.setDisable(false);
                settingsStage.close();
            }
        });
        
        settingsStage.setOnCloseRequest(eh -> settingsBtn.setDisable(false));
    }
    
    /**
     * Method for handling the ActionEvent of pressing the search button. If a 
     * valid location was given, weather data is updated. Search history is 
     * updated.
     */
    @FXML private void searchBtnPressed() {
        try {
            if (searchField.getValue() == null || searchField.getValue().equals("")) {
                throw new CityNotFoundException("Location is empty");
            }
            WeatherApp.newWeatherState(searchField.getValue());
            String location = WeatherApp.getState().getLocation();
            ArrayList<String> history = WeatherApp.getSearchHistory();
            ArrayList<String> favorites = WeatherApp.getFavorites();
            locationText.setText(location);
            if (favorites.contains(location)) {
                favoritesImView.setImage(favoritesIm);
            } else {
                favoritesImView.setImage(notFavoritesIm);
            }
            int searchHistorySize = 5;
            /* New location is added to the search history and if the search 
            history is full the first added location is removed.*/
            if (!history.contains(location)) {
                if (history.size() == searchHistorySize) {
                    /* If the removed location is a favorite, it is not removed
                    from the search field menu */
                    if (!favorites.contains(history.get(0))) {
                        searchField.getItems().remove(history.get(0));
                    }
                    /* If the new location is a favorite, it is not added again
                    to the search field menu */
                    if (!favorites.contains(location)) {
                        searchField.getItems().add(location);
                    }
                    history.remove(0);
                    history.add(location);
                } else {
                    if (!favorites.contains(location)) {
                        searchField.getItems().add(location);
                    }
                    history.add(location);
                }
            } else {
                
                history.remove(location);
                history.add(location);
            }
            WeatherApp.setSearchHistory(history);

            updateLocationTextSize();

            searchField.setStyle("-fx-font-size: 20px;");
            searchField.setValue(null);
            updateCurrent();
            updateDaily();
            updateHourly();
            loadMap();

        } catch (CityNotFoundException e) {
            searchField.setStyle("-fx-font-size: 20px; -fx-border-color: red");
        } 
    }
    
    /**
     * Method for handling the ActionEvent of pressing the favorites button. 
     * The current location is either added or removed from favorites
     */
    @FXML
    private void favoritesBtnPressed() {
        String location = WeatherApp.getState().getLocation();
        ArrayList<String> favorites = WeatherApp.getFavorites();
        if (! favorites.contains(location)) {
            favoritesImView.setImage(favoritesIm);
            favorites.add(location);
            /* If the location is already in the search field menu, it must be
            removed and added again to get the favorite icon in front of it.*/
            if (searchField.getItems().contains(location)) {
                searchField.getItems().remove(location);
            }
            searchField.getItems().add(location);
            searchField.setValue(null);
        } else {
            favoritesImView.setImage(notFavoritesIm);
            favorites.remove(location);
            searchField.getItems().remove(location);
            if (WeatherApp.getSearchHistory().contains(location)) {
                searchField.getItems().add(location);
            } 
        }
        WeatherApp.setFavorites(favorites);
    }

    /**
     * Method to get the image of a specific weather icon id
     * @param id the id of the image, should be the same as OpenWeatherMap API icon id
     * @return image object
     */
    private Image getWeatherImageById(String id) {
        String imagePath = WeatherApp.WEATHER_ICONS_PATH + id 
                                      + WeatherApp.WEATHER_IMAGE_FORMAT;
        return new Image(getClass().getResourceAsStream(imagePath));
    }

    /**
     * Method to convert Unix time to a date string dd.MM.yyyy
     * @param time unix timestamp in seconds
     * @param timeZone time zone id
     * @return the date string
     */
    private String toDateString(long time, ZoneId timeZone) {
        Instant instant = Instant.ofEpochSecond(time);
        LocalDate date = instant.atZone(timeZone).toLocalDate();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");
        return date.format(formatter);
    }

    /**
     * Method to convert Unix time to a time string HH:mm
     * @param time unix timestamp in seconds
     * @param timeZone time zone id
     * @return
     */
    private String toTimeString(long time, ZoneId timeZone) {
        Instant instant = Instant.ofEpochSecond(time);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return instant.atZone(timeZone).format(formatter);
    }

    /**
     * Update current weather box data according to current weather state
     */
    private void updateCurrent() {

        final int LOW_PRESSURE_THRESHOLD = 1011;
        final int HIGH_PRESSURE_THRESHOLD = 1015;

        WeatherDataCurrent weather = WeatherApp.getCurrentWeather();
        TempUnit unitT = WeatherApp.getUnitT();

        String iconId = weather.getIconId();
        Image weatherImage = getWeatherImageById(iconId);
        currentWeatherIcon.setImage(weatherImage);
        
        float temp = weather.getTemperature().getTemp();
        currentTemp.setText(Math.round(temp) + unitT.toString());
        float feelsLike = weather.getTemperature().getFeelsLike();
        currentFeelsLike.setText(String.format("%d %s", Math.round(feelsLike),
                                               WeatherApp.getUnitT().toString()));
        
        // The icon is red by default in FXML, set it to blue if feels like is lower 
        // than the indicated temperature
        if (feelsLike < temp) {
            // set hue to blue
            currentFeelsLikeIcon.setEffect(null);
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setHue(-0.8);
            currentFeelsLikeIcon.setEffect(colorAdjust);
            currentFeelsLike.setFill(javafx.scene.paint.Color.BLUE);
        }
        // set to gray if equal
        else if (feelsLike == temp) {
            currentFeelsLikeIcon.setEffect(null);
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setContrast(-1);
            currentFeelsLikeIcon.setEffect(colorAdjust);
            currentFeelsLike.setFill(javafx.scene.paint.Color.web("#3b3b3b"));
        }

        currentCond.setText(weather.getDescription());
        currentWindIcon.setRotate(weather.getWind().getDeg());
        currentWindSpd.setText(Integer.toString(Math.round(weather.getWind().getSpeed())));
        currentHumidity.setText(weather.getHumidity() + " %");
        
        float pressure = weather.getPressure();
        currentPressure.setText(String.format("%.0f hPa", pressure));
        if (pressure < LOW_PRESSURE_THRESHOLD) {
            currentBarometerIcon.setImage(new Image(getClass().getResourceAsStream(
                                          WeatherApp.BAROMETER_HIGH_IM_PATH)));
            currentBarometerIcon.setScaleX(-1);
        }
        else if (pressure > HIGH_PRESSURE_THRESHOLD) {
            currentBarometerIcon.setImage(new Image(getClass().getResourceAsStream(
                                          WeatherApp.BAROMETER_HIGH_IM_PATH)));
        }
        else {
            currentBarometerIcon.setImage(new Image(getClass().getResourceAsStream(
                                          WeatherApp.BAROMETER_IM_PATH)));
        }
        
    }

    /**
     * Handler for clicking a day box. Used for scrolling hour boxes
     * to the corresponding day.
     * @param event event to be handled
     */
    private void onDayBoxClicked(MouseEvent event) {
        VBox box = (VBox) event.getSource();
        int dayIndex = dailyBoxesContainer.getItems().indexOf(box);
        
        VBox firstHourBox = (VBox) hourlyBoxesContainer.getItems().get(0);
        Text textObject = (Text) firstHourBox.getChildren().get(1);
        String hourText = textObject.getText();
        String[] parts = hourText.split(":");
        int firstHour = Integer.parseInt(parts[0]);
        
        int secondDayIndex = 24 - firstHour;

        if (dayIndex > 0) {
            hourlyBoxesContainer.scrollTo(secondDayIndex + 24 * (dayIndex - 1));
        }
        else {
            hourlyBoxesContainer.scrollTo(0);
        }

    }
    
    /**
     * Handler for pressing the map refresh button. Reloads the map with the
     * current weather state's coordinates.
     */
    @FXML
    private void onMapRefreshBtnPressed(ActionEvent event) {
        loadMap();
    }

    /**
     * Fills one day's forecast box with the given WeatherDataDay object's data
     * @param weatherDay weather data to be used
     * @param controller the target box's controller instance
     */
    private void fillDayBox(WeatherDataDay weatherDay, DayBoxController controller) {
        controller.setDate(toDateString(weatherDay.getTime(), ZoneId.systemDefault()));
        controller.setCondition(weatherDay.getDescription());
        float temp = weatherDay.getTemperature().getTempDay();
        controller.setTemp(temp);
        controller.setLowTemp(weatherDay.getTemperature().getTempMin());
        controller.setHighTemp(weatherDay.getTemperature().getTempMax());
        controller.setFeelsLike(weatherDay.getTemperature().getFeelsLikeDay(), temp);
        controller.setWindSpd(weatherDay.getWind().getSpeed());
        controller.setWindDir(weatherDay.getWind().getDeg());
        controller.setWeatherIcon(weatherDay.getIconId());
        controller.setPressure(weatherDay.getPressure());
        controller.setPrecipAmount(weatherDay.getPrecipitation().getRain() + weatherDay.getPrecipitation().getSnow());
        controller.setPrecipProb(weatherDay.getPrecipitation().getProbability());
        controller.setHumidity(weatherDay.getHumidity());
    }

    /**
     * Creates new daily forecast boxes and fills them with the
     * current weather state's data. 
     */
    private void initDaily() {
        ObservableList<VBox> containerItems = dailyBoxesContainer.getItems();
        containerItems.clear();
        dayBoxes.clear();
        WeatherState state = WeatherApp.getState();
        for (int i = 0; i < state.getDailyForecastCount(); i++) {    
            DayBox dayBox = new DayBox();
            dayBoxes.add(dayBox);
            dayBox.getBox().setOnMouseClicked(this::onDayBoxClicked);
            containerItems.add(dayBox.getBox());
            DayBoxController controller = dayBox.getController();
            WeatherDataDay weatherDay = state.getWeatherDay(i);
            fillDayBox(weatherDay, controller);
        }
    }

    /**
     * Updates the existing daily forecast boxes with the
     * current weather state's data. 
     */
    private void updateDaily() {
        WeatherState state = WeatherApp.getState();
        for (int i = 0; i < dayBoxes.size(); i++) {
            DayBoxController controller = dayBoxes.get(i).getController();
            WeatherDataDay weatherDay = state.getWeatherDay(i);
            fillDayBox(weatherDay, controller);
        }
    }

    /**
     * Fills one hour's forecast box with the given WeatherDataHour object's data
     * @param weatherHour weather data to be used
     * @param controller the target box's controller instance
     */
    private void fillHourBox(WeatherDataHour weatherHour, HourBoxController controller) {
        controller.setDate(toDateString(weatherHour.getTime(), ZoneId.systemDefault()));
        controller.setTime(toTimeString(weatherHour.getTime(), ZoneId.systemDefault()));
        float temp = weatherHour.getTemperature().getTemp();
        controller.setTemp(temp);
        controller.setFeelsLike(weatherHour.getTemperature().getFeelsLike(), temp);
        controller.setWindSpd(weatherHour.getWind().getSpeed());
        controller.setWindDir(weatherHour.getWind().getDeg());
        controller.setWeatherIcon(weatherHour.getIconId());
        controller.setPrecipAmount(weatherHour.getPrecipitation().getRain1h() + weatherHour.getPrecipitation().getSnow1h());
    }

    /**
     * Creates new hour forecast boxes and fills them with the
     * current weather state's data. 
     */
    private void initHourly() {
        WeatherState state = WeatherApp.getState();
        ObservableList<VBox> containerItems = hourlyBoxesContainer.getItems();
        containerItems.clear();
        hourBoxes.clear();
        int hours = WeatherApp.getState().getHourForecastCount();
        for (int i = 0; i < hours; i++) {
            HourBox hourBox = new HourBox();
            hourBoxes.add(hourBox);
            containerItems.add(hourBox.getBox());
            HourBoxController controller = hourBox.getController();
            WeatherDataHour weatherHour = state.getWeatherHour(i);
            fillHourBox(weatherHour, controller);
        }
        
        // Add text to the end of the list if there are no more hourly
        // forecasts available. Useful when user is clicking a day box
        // which doesn't have hourly forecasts yet.
        VBox emptyBox = new VBox();
        emptyBox.setPrefHeight(312);
        emptyBox.setPrefWidth(70);
        
        Font font = new Font(24);

        Text text1 = new Text("No");
        text1.setFont(font);
        text1.setFill(javafx.scene.paint.Color.web("#3b3b3b"));
        emptyBox.getChildren().add(text1);
        
        Text text2 = new Text("more");
        text2.setFont(font);
        text2.setFill(javafx.scene.paint.Color.web("#3b3b3b"));
        emptyBox.getChildren().add(text2);
        
        Text text3 = new Text("data");
        text3.setFont(font);
        text3.setFill(javafx.scene.paint.Color.web("#3b3b3b"));
        emptyBox.getChildren().add(text3);
        
        hourlyBoxesContainer.getItems().add(emptyBox);
    }

    /**
     * Updates the existing hourly forecast boxes with the
     * current weather state's data. 
     */
    private void updateHourly() {
        WeatherState state = WeatherApp.getState();
        for (int i = 0; i < hourBoxes.size(); i++) {
            HourBoxController controller = hourBoxes.get(i).getController();
            WeatherDataHour weatherHour = state.getWeatherHour(i);
            fillHourBox(weatherHour, controller);
        }
    }
    
    /**
     * Loads the map from it's html file with the current weather states's
     * coordinates.
     */
    private void loadMap() {
        WebEngine webEngine = mapView.getEngine();
        URL url = getClass().getResource(WeatherApp.MAP_HTML_FILE);
        webEngine.load(url.toExternalForm() 
            + "?lat=" + WeatherApp.getState().getCoord().getLat() 
            + "&lon=" + WeatherApp.getState().getCoord().getLon()
            + "&apiKey=" + WeatherApp.API_KEY
        );

    }

    /**
     * Updates the location text size to smaller if it's width is over 400px
     */
    public void updateLocationTextSize() {
        if (locationText.getLayoutBounds().getWidth() > 400) {
            locationText.setStyle("-fx-font-size: 33px;");
        }
        else {
            locationText.setStyle("-fx-font-size: 45px;");
        }
    }

    /**
     * Initializes the UI Nodes with correct values and functionalities
     */
    @FXML
    public void initialize() {
        String location = WeatherApp.getState().getLocation();
        locationText.setText(location);
        ArrayList<String> favorites = WeatherApp.getFavorites();
        searchField.getItems().addAll(favorites);
        ArrayList<String> history = WeatherApp.getSearchHistory();
        for (String loc : history) {
            if (!favorites.contains(loc)) {
                searchField.getItems().add(loc);
            }
        }
        /* Favorite icon is added in front of the favorite city names in search 
        field menu */
        searchField.setCellFactory(p -> new ListCell<String>() {
            private final ImageView imView = new ImageView();
            
            @Override
            protected void updateItem(String favorite, boolean empty) {
                super.updateItem(favorite, empty);
                if (empty || favorite == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(favorite);
                    if (favorites.contains(favorite)) {
                        setGraphic(imView);
                        imView.setImage(favoritesIm);
                        imView.setFitHeight(20);
                        imView.setFitWidth(20);
                    } else {
                        setGraphic(null);
                    }
                }
            }
        });
        
        if (favorites.contains(location)) {
            favoritesImView.setImage(favoritesIm);
        }

        updateLocationTextSize();
        updateCurrent();
        initDaily();
        initHourly();
        loadMap();
    }
    
    /**
     * Class for interfacing with the day box defined with FXML
     * @author Oskari Heinonen
     */
    private class DayBox {

        private FXMLLoader loader;
        private DayBoxController controller;
        
        /**
         * Constructor for the DayBox class.
         * It initializes the FXMLLoader with the path to the day box's FXML file,
         * loads the FXML file, and gets the controller.
         */
        public DayBox() {
            loader = new FXMLLoader(getClass().getResource(WeatherApp.DAY_BOX_FILE));
            try {
                loader.load();
                controller = loader.getController();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Returns the root VBox of the loaded day box.
         * @return the root VBox
         */
        public VBox getBox() {
            return loader.getRoot();
        }

        /**
         * Returns the controller instance associated with the loaded day box.
         * @return controller instance of the box
         */
        public DayBoxController getController() {
            return controller;
        }
    }

    /**
     * This class serves as an interface for the hour box defined with FXML.
     * It loads the FXML file and provides access to the root VBox and the associated controller.
     * @author Oskari Heinonen
     */
    private class HourBox {

        private FXMLLoader loader;
        private HourBoxController controller;
        
        /**
         * Constructor for the HourBox class.
         * It initializes the FXMLLoader with the path to the hour box's FXML file,
         * loads the FXML file, and gets the controller.
         */
        public HourBox() {
            loader = new FXMLLoader(getClass().getResource(WeatherApp.HOUR_BOX_FILE));
            try {
                loader.load();
                controller = loader.getController();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * Returns the root VBox of the loaded hour box.
         * @return the root VBox
         */
        public VBox getBox() {
            return loader.getRoot();
        }

        /**
         * Returns the controller instance associated with the loaded hour box.
         * @return controller instance of the box
         */
        public HourBoxController getController() {
            return controller;
        }

    }

}
