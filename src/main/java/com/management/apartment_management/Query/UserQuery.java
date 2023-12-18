package com.management.apartment_management.Query;

import com.management.apartment_management.Models.Tenant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.management.apartment_management.Constants.DBConstants.*;

public class UserQuery {
    public static String getPassFromID(int tenant_id) {
        String pass = "";
        String SELECT_QUERY = "SELECT password FROM user WHERE tenant_id = ?";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenant_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pass = resultSet.getString("password");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving password of tenant ID: " + tenant_id, e);
        }
        return pass;
    }public static String getUsernameFromID(int tenant_id) {
        String username = "";
        String SELECT_QUERY = "SELECT username FROM user WHERE tenant_id = ?";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenant_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                username = resultSet.getString("username");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving password of tenant ID: " + tenant_id, e);
        }
        return username;
    }
    public static int updatePass(String pass, int tenant_id) {
        String UPDATE_QUERY = "UPDATE user SET password = ? WHERE tenant_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, pass);
            preparedStatement.setInt(2, tenant_id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update tenant id: " + tenant_id, e);
        }
    }
    public static int updateUsername(String username, int tenant_id) {
        String UPDATE_QUERY = "UPDATE user SET username = ? WHERE tenant_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, username);
            preparedStatement.setInt(2, tenant_id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update tenant id: " + tenant_id, e);
        }
    }
}
