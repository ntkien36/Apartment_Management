package com.management.apartment_management.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import com.management.apartment_management.Utils.ViewUtils;

import static com.management.apartment_management.Constants.DBConstants.*;
import static com.management.apartment_management.Constants.FXMLConstants.*;

public class AdminController implements Initializable {
    @FXML
    private AnchorPane basePane;

    @FXML
    private Button buttonAppartment;

    @FXML
    private Button buttonNotification;

    @FXML
    private Button buttonPayment;

    @FXML
    private Button buttonReport;

    @FXML
    private Button buttonSetting;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private HBox medicalHbox;

    @FXML
    private HBox statisticHbox;

    @FXML
    private HBox statisticHbox1;

    @FXML
    private Label usernameLabel;

    //Save user role
    private final ViewUtils viewUtils = new ViewUtils();
    private Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);

    public AdminController() throws SQLException {
    }

    @FXML
    public void switchToDashboard(ActionEvent event) throws IOException {
        viewUtils.changeScene(event, ADMIN_VIEW_FXML);
    }
    @FXML
    void switchToAppartment(ActionEvent event) {

    }

    @FXML
    void switchToNotification(ActionEvent event) {

    }

    @FXML
    void switchToPayment(ActionEvent event) {

    }

    @FXML
    void switchToReport(ActionEvent event) {

    }

    @FXML
    void switchToSetting(ActionEvent event) {

    }
    @FXML
    void logout(ActionEvent event) throws IOException {
        viewUtils.changeScene(event, HOME_VIEW_FXML);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
        if (role.equals("Tenant")) {
        }

        if (role.equals("Admin")) {

        }
    }
}
