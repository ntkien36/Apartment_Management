package com.management.apartment_management.Query;

import com.management.apartment_management.Models.Apartment;
import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Models.Tenant;

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
    public static List<Integer> getUnrentedApartmentID(){
        List<Integer> unrentedApartmentID = new ArrayList<>();
        String SELECT_QUERY = "SELECT a.apartment_id\n" +
                "FROM apartment a\n" +
                "LEFT JOIN tenant t ON a.apartment_id = t.apartment_id\n" +
                "WHERE t.tenant_id IS NULL;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("apartment_id");
                unrentedApartmentID.add(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving id of unrented apartment: ", e);
        }
        return unrentedApartmentID;
    }
    public static List<Apartment> getApartment() {
        List<Apartment> apartments = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM apartment";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
//            preparedStatement.setInt(1, ownerID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("apartment_id");
                    int building_id = resultSet.getInt("building_id");
                    int number = resultSet.getInt("apartment_number");
                    int size = resultSet.getInt("size");
                    int rent = resultSet.getInt("rent");
                    Apartment b = new Apartment(ID, building_id, number, size, rent);
                    apartments.add(b);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving apartments for apartment ID: ", e);
        }
        return apartments;
    }

    public static int addApartment(Apartment apartment) throws SQLException {
        String INSERT_QUERY = "INSERT INTO apartment (apartment_id, building_id, apartment_number, size, rent) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setInt(1, apartment.getID());
            preparedStatement.setInt(2, apartment.getBuildingID());
            preparedStatement.setInt(3, apartment.getNumber());
            preparedStatement.setInt(4, apartment.getSize());
            preparedStatement.setInt(5, apartment.getRent());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding apartment: " + apartment, e);
        }
    }

    public static int deleteApartment(int apartment_id) {
        String DELETE_TENANT_QUERY = "DELETE FROM tenant WHERE apartment_id = ?";
        String DELETE_APARTMENT_QUERY = "DELETE FROM apartment WHERE apartment_id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement deleteTenantStatement = conn.prepareStatement(DELETE_TENANT_QUERY);
             PreparedStatement deleteApartmentStatement = conn.prepareStatement(DELETE_APARTMENT_QUERY)) {

            conn.setAutoCommit(false); // Set auto-commit to false to perform a transaction

            deleteTenantStatement.setInt(1, apartment_id);
            int deletedTenantRows = deleteTenantStatement.executeUpdate();

            deleteApartmentStatement.setInt(1, apartment_id);
            int deletedApartmentRows = deleteApartmentStatement.executeUpdate();

            conn.commit(); // Commit the transaction

            return Math.max(deletedTenantRows, deletedApartmentRows); // Return the minimum number of deleted rows

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting apartment_id: " + apartment_id, e);
        }
    }
    public static int updateSize(Apartment apartment, String size) {
        String UPDATE_QUERY = "UPDATE apartment SET size = ? WHERE apartment_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, size);
            preparedStatement.setInt(2, apartment.getID());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update apartment: " + apartment, e);
        }
    }
    //    public static int updateTenantID(Apartment apartment, String tenantID) {
//        String UPDATE_QUERY = "UPDATE tenant SET tenant = ? WHERE apartment_id = ?";
//        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
//             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
//            preparedStatement.setString(1, tenantID);
//            preparedStatement.setInt(2, apartment.getID());
//            return preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            throw new RuntimeException("Error update apartment: " + apartment, e);
//        }
//    }
    public static int updateNumber(Apartment apartment, String number) {
        String UPDATE_QUERY = "UPDATE apartment SET apartment_number = ? WHERE apartment_id = ?";
        try (Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, number);
            preparedStatement.setInt(2, apartment.getID());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error update apartment: " + apartment, e);
        }
    }
    public static Integer getTenantIDByApartmentID(int apartmentID) {
        int tenantID = 0;
        String SELECT_QUERY = "SELECT t.tenant_id \n" +
                "FROM tenant as t\n" +
                "JOIN apartment as a ON t.apartment_id = a.apartment_id\n" +
                "WHERE a.apartment_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, apartmentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tenantID = resultSet.getInt("tenant_id");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: " + apartmentID, e);
        }
        return tenantID;
    }
    public static String getTenantNameByApartmentID(int apartmentID) {
        String tenantName="";
        String SELECT_QUERY = "SELECT t.name \n" +
                "FROM tenant as t \n" +
                "JOIN apartment as a ON a.apartment_id = t.apartment_id\n" +
                "WHERE a.apartment_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, apartmentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    tenantName = resultSet.getString("name");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: " + apartmentID, e);
        }
        return tenantName;
    }
    public static String getStatusByApartmentID(int apartmentID) {
        String status="";
        String SELECT_QUERY = "SELECT t.status \n" +
                "FROM tenant t\n" +
                "JOIN apartment a ON t.apartment_id = a.apartment_id\n" +
                "WHERE a.apartment_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, apartmentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    status = resultSet.getString("status");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: " + apartmentID, e);
        }
        return status;
    }
    public static String getStartEndDateByApartmentID(int apartmentID) {
        String startEndDate="";
        String SELECT_QUERY = "SELECT concat(c.start_date, ' ~ ', c.end_date) as startEndDate\n" +
                "FROM contract c\n" +
                "JOIN tenant t ON c.tenant_id = t.tenant_id\n" +
                "JOIN apartment a ON a.apartment_id = t.apartment_id\n" +
                "WHERE a.apartment_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, apartmentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    startEndDate = resultSet.getString("startEndDate");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: " + apartmentID, e);
        }
        return startEndDate;
    }
    public static String getBillByApartmentID(int apartmentID) {
        String bill="";
        String SELECT_QUERY =
//                "SELECT a.apartment_id \n" +
//                "FROM apartment a\n" +
//                "JOIN tenant t ON a.apartment_id = t.apartment_id\n" +
//                "WHERE apartment_id = ?;";
                "SELECT a.apartment_id,\n" +
                        "CASE\n" +
                        "WHEN COUNT(DISTINCT c.contract_id) = COUNT(DISTINCT p.contract_id) THEN 'complete'\n" +
                        "ELSE 'incomplete'\n" +
                        "END AS bill\n" +
                        "FROM\n" +
                        "apartment a\n" +
                        "LEFT JOIN tenant t ON a.apartment_id = t.apartment_id\n" +
                        "LEFT JOIN contract c ON t.tenant_id = c.tenant_id\n" +
                        "LEFT JOIN payment p ON c.contract_id = p.contract_id\n" +
                        "WHERE\n" +
                        "a.apartment_id = ?\n" +
                        "GROUP BY\n" +
                        "a.apartment_id;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, apartmentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    bill = resultSet.getString("bill");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for tenant ID: " + apartmentID, e);
        }
        return bill;
    }
    public static Apartment getApartmentByTenantID(int tenantID) {
        Apartment apm = new Apartment();
        String SELECT_QUERY = "SELECT *\n" +
                "FROM apartment\n" +
                "WHERE apartment_id = (\n" +
                "    SELECT apartment_id\n" +
                "    FROM tenant\n" +
                "    WHERE tenant_id = ?\n" +
                ");";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, tenantID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int ID = resultSet.getInt("apartment_id");
                    int number = resultSet.getInt("apartment_number");
                    int size = resultSet.getInt("size");
                    int rent = resultSet.getInt("rent");
                    int buildingID = resultSet.getInt("building_id");
                    apm = new Apartment(ID, number, size, rent, buildingID);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving pets for tenant ID: " + tenantID, e);
        }
        return apm;
    }
    public static String getBuildingNameByApartmentID(int apartmentID) {
        String building_name="";
        String SELECT_QUERY = "SELECT b.name\n" +
                "FROM building b\n" +
                "JOIN apartment a ON b.building_id = a.building_id\n" +
                "WHERE a.apartment_id = ?;";
        try {
            Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
            preparedStatement.setInt(1, apartmentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    building_name = resultSet.getString("name");

                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving for apartment ID: " + apartmentID, e);
        }
        return building_name;
    }
//

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
