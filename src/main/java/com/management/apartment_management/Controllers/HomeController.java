package com.management.apartment_management.Controllers;
//import libs

import com.management.apartment_management.HomeApplication;
import com.management.apartment_management.Utils.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Optional;
import java.util.prefs.Preferences;

import static com.management.apartment_management.Constants.DBConstants.*;
import static com.management.apartment_management.Constants.FXMLConstants.*;

public class HomeController {
    @FXML
    private TextField inputUsername, inputPassword;

    public void handleLogin(ActionEvent event) {
        String SELECT_QUERY = "SELECT * FROM user WHERE username = ? AND password = ?";
        String Username = inputUsername.getText();
        String Password = inputPassword.getText();
        if (Username.trim().equals("") || Password.trim().equals("")) {
            ButtonType foo = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            ButtonType bar = new ButtonType("Tạo tài khoản mới", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Khoan nào bro!",
                    foo,
                    bar);

            alert.setContentText("Chưa nhập username hoặc password");

            alert.setTitle("Khoan nào bro!");
            Optional<ButtonType> result1 = alert.showAndWait();

            if (result1.orElse(bar) == foo) {
                //System.out.println("Foo");
            }
            if (result1.orElse(bar) == bar) {
//                        viewUtils.changeScene(event, SIGNUP_VIEW_FXML);
                loadSignUpFXML();
            }
        }   else {
            try {
                //Khai bao ket noi sql
                Connection conn = DriverManager.getConnection(DATABASE, USERNAME, PASSWORD);
                PreparedStatement preparedStatement = conn.prepareStatement(SELECT_QUERY);
                preparedStatement.setString(1, Username);
                preparedStatement.setString(2, Password);
                ResultSet result = preparedStatement.executeQuery();
                ViewUtils viewUtils = new ViewUtils();

                if (result.next()) {
                    Preferences userPreferences = Preferences.userRoot();
                    userPreferences.put("role", result.getString(4));
                    userPreferences.put("username", result.getString(2));
                    userPreferences.put("user_id", result.getString(1));
                    userPreferences.put("tenant_id", result.getString(5));
                    String role = userPreferences.get("role", "");
                    if (role.equals("Admin")) {
                        viewUtils.changeScene(event, ADMIN_VIEW_FXML);
                    }
                    if (role.equals("Tenant")) {
                        viewUtils.changeScene(event, USER_VIEW_FXML);
                    }
//                    viewUtils.changeScene(event, ADMIN_VIEW_FXML);
                }   else {
//                    createDialog(
//                            Alert.AlertType.ERROR,
//                            "Cảnh báo!",
//                            "Khoan nào bro!",
//                            "Sai username hoặc password!"
//                    );
                    ButtonType foo = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    ButtonType bar = new ButtonType("Tạo tài khoản mới", ButtonBar.ButtonData.CANCEL_CLOSE);
                    Alert alert = new Alert(Alert.AlertType.ERROR,
                            "Khoan nào bro!",
                            foo,
                            bar);

                    alert.setContentText("Sai username hoặc password");
                    alert.setTitle("Khoan nào bro!");
                    Optional<ButtonType> result1 = alert.showAndWait();

                    if (result1.orElse(bar) == foo) {
                        //System.out.println("Foo");
                    }
                    if (result1.orElse(bar) == bar) {
//                        viewUtils.changeScene(event, SIGNUP_VIEW_FXML);
                        loadSignUpFXML();
                    }

                }
            }   catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void signUp(MouseEvent mouseEvent) {
        Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        currentStage.close();
        loadSignUpFXML();
    }

    private void loadSignUpFXML() {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(SIGNUP_VIEW_FXML));
        try {
            Parent root = fxmlLoader.load();
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Đăng ký tài khoản mới");
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}