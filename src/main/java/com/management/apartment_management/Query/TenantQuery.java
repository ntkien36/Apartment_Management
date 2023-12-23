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
        String SELECT_QUERY = "SELECT t.*, IFNULL(CONCAT(MIN(c.start_date), ' - ', MAX(c.end_date)), '') AS startEndDate\n" +
                "FROM tenant t\n" +
                "LEFT JOIN contract c ON t.tenant_id = c.tenant_id\n" +
                "GROUP BY t.tenant_id;";
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
                    int apartmentID = resultSet.getInt("apartment_id");
                    String  startEndDate = resultSet.getString("startEndDate");
                    Tenant t = new Tenant(ID, name, contact, status, apartmentID, startEndDate);
                    pets.add(t);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: ", e);
        }
        return pets;
    }

    public static Date getStartDateByTenantID(int tenantID) {
        Date start_date = null;
        String SELECT_QUERY = "SELECT MIN(start_date) AS earliest_start_date\n" +
                "FROM contract\n" +
                "WHERE tenant_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenantID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    start_date = resultSet.getDate("earliest_start_date");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: " + tenantID, e);
        }
        return start_date;
    }
    public static Date getEndDateByTenantID(int tenantID) {
        Date end_date = null;
        String SELECT_QUERY = "SELECT MAX(end_date) AS late_end_date\n" +
                "FROM contract\n" +
                "WHERE tenant_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenantID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    end_date = resultSet.getDate("late_end_date");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: " + tenantID, e);
        }
        return end_date;

    }
//    public static List<Tenant> getTenantDetailByTenantID(int tenantID) {
//        List<Tenant> tenants = new ArrayList<>();
//        String SELECT_QUERY = "SELECT t.*, a.apartment_number, b.name AS building_name, IFNULL(CONCAT(MIN(c.start_date), ' - ', MAX(c.end_date)), '') AS startEndDate\n" +
//                "FROM tenant t\n" +
//                "JOIN apartment a ON t.apartment_id = a.apartment_id\n" +
//                "JOIN building b ON a.building_id = b.building_id\n" +
//                "LEFT JOIN contract c ON t.tenant_id = c.tenant_id\n" +
//                "GROUP BY t.tenant_id WHERE tenant_id = ?;";
//        try {
//            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
//            preparedStatement.setInt(1, tenantID);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    int ID = resultSet.getInt("tenant_id");
//                    String name = resultSet.getString("name");
//                    String contact = resultSet.getString("contact");
//                    String status = resultSet.getString("status");
//                    int apartmentID = resultSet.getInt("apartment_id");
//                    String  startEndDate = resultSet.getString("startEndDate");
//                    int apartmentNumber = resultSet.getInt("apartment_number");
//                    int buildingName = resultSet.getInt("building_name");
//                    Tenant t = new Tenant(ID, name, contact, status, apartmentID, startEndDate, apartmentNumber, buildingName);
//                    tenants.add(t);
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving for tenant ID: " + tenantID, e);
//        }
//        return tenants;
//    }
    public static Integer getApartmentNumberByTenantID(int tenantID) {
        int apartmentNumber = 0;
        String SELECT_QUERY = "SELECT a.apartment_number \n" +
                "FROM tenant t\n" +
                "JOIN apartment a ON t.apartment_id = a.apartment_id\n" +
                "WHERE tenant_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenantID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    apartmentNumber = resultSet.getInt("apartment_number");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: " + tenantID, e);
        }
        return apartmentNumber;
    }
    public static String getBuildingNameByTenantID(int tenantID) {
        String buildingName = "";
        String SELECT_QUERY = "SELECT b.name AS building_name\n" +
                "FROM tenant t\n" +
                "JOIN apartment a ON t.apartment_id = a.apartment_id\n" +
                "JOIN building b ON a.building_id = b.building_id\n" +
                "WHERE tenant_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenantID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    buildingName = resultSet.getString("building_name");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: " + tenantID, e);
        }
        return buildingName;
    }
    public static int addTenant(Tenant t) throws SQLException {
        int maxBuildingId = 0;
        String INSERT_QUERY = "INSERT INTO tenant (tenant_id, apartment_id, name, contact, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)
        ) {
            preparedStatement.setInt(1, t.getId());
            preparedStatement.setInt(2, t.getApartmentID());
            preparedStatement.setString(3, t.getName());
            preparedStatement.setString(4, t.getContact());
            preparedStatement.setString(5,t.getStatus());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding tenant: " + t, e);
        }
    }
    public static int updateStatus(Tenant tenant, String status) {
        String UPDATE_QUERY = "UPDATE tenant SET status = ? WHERE tenant_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, tenant.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update tenant: " + tenant, e);
        }
    }
    public static int updateName(Tenant tenant, String name) {
        String UPDATE_QUERY = "UPDATE tenant SET name = ? WHERE tenant_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, tenant.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update tenant: " + tenant, e);
        }
    }
    public static int updatecontact(Tenant tenant, String contact) {
        String UPDATE_QUERY = "UPDATE tenant SET contact = ? WHERE tenant_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, contact);
            preparedStatement.setInt(2, tenant.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update tenant: " + tenant, e);
        }
    }
    public static int deleteTenant(int tenant_id) {
        String DELETE_PAYMENT_QUERY = "DELETE FROM payment WHERE contract_id IN (SELECT contract_id FROM contract WHERE tenant_id = ?)";
        String DELETE_CONTRACT_QUERY = "DELETE FROM contract WHERE tenant_id = ?";
        String DELETE_TENANT_QUERY = "DELETE FROM tenant WHERE tenant_id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement deletePaymentStatement = conn.prepareStatement(DELETE_PAYMENT_QUERY);
            PreparedStatement deleteContractStatement = conn.prepareStatement(DELETE_CONTRACT_QUERY);
            PreparedStatement deleteTenantStatement = conn.prepareStatement(DELETE_TENANT_QUERY)) {

            conn.setAutoCommit(false); // Set auto-commit to false to perform a transaction

            deletePaymentStatement.setInt(1, tenant_id);
            int deletedPaymentRows = deletePaymentStatement.executeUpdate();

            deleteContractStatement.setInt(1, tenant_id);
            int deletedContractRows = deleteContractStatement.executeUpdate();

            deleteTenantStatement.setInt(1, tenant_id);
            int deletedTenantRows = deleteTenantStatement.executeUpdate();

            conn.commit(); // Commit the transaction

            return Math.max(Math.max(deletedPaymentRows, deletedContractRows), deletedTenantRows); // Return the minimum number of deleted rows

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting tenant_id: " + tenant_id, e);
        }
    }

}
