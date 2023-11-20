package com.management.apartment_management.Query;

import com.management.apartment_management.Models.Apartment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.management.apartment_management.Constants.DBConstants.*;

public class ApartmentQuery {
    public static List<Apartment> getApartmentsByBuildingID(int apartmentID) {
        List<Apartment> pets = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM apartment WHERE building_ID = ?";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, apartmentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("apartment_id");
                    int number = resultSet.getInt("apartment_number");
                    int size = resultSet.getInt("size");
                    int rent = resultSet.getInt("rent");
                    int buildingID = resultSet.getInt("building_id");
                    Apartment apm = new Apartment(ID, number, size, rent, buildingID);
                    pets.add(apm);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving apartments for building ID: " + apartmentID, e);
        }
        return pets;
    }
    public static int getNumberOfApartmentsByBuildingID(int buildingID) {
        int numberOfAPMs = 0;
        String SELECT_QUERY = "SELECT COUNT(*) AS count FROM apartment WHERE building_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY)) {
            preparedStatement.setInt(1, buildingID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    numberOfAPMs = resultSet.getInt("count");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving the number of apartments for building ID: " + buildingID, e);
        }
        return numberOfAPMs;
    }
//    public static int getNumberOfPets() {
//        int numberOfPets = 0;
//        String SELECT_QUERY = "SELECT COUNT(*) AS count FROM Pets";
//        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//             PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY)) {
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                if (resultSet.next()) {
//                    numberOfPets = resultSet.getInt("count");
//                }
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving the number of pets for owner ID: " + e);
//        }
//        return numberOfPets;
//    }
//
//    public static int addPet(Pet pet) {
//        String INSERT_QUERY = "INSERT INTO Pets (Name, Gender, Info, Owner_ID, Employee_ID) VALUES (?, ?, ?, ?, ?)";
//        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_QUERY)) {
//            preparedStatement.setString(1, pet.getName());
//            preparedStatement.setString(2, String.valueOf(pet.getGender()));
//            preparedStatement.setString(3, pet.getInfo());
//            preparedStatement.setInt(4, pet.getOwnerID());
//            preparedStatement.setInt(5, pet.getEmployeeID());
//            return preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error adding pet: " + pet, e);
//        }
//    }
//
//    public static int deletePet(Pet pet) {
//        String DELETE_QUERY = "DELETE from Pets WHERE id = ? ";
//        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//             PreparedStatement preparedStatement = conn.prepareStatement(DELETE_QUERY)) {
//            preparedStatement.setInt(1, pet.getID());
//            return preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error delete pet: " + pet, e);
//        }
//    }
//
//    public static int updateInfoPet(Pet pet, String info) {
//        String UPDATE_QUERY = "UPDATE Pets SET Info = ? WHERE id = ?";
//        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
//            preparedStatement.setString(1, info);
//            preparedStatement.setInt(2, pet.getID());
//            return preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error update pet: " + pet, e);
//        }
//    }
//
//    public static List<RecordOnPet> getRecordsOnPet(int petID) {
//        List<RecordOnPet> recordsOnPet = new ArrayList<>();
//        String SELECT_QUERY = "SELECT Name, Type, State, Date, Status\n" +
//                "FROM pets\n" +
//                "INNER JOIN record ON pets.ID = record.Pet_ID\n" +
//                "INNER JOIN service ON record.Service_ID = service.ID WHERE pets.ID = ? ;";
//        try {
//            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
//            preparedStatement.setInt(1, petID);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    String name = resultSet.getString("Name");
//                    Date date = resultSet.getDate("Date");
//                    String type = resultSet.getString("Type");
//                    String status = resultSet.getString("Status");
//                    String state = resultSet.getString("State");
//                    RecordOnPet recordOnPet = new RecordOnPet(name, date, type, status, state);
//                    recordsOnPet.add(recordOnPet);
//                }
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException("Error retrieving records on pet for pet ID: " + petID, e);
//        }
//        return recordsOnPet;
//    }
}
