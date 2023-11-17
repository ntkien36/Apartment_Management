package com.management.apartment_management.Query;
import com.management.apartment_management.Models.Building;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.management.apartment_management.Constants.DBConstants.*;

public class BuildingQuery {
    public static String getNameFromID(int building_id) {
        String name = "";
        String SELECT_QUERY = "SELECT name FROM Building WHERE building_id = ?";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, building_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString("name");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving name of owner ID: " + building_id, e);
        }
        return name;
    }
    public static List<Building> getBuilding() {
        List<Building> buildings = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM building";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
//            preparedStatement.setInt(1, ownerID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("building_id");
                    String name = resultSet.getString("name");
                    String address = resultSet.getString("address");
                    int total = resultSet.getInt("total_apartment");
                    Building b = new Building(ID, name, address, total);
                    buildings.add(b);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving buildings for building ID: ", e);
        }
        return buildings;
    }
}

