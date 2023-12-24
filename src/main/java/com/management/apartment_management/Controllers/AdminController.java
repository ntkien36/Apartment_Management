package com.management.apartment_management.Controllers;

import com.management.apartment_management.Controllers.Report.UserReportController;
import com.management.apartment_management.Query.TenantQuery;
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
import static com.management.apartment_management.Constants.FXMLConstants.REPORT_VIEW_FXML;

public class AdminController implements Initializable {
    @FXML
    private AnchorPane basePane;

    @FXML
    private Button buttonApartment;

    @FXML
    private Button buttonBuilding;

    @FXML
    private Button buttonContract;

    @FXML
    private Button buttonPayment;

    @FXML
    private Button buttonReport;

    @FXML
    private Button buttonTenant;


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
    void switchToBuilding() throws IOException {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
//        if (role.equals("chunuoi")) {
//            viewUtils.changeAnchorPane(basePane, PET_VIEW_FXML);
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PET_VIEW_FXML));
//            Node node = fxmlLoader.load();
//            basePane.getChildren().setAll(node);
//            PetController petController = fxmlLoader.getController();
//            petController.setOwnerID(Integer.parseInt(pre.get("id", "")));
//            petController.petGrid();
//        }
//        if (role.equals("Tenant")) {
//            viewUtils.changeAnchorPane(basePane, BUILDING_VIEW_FXML);
//        }
        if (role.equals("Admin")) {
            viewUtils.changeAnchorPane(basePane, BUILDING_VIEW_FXML);
        }


    }
    @FXML
    void switchToApartment(ActionEvent event) throws IOException {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
//        if (role.equals("Tenant")) {
//            viewUtils.changeAnchorPane(basePane, APARTMENT_VIEW_FXML);
//        }
        if (role.equals("Admin")) {
            viewUtils.changeAnchorPane(basePane, APARTMENT_VIEW_FXML);
        }
    }


    @FXML
    void switchToContract(ActionEvent event) throws IOException {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
//        if (role.equals("Tenant")) {
//            viewUtils.changeAnchorPane(basePane, CONTRACT_VIEW_FXML);
//        }
        if (role.equals("Admin")) {
            viewUtils.changeAnchorPane(basePane, CONTRACT_VIEW_FXML);
        }
    }


    @FXML
    void switchToPayment(ActionEvent event) throws IOException {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
//        if (role.equals("Tenant")) {
//            viewUtils.changeAnchorPane(basePane, PAYMENT_VIEW_FXML);
//        }
        if (role.equals("Admin")) {
            viewUtils.changeAnchorPane(basePane, PAYMENT_VIEW_FXML);
        }
    }

    @FXML
    void switchToReport(ActionEvent event) throws IOException {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
//        if (role.equals("Tenant")) {
//            viewUtils.changeAnchorPane(basePane, REPORT_VIEW_FXML);
//        }
        if (role.equals("Admin")) {
            viewUtils.changeAnchorPane(basePane, REPORT_VIEW_FXML);
        }
    }

    @FXML
    void switchToTenant(ActionEvent event) throws IOException {
        Preferences pre = Preferences.userRoot();
        String role = pre.get("role", "");
//        if (role.equals("Tenant")) {
//            viewUtils.changeAnchorPane(basePane, TENANT_VIEW_FXML);
//        }
        if (role.equals("Admin")) {
            viewUtils.changeAnchorPane(basePane, TENANT_VIEW_FXML);
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
            int id = Integer.parseInt(pre.get("id", ""));
            usernameLabel.setText("" + TenantQuery.getNameFromID(id));
        }

        if (role.equals("Admin")) {
        }
    }
}
