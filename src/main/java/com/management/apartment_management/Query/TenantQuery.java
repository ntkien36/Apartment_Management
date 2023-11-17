package com.management.apartment_management.Query;

import com.management.apartment_management.Models.Tenant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.management.apartment_management.Constants.DBConstants.*;

public class TenantQuery {
    public static String getNameFromID(int tenant_id) {
        String name = "";
        String SELECT_QUERY = "SELECT name FROM tenant WHERE tenant_id = ?";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenant_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString("name");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving name of tenant ID: " + tenant_id, e);
        }
        return name;
    }
    public static List<Tenant> getTenant() {
        List<Tenant> pets = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM tenant";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
//            preparedStatement.setInt(1, ownerID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("tenant_id");
                    String name = resultSet.getString("name");
                    String contact = resultSet.getString("contact");
                    String status = resultSet.getString("status");
                    Tenant t = new Tenant(ID, name, contact, status);
                    pets.add(t);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: ", e);
        }
        return pets;
    }
}
