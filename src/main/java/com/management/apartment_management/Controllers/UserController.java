package com.management.apartment_management.Controllers;

import com.management.apartment_management.Controllers.Apartment.UserApartmentController;
import com.management.apartment_management.Controllers.Payment.DetailPaymentController;
import com.management.apartment_management.Controllers.Payment.UserPaymentController;
import com.management.apartment_management.Controllers.Report.UserReportController;
import com.management.apartment_management.HomeApplication;
import com.management.apartment_management.Models.Apartment;
import com.management.apartment_management.Query.*;
import com.management.apartment_management.Utils.ViewUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.management.apartment_management.Constants.DBConstants.*;
import static com.management.apartment_management.Constants.FXMLConstants.*;

public class UserController implements Initializable {
    @FXML
    private AnchorPane basePane;

    @FXML
    private Button buttonApartment;

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
    private HBox statisticHbox;

    @FXML
    private HBox statisticHbox2;

    @FXML
    private Label usernameLabel;

    //Save user role
    private final ViewUtils viewUtils = new ViewUtils();
    private Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);

    public UserController() throws SQLException {
    }

    @FXML
    public void switchToDashboard(ActionEvent event) throws IOException {
        viewUtils.changeScene(event, USER_VIEW_FXML);
    }
    @FXML
    void switchToMyApartment(ActionEvent event) throws IOException {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
        if (role.equals("Tenant")) {
            viewUtils.changeAnchorPane(basePane, USER_APARTMENT_VIEW_FXML);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(USER_APARTMENT_VIEW_FXML));
            Node node = fxmlLoader.load();
            basePane.getChildren().setAll(node);
            UserApartmentController popupController = fxmlLoader.getController();
            Apartment apartment = ApartmentQuery.getApartmentByTenantID(Integer.parseInt(pre.get("tenant_id", "")));
            popupController.setApartment(apartment);
//                popupController.setApartment(apartment);
                popupController.setBuilding(ApartmentQuery.getBuildingNameByApartmentID(apartment.getID()));
                popupController.setStartDate(TenantQuery.getStartDateByTenantID(Integer.parseInt(pre.get("tenant_id", ""))));
                popupController.setEndDate(TenantQuery.getEndDateByTenantID(Integer.parseInt(pre.get("tenant_id", ""))));
            popupController.setNumber(Integer.toString(apartment.getNumber()));
            popupController.setSize(Integer.toString(apartment.getSize()));
            popupController.setRent(Integer.toString(apartment.getRent()));

        }
    }

    @FXML
    void switchToPayment(ActionEvent event) throws IOException {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
        if (role.equals("Tenant")) {
            viewUtils.changeAnchorPane(basePane, USER_PAYMENT_VIEW_FXML);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(USER_PAYMENT_VIEW_FXML));
            Node node = fxmlLoader.load();
            basePane.getChildren().setAll(node);
            UserPaymentController popupController = fxmlLoader.getController();
        }
    }

    @FXML
    void switchToReport(ActionEvent event) throws IOException {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
        if (role.equals("Tenant")) {
            viewUtils.changeAnchorPane(basePane, USER_REPORT_VIEW_FXML);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(USER_REPORT_VIEW_FXML));
            Node node = fxmlLoader.load();
            basePane.getChildren().setAll(node);
            UserReportController reportController = fxmlLoader.getController();
            String tenantID = pre.get("tenant_id", "");
            System.out.println(tenantID);
            reportController.setTenantID(Integer.parseInt(tenantID));
            reportController.reportList = FXCollections.observableArrayList(ReportQuery.getReportsByTenantID(Integer.parseInt(tenantID)));
            reportController.reportVbox();
        }
    }

    @FXML
    void switchToSetting(ActionEvent event) throws IOException {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
        int id = Integer.parseInt(pre.get("tenant_id", ""));
        if (role.equals("Tenant")) {
            viewUtils.changeAnchorPane(basePane, USER_SETTING_VIEW_FXML);
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(USER_SETTING_VIEW_FXML));
            Node node = fxmlLoader.load();
            basePane.getChildren().setAll(node);
            SettingController popupController = fxmlLoader.getController();
            popupController.setPassword(UserQuery.getPassFromID(id));
            popupController.setUsername(UserQuery.getUsernameFromID(id));
        }
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
            int id = Integer.parseInt(pre.get("tenant_id", ""));
            System.out.println(id);
            usernameLabel.setText("" + TenantQuery.getNameFromID(id));
        }
    }
}
