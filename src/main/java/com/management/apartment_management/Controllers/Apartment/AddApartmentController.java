package com.management.apartment_management.Controllers.Apartment;

import com.management.apartment_management.Models.Apartment;
import com.management.apartment_management.Query.ApartmentQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.management.apartment_management.Constants.DBConstants.*;

public class AddApartmentController implements Initializable {

    @FXML
    private TextField numberInput;

    @FXML
    private TextField buildingIDInput;
    @FXML
    private TextField rentInput;

    @FXML
    private TextField sizeInput;

    @FXML
    private Text stage;

    @FXML
    void addApartment(ActionEvent event) throws SQLException {
        int maxApartmentId = 0;
        String SELECT_QUERY = "SELECT MAX(apartment_id) FROM apartment";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement selectStatement = conn.prepareStatement(SELECT_QUERY);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                maxApartmentId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int nextApartmentId = maxApartmentId + 1;
        String buildingID1 = buildingIDInput.getText();
        String number1 = numberInput.getText();
        String size1 = sizeInput.getText();
        String rent1 = rentInput.getText();
        if (buildingID1 == null || number1 == null || rent1 == null || size1 == null) {
            stage.setText("Please fill all the fields");
        }
        else {
            int buildingID = Integer.parseInt(buildingID1);
            int number = Integer.parseInt(number1);
            int size = Integer.parseInt(size1);
            int rent = Integer.parseInt(rent1);
            Apartment apartment = new Apartment(nextApartmentId, buildingID, number, size, rent);
            int res = ApartmentQuery.addApartment(apartment);
            if (res == 1) {
                stage.setText("Apartment added successfully!");
            } else {
                stage.setFill(javafx.scene.paint.Color.RED);
                stage.setText("Apartment added failed!");
            }
        }
    }
    public AddApartmentController(){
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

