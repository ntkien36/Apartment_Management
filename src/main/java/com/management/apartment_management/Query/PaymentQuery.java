package com.management.apartment_management.Query;

import com.management.apartment_management.Models.Payment;
import com.management.apartment_management.Models.Tenant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.management.apartment_management.Constants.DBConstants.*;

public class PaymentQuery {
//    public static String getNameFromID(int tenant_id) {
//        String name = "";
//        String SELECT_QUERY = "SELECT name FROM tenant WHERE tenant_id = ?";
//        try {
//            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
//            preparedStatement.setInt(1, tenant_id);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                name = resultSet.getString("name");
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving name of tenant ID: " + tenant_id, e);
//        }
//        return name;
//    }
    public static List<Payment> getPayment() {
        List<Payment> pays = new ArrayList<>();
        String SELECT_QUERY = "SELECT p.payment_id, p.amount, p.payment_date, c.notes, t.name\n" +
                "FROM payment p\n" +
                "JOIN contract c ON p.contract_id = c.contract_id\n" +
                "JOIN tenant t ON c.tenant_id = t.tenant_id\n" +
                "WHERE c.contract_id = p.contract_id;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
//            preparedStatement.setInt(1, ownerID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("payment_id");
                    String tenantName = resultSet.getString("name");
                    int amount = resultSet.getInt("amount");
                    Date paymentDate = resultSet.getDate("payment_date");
                    String  note = resultSet.getString("notes");
                    Payment p = new Payment(ID, tenantName, amount, paymentDate, note);
                    pays.add(p);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for Payment: ", e);
        }
        return pays;
    }
    public static int deletePayment(int payment_id){
        String DELETE_QUERY = "DELETE from payment WHERE payment_id = ? ";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_QUERY)) {
            preparedStatement.setInt(1, payment_id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error delete payment id: " + payment_id, e);
        }
    }
    public static int updateAmount(Payment payment, String amount) {
        String UPDATE_QUERY = "UPDATE payment SET amount = ? WHERE payment_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setInt(1, Integer.parseInt(amount));
            preparedStatement.setInt(2, payment.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update payment: " + payment, e);
        }
    }
    public static int updateNote(Payment payment, String note) {
        String UPDATE_QUERY = "UPDATE contract\n" +
                "SET notes = ?\n" +
                "WHERE contract_id IN (\n" +
                "    SELECT contract_id\n" +
                "    FROM payment\n" +
                "    WHERE payment_id = ?\n" +
                ");";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, note);
            preparedStatement.setInt(2, payment.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update payment: " + payment, e);
        }
    }
    public static int updatePaymentTime(Payment payment, Date paymentDate) {
        String UPDATE_QUERY = "UPDATE payment SET payment_date = ? WHERE payment_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setDate(1, paymentDate);
            preparedStatement.setInt(2, payment.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update payment: " + payment, e);
        }
    }
    public static String getStartDateByPaymentID(int paymentID) {
        String startDate = "";
        String SELECT_QUERY = "SELECT DATE_FORMAT(c.start_date, '%Y-%m-%d') AS start_date_string\n" +
                "FROM contract c\n" +
                "JOIN payment p ON c.contract_id = p.contract_id\n" +
                "WHERE p.payment_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, paymentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    startDate = resultSet.getString("start_date_string");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for payment ID: " + paymentID, e);
        }
        return startDate;
    }
    public static String getEndDateByPaymentID(int paymentID) {
        String endDate = "";
        String SELECT_QUERY = "SELECT DATE_FORMAT(c.end_date, '%Y-%m-%d') AS end_date_string\n" +
                "FROM contract c\n" +
                "JOIN payment p ON c.contract_id = p.contract_id\n" +
                "WHERE p.payment_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, paymentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    endDate = resultSet.getString("end_date_string");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for payment ID: " + paymentID, e);
        }
        return endDate;
    }
    public static List<Payment> getPaymentByTenantID(int tenantID) {
        List<Payment> pays = new ArrayList<>();
        String SELECT_QUERY = "SELECT p.payment_id, p.amount, p.payment_date, c.notes\n" +
                "                FROM payment p\n" +
                "                JOIN contract c ON p.contract_id = c.contract_id\n"  +
                "                JOIN tenant t ON c.tenant_id = t.tenant_id\n"  +
                "                WHERE t.tenant_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenantID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("payment_id");
                    int amount = resultSet.getInt("amount");
                    Date paymentDate = resultSet.getDate("payment_date");
                    String  note = resultSet.getString("notes");
                    Payment p = new Payment(ID, note, amount, paymentDate);
                    pays.add(p);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: " + tenantID, e);
        }
        return pays;
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
//    public static Integer getApartmentNumberByTenantID(int tenantID) {
//        int apartmentNumber = 0;
//        String SELECT_QUERY = "SELECT a.apartment_number \n" +
//                "FROM tenant t\n" +
//                "JOIN apartment a ON t.apartment_id = a.apartment_id\n" +
//                "WHERE tenant_id = ?;";
//        try {
//            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
//            preparedStatement.setInt(1, tenantID);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    apartmentNumber = resultSet.getInt("apartment_number");
//
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving for tenant ID: " + tenantID, e);
//        }
//        return apartmentNumber;
//    }
//
//    public static int addTenant(Tenant t) throws SQLException {
//        int maxBuildingId = 0;
//        String INSERT_QUERY = "INSERT INTO tenant (tenant_id, apartment_id, name, contact, status) VALUES (?, ?, ?, ?, ?)";
//        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)
//        ) {
//            preparedStatement.setInt(1, t.getId());
//            preparedStatement.setInt(2, t.getApartmentID());
//            preparedStatement.setString(3, t.getName());
//            preparedStatement.setString(4, t.getContact());
//            preparedStatement.setString(5,t.getStatus());
//            return preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error adding tenant: " + t, e);
//        }
//    }
//
//    public static int deleteTenant(int tenant_id) {
//        String DELETE_PAYMENT_QUERY = "DELETE FROM payment WHERE contract_id IN (SELECT contract_id FROM contract WHERE tenant_id = ?)";
//        String DELETE_CONTRACT_QUERY = "DELETE FROM contract WHERE tenant_id = ?";
//        String DELETE_TENANT_QUERY = "DELETE FROM tenant WHERE tenant_id = ?";
//
//        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//            PreparedStatement deletePaymentStatement = conn.prepareStatement(DELETE_PAYMENT_QUERY);
//            PreparedStatement deleteContractStatement = conn.prepareStatement(DELETE_CONTRACT_QUERY);
//            PreparedStatement deleteTenantStatement = conn.prepareStatement(DELETE_TENANT_QUERY)) {
//
//            conn.setAutoCommit(false); // Set auto-commit to false to perform a transaction
//
//            deletePaymentStatement.setInt(1, tenant_id);
//            int deletedPaymentRows = deletePaymentStatement.executeUpdate();
//
//            deleteContractStatement.setInt(1, tenant_id);
//            int deletedContractRows = deleteContractStatement.executeUpdate();
//
//            deleteTenantStatement.setInt(1, tenant_id);
//            int deletedTenantRows = deleteTenantStatement.executeUpdate();
//
//            conn.commit(); // Commit the transaction
//
//            return Math.max(Math.max(deletedPaymentRows, deletedContractRows), deletedTenantRows); // Return the minimum number of deleted rows
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error deleting tenant_id: " + tenant_id, e);
//        }
//    }

}
