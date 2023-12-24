package com.management.apartment_management.Query;

import com.management.apartment_management.Models.Contract;
import com.management.apartment_management.Models.Payment;
import com.management.apartment_management.Models.Tenant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.management.apartment_management.Constants.DBConstants.*;

public class ContractQuery {
    public static List<Contract> getNotYetPayment() {
        List<Contract> pays = new ArrayList<>();
        String SELECT_QUERY = "SELECT c.contract_id, c.payment_amount, c.end_date, c.notes, t.name\n" +
                "FROM contract c\n" +
                "JOIN tenant t ON c.tenant_id = t.tenant_id\n" +
                "WHERE c.contract_id NOT IN (SELECT contract_id FROM payment);";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
//            preparedStatement.setInt(1, ownerID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("contract_id");
                    String tenantName = resultSet.getString("name");
                    int amount = resultSet.getInt("payment_amount");
                    Date paymentDate = resultSet.getDate("end_date");
                    String  note = resultSet.getString("notes");
                    Contract p = new Contract(ID, tenantName, amount, paymentDate, note);
                    pays.add(p);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for not yet Payment: ", e);
        }
        return pays;
    }
    public static List<Contract> getContract() {
        List<Contract> contracts = new ArrayList<>();
        String SELECT_QUERY = "SELECT c.*, t.name AS tenant_name\n" +
                "FROM contract c\n" +
                "JOIN tenant t ON c.tenant_id = t.tenant_id;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
//            preparedStatement.setInt(1, ownerID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("contract_id");
                    String tenantName = resultSet.getString("tenant_name");
                    Date startDate = resultSet.getDate("start_date");
                    Date endDate = resultSet.getDate("end_date");
                    String  note = resultSet.getString("notes");
                    Contract p = new Contract(ID, tenantName, startDate, endDate, note);
                    contracts.add(p);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for contract: ", e);
        }
        return contracts;
    }
    public static int deleteContract(int contract_id) {
        String DELETE_CONTRACT_QUERY = "DELETE FROM contract WHERE contract_id = ?";
        String DELETE_PAYMENT_QUERY = "DELETE FROM payment WHERE contract_id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement deletePaymentStatement = conn.prepareStatement(DELETE_PAYMENT_QUERY);
             PreparedStatement deleteContractStatement = conn.prepareStatement(DELETE_CONTRACT_QUERY);
             ) {

            conn.setAutoCommit(false); // Set auto-commit to false to perform a transaction

            deletePaymentStatement.setInt(1, contract_id);
            int deletedPaymentRows = deletePaymentStatement.executeUpdate();

            deleteContractStatement.setInt(1, contract_id);
            int deletedContractRows = deleteContractStatement.executeUpdate();


            conn.commit(); // Commit the transaction

            return Math.max(deletedPaymentRows, deletedContractRows); // Return the minimum number of deleted rows

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting contract_id: " + contract_id, e);
        }
    }
    public static int updateAmount(Contract contract, String amount) {
        String UPDATE_QUERY = "UPDATE contract SET payment_amount = ? WHERE contract_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setInt(1, Integer.parseInt(amount));
            preparedStatement.setInt(2, contract.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update contract: " + contract, e);
        }
    }
    public static int updateNote(Contract contract, String note) {
        String UPDATE_QUERY = "UPDATE contract SET notes = ? WHERE contract_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, note);
            preparedStatement.setInt(2, contract.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update contract: " + contract, e);
        }
    }
    public static int updateStartTime(Contract contract, Date startDate) {
        String UPDATE_QUERY = "UPDATE contract SET start_date = ? WHERE contract_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setDate(1, startDate);
            preparedStatement.setInt(2, contract.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update contract: " + contract, e);
        }
    }
    public static int updateEndTime(Contract contract, Date endDate) {
        String UPDATE_QUERY = "UPDATE contract SET end_date = ? WHERE contract_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setDate(1, endDate);
            preparedStatement.setInt(2, contract.getId());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update contract: " + contract, e);
        }
    }
    public static int getAmountByContractID(int contractID) {
        int amount = 0;
        String SELECT_QUERY = "SELECT payment_amount from contract WHERE contract_id = ?";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, contractID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    amount = resultSet.getInt("payment_amount");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for contract ID: " + contractID, e);
        }
        return amount;
    }
    public static List<Contract> getNotYetPaymentByTenantID(int tenantID) {
        List<Contract> pays = new ArrayList<>();
        String SELECT_QUERY = "SELECT c.contract_id, c.payment_amount, c.end_date, c.notes\n" +
                "FROM contract c\n" +
                "JOIN tenant t ON c.tenant_id = t.tenant_id\n" +
                "WHERE t.tenant_id = ? AND c.contract_id NOT IN (SELECT contract_id FROM payment);";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenantID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("contract_id");
                    int amount = resultSet.getInt("payment_amount");
                    Date paymentDate = resultSet.getDate("end_date");
                    String  note = resultSet.getString("notes");
                    Contract p = new Contract(ID, note, amount, paymentDate);
                    pays.add(p);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for not yet Payment of tenant ID: " + tenantID, e);
        }
        return pays;
    }

}
