module fi.tuni.prog3.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.web;
    requires javafx.graphics;
    requires javafx.base;

    opens fi.tuni.prog3.weatherapp to javafx.fxml;

    exports fi.tuni.prog3.weatherapp;
}
