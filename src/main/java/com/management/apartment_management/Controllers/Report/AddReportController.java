package com.management.apartment_management.Controllers.Report;

import com.management.apartment_management.Models.Report;
import com.management.apartment_management.Query.ReportQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.management.apartment_management.Constants.DBConstants.*;

public class AddReportController implements Initializable {

    @FXML
    private TextField nameInput;

    @FXML
    private TextField descriptionInput;
    @FXML
    private TextField create_byInput;

    @FXML
    private Text stage;

    @FXML
    void addReport(ActionEvent event) throws SQLException {
        int maxReportId = 0;
        String SELECT_QUERY = "SELECT MAX(report_id) FROM report";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement selectStatement = conn.prepareStatement(SELECT_QUERY);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                maxReportId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int nextReportId = maxReportId + 1;
        String name1 = nameInput.getText();
        String description1 = descriptionInput.getText();
        String status = "DISAPPROVED";
        String create_by1 = create_byInput.getText();
        if (name1 == null || description1 == null || create_by1 == null) {
            stage.setText("Please fill all the fields");
        }
        else {
            int create_by = Integer.parseInt(create_by1);
            Report report = new Report(nextReportId, name1, description1, status, create_by);
            int res = ReportQuery.addReport(report);
            if (res == 1) {
                stage.setText("Report added successfully!");
            } else {
                stage.setFill(javafx.scene.paint.Color.RED);
                stage.setText("Report added failed!");
            }
        }
    }
    public AddReportController(){
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

