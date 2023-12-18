package com.management.apartment_management.Controllers.Report;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ReportCardController {

    @FXML
    private Label infoLabel;

    @FXML
    private Label nameLabel;
    @FXML
    private Label statusLabel;

    @FXML
    public Button reportButton;

    public void setInfoLabel(String info) {
        infoLabel.setText(info);
    }
    public void setStatusLabel(String status) {
        statusLabel.setText(status);
    }

    public void setNameLabel(String info) {
        nameLabel.setText(info);
    }

    @FXML
    void openPetDetail(ActionEvent event) {
        System.out.println("Hello, click from report");
    }
}
