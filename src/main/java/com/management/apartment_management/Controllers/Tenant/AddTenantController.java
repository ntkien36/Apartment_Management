package com.management.apartment_management.Controllers.Tenant;

import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Models.Tenant;
import com.management.apartment_management.Query.ApartmentQuery;
import com.management.apartment_management.Query.TenantQuery;

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

import static com.management.apartment_management.Constants.DBConstants.*;
import static com.management.apartment_management.Query.ApartmentQuery.getUnrentedApartmentID;

public class AddTenantController implements Initializable {

    @FXML
    private TextArea contactInput;

    @FXML
    private TextField nameInput;
    @FXML
    private TextField statusInput;

    @FXML
    private Text stage;
    @FXML
    private ChoiceBox<Integer> apartmentChoiceBox;

    @FXML
    void addTenant(ActionEvent event) throws SQLException {
        int maxTenantId = 0;
        String SELECT_QUERY = "SELECT MAX(tenant_id) FROM tenant";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement selectStatement = conn.prepareStatement(SELECT_QUERY);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                maxTenantId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int nextTenantId = maxTenantId + 1;
        String name = nameInput.getText();
        String contact = contactInput.getText();
        String status = statusInput.getText();
        int apartmentID = apartmentChoiceBox.getValue();
        String str = String.valueOf(apartmentID);
        if (name == null || contact == null || str == null) {
            stage.setText("Please fill all the fields");
        } else {
            Tenant tenant = new Tenant(nextTenantId, name, contact, status, apartmentID);
            int res = TenantQuery.addTenant(tenant);
            if (res == 1) {
                stage.setText("Tenant added successfully!");
            } else {
                stage.setFill(javafx.scene.paint.Color.RED);
                stage.setText("Tenant added failed!");
            }
        }
    }
    public AddTenantController(){
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<Integer> apartmentIDList = FXCollections.observableArrayList(getUnrentedApartmentID());
        apartmentChoiceBox.setItems(apartmentIDList);
        apartmentChoiceBox.getSelectionModel().selectFirst();
    }
}

