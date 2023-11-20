package com.management.apartment_management.Controllers.Building;

import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Query.BuildingQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.management.apartment_management.Constants.DBConstants.*;

public class AddBuildingController implements Initializable {

    @FXML
    private TextArea addrInput;

    @FXML
    private TextField nameInput;
    @FXML
    private TextField totalInput;

    @FXML
    private Text stage;

    @FXML
    void addBuilding(ActionEvent event) throws SQLException {
        int maxBuildingId = 0;
        String SELECT_QUERY = "SELECT MAX(building_id) FROM building";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement selectStatement = conn.prepareStatement(SELECT_QUERY);
            ResultSet resultSet = selectStatement.executeQuery();
            if (resultSet.next()) {
                maxBuildingId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int nextBuildingId = maxBuildingId + 1;
        String name = nameInput.getText();
        String addr = addrInput.getText();
        String str = totalInput.getText();
        int total = Integer.parseInt(str);
        if (name == null || addr == null || str == null) {
            stage.setText("Please fill all the fields");
        } else {
            Building building = new Building(nextBuildingId, name, addr, total);
            int res = BuildingQuery.addBuilding(building);
            if (res == 1) {
                stage.setText("Building added successfully!");
            } else {
                stage.setFill(javafx.scene.paint.Color.RED);
                stage.setText("Building added failed!");
            }
        }
    }
    public AddBuildingController(){
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

