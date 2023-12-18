package com.management.apartment_management.Controllers.Report;

import com.management.apartment_management.Models.Report;
import com.management.apartment_management.Query.ReportQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.management.apartment_management.Constants.DBConstants.*;

public class UserAddReportController implements Initializable {

    @FXML
    private TextArea desInput;

    @FXML
    private TextField nameInput;


    @FXML
    private Text stage;

    @FXML
    void addTenant(ActionEvent event) throws SQLException {
        int maxId = 0;
        String SELECT_QUERY = "SELECT MAX(report_id) FROM report";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement selectStatement = conn.prepareStatement(SELECT_QUERY);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                maxId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int nextId = maxId + 1;
        String name = nameInput.getText();
        String des = desInput.getText();
        String status = "DISAPPROVED";
        if (name == null || des == null) {
            stage.setText("Please fill all the fields");
        } else {
            Preferences pre = Preferences.userRoot();
            String tenantID = pre.get("tenant_id", "");
            Report report = new Report(nextId, name, des, status, Integer.parseInt(tenantID));
            int res = ReportQuery.addReport(report);
            if (res == 1) {
                stage.setText("Report added successfully!");
            } else {
                stage.setFill(javafx.scene.paint.Color.RED);
                stage.setText("Report added failed!");
            }
        }
    }
    public UserAddReportController(){
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

