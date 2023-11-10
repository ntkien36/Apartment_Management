package com.management.apartment_management;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import static com.management.apartment_management.Constants.FXMLConstants.HOME_VIEW_FXML;

public class HomeApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(HOME_VIEW_FXML));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Apartment Management");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}