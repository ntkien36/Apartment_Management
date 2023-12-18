package com.management.apartment_management.Query;

import com.management.apartment_management.Models.Report;
import com.management.apartment_management.Models.Tenant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.management.apartment_management.Constants.DBConstants.*;

public class ReportQuery {
    public static List<Report> getReportsByTenantID(int tenantID) {
        List<Report> rps = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM report WHERE create_by = ?";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenantID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("report_id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    String status = resultSet.getString("status");
//                    int tenant_id = resultSet.getInt("create_by");
                    Report rp = new Report(ID, name, description, status);
                    rps.add(rp);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving pets for tenant ID: " + tenantID, e);
        }
        return rps;
    }
    public static int addReport(Report r) throws SQLException {
        int maxBuildingId = 0;
        String INSERT_QUERY = "INSERT INTO report (report_id, name, description, status, create_by) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setInt(1, r.getId());
            preparedStatement.setString(2, r.getName());
            preparedStatement.setString(3, r.getDescription());
            preparedStatement.setString(4, r.getStatus());
            preparedStatement.setInt(5,r.getCreate_by());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding report: " + r, e);
        }
    }

}
