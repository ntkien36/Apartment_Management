package com.management.apartment_management.Controllers;

import com.management.apartment_management.Models.Apartment;
import com.management.apartment_management.Models.Tenant;
import com.management.apartment_management.Query.TenantQuery;
import com.management.apartment_management.Query.UserQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.sql.Date;
import java.util.prefs.Preferences;

public class SettingController {
    @FXML
    private AnchorPane basePane;

    @FXML
    private Label contact;

    @FXML
    private Text number;

    @FXML
    private Button passButton;

    @FXML
    private PasswordField passText;

    @FXML
    private Button userButton;
    @FXML
    private Text log;

    @FXML
    private TextField usernameText;
    Preferences pre = Preferences.userRoot();
    int id = Integer.parseInt(pre.get("tenant_id", ""));
    public void setPassword(String info) {
        passText.setText(info);
    }
    public void setUsername(String info) {usernameText.setText(info);}
    @FXML
    void onUpdate(ActionEvent event) {
        String updatedPass = this.passText.getText();
        String updatedUsername = this.usernameText.getText();
        if (UserQuery.updatePass(updatedPass, id) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
        if (UserQuery.updateUsername(updatedUsername, id) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
    }
    @FXML
    void updatePass(ActionEvent event) {
        String updatedPass = this.passText.getText();
        if (UserQuery.updatePass(updatedPass, id) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
    }

    @FXML
    void updateUsername(ActionEvent event) {
        String updatedUsername = this.usernameText.getText();
        if (UserQuery.updateUsername(updatedUsername, id) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
    }

}
