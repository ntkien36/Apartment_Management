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

    public static int addBuilding(Building building) throws SQLException {
        int maxBuildingId = 0;
        String INSERT_QUERY = "INSERT INTO building (building_id, name, address, total_apartment) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setInt(1, building.getId());
            preparedStatement.setString(2, building.getName());
            preparedStatement.setString(3, building.getAddress());
            preparedStatement.setInt(4, building.getTotal());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding building: " + building, e);
        }
    }
//        int maxBuildingId = 0;
//        String SELECT_QUERY = "SELECT MAX(user_id) FROM user";
//        try {
//            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//            PreparedStatement selectStatement = conn.prepareStatement(SELECT_QUERY);
//            ResultSet resultSet = selectStatement.executeQuery();
//            if (resultSet.next()) {
//                maxBuildingId = resultSet.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        String INSERT_QUERY = "INSERT INTO building (building_id, name, address, total_apartment) VALUES (?, ?, ?, ?)";
//        try {
//            int nextBuildingId = maxBuildingId + 1;
//            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
//            preparedStatement.setInt(1, nextBuildingId);
//            preparedStatement.setString(2, building.getName());
//            preparedStatement.setString(3, building.getAddress());
//            preparedStatement.setInt(4, building.getTotal());
//            return preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error adding building: " + building, e);
//        }
//    }
}

