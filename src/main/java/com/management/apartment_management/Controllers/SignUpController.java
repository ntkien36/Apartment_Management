package com.management.apartment_management.Controllers;

import com.management.apartment_management.HomeApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ResourceBundle;




import static com.management.apartment_management.Constants.DBConstants.*;
import static com.management.apartment_management.Constants.FXMLConstants.HOME_VIEW_FXML;
import static com.management.apartment_management.Constants.FXMLConstants.SIGNUP_VIEW_FXML;
import static com.management.apartment_management.Utils.Utils.createDialog;

public class SignUpController implements Initializable {
    @FXML
    private TextField signUpUsername, signUpPassword, ReSignUpPassword;
    private final ToggleGroup toggleRole = new ToggleGroup();

    public void handleSignUp() {
        String SELECT_QUERY = "SELECT MAX(user_id) FROM user";
        String TENANT_QUERY = "SELECT MAX(tenant_id) FROM tenant";
        String inputUsername = signUpUsername.getText();
        String inputPassword = signUpPassword.getText();
        String reInputPassword = ReSignUpPassword.getText();
        String role = "";

        if (inputUsername.trim().equals("") || inputPassword.trim().equals("") || reInputPassword.trim().equals("")) {
            createDialog(
                    Alert.AlertType.WARNING,
                    "Khoan nào cán bộ",
                    "", "Vui lòng nhập đủ tài khoản và mật khẩu!"
            );

        } else {
            int maxUserId = 0;
            try {
                Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
                PreparedStatement selectStatement = conn.prepareStatement(SELECT_QUERY);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    maxUserId = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int maxTenantId = 0;
            try {
                Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
                PreparedStatement selectStatement = conn.prepareStatement(TENANT_QUERY);
                ResultSet resultSet = selectStatement.executeQuery();
                if (resultSet.next()) {
                    maxTenantId = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String CREATE_QUERY = "INSERT INTO user (username, password, role, user_id, tenant_id) VALUES (?,?,?,?,?);";
            String CREATE_QUERY_1 = "INSERT INTO tenant (tenant_id) VALUES (?);";
            try {
                int nextUserId = maxUserId + 1;
                int nextTenantId = maxTenantId +1;
                Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(CREATE_QUERY);
                PreparedStatement preparedStatement1 = conn.prepareStatement(CREATE_QUERY_1);
                preparedStatement.setString(1, inputUsername);
                preparedStatement.setString(2, inputPassword);
                preparedStatement.setString(3, "Tenant");
                preparedStatement.setInt(4, nextUserId);
                preparedStatement.setInt(5, nextTenantId);
                preparedStatement1.setInt(1, nextTenantId);
                int result = Math.max(preparedStatement.executeUpdate(), preparedStatement1.executeUpdate());
                if (result == 1) {
                    signUpPassword.clear();
                    signUpUsername.clear();
                    createDialog(
                            Alert.AlertType.CONFIRMATION,
                            "Thành công",
                            "", "Đăng ký người dùng mới thành công!"
                    );
                } else {
                    createDialog(
                            Alert.AlertType.ERROR,
                            "Thất bại",
                            "", "Đăng ký người dùng mới thất bại!"
                    );
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML
    void signIn(MouseEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(HOME_VIEW_FXML));
        try {
//            Parent root = fxmlLoader.load();
            Stage popupStage = new Stage();
//            popupStage.initModality(Modality.APPLICATION_MODAL);
//            //popupStage.setTitle("Apartment Management");
//            popupStage.setScene(new Scene(root));
//            popupStage.showAndWait();
            Scene scene = new Scene(fxmlLoader.load());
            popupStage.setTitle("Apartment Management");
            popupStage.setScene(scene);
            popupStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
