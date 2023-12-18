package com.management.apartment_management.Controllers.Report;

import com.management.apartment_management.HomeApplication;
import com.management.apartment_management.Models.Report;
import com.management.apartment_management.Models.Tenant;
import com.management.apartment_management.Query.ReportQuery;
import com.management.apartment_management.Query.TenantQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static com.management.apartment_management.Constants.FXMLConstants.*;

public class UserReportController implements Initializable {

    @FXML
    private AnchorPane basePane;
//    @FXML
//    public Text title;

    @FXML
    private TextField search;

    @FXML
    private VBox vbox;
    @FXML
    private Pane pane;

    private int tenantID;
    public ObservableList<Report> reportList = FXCollections.observableArrayList();

    public void setTenantID(int tenantID) {
        this.tenantID = tenantID;
    }
    @FXML
    void search(){
        FilteredList<Report> filteredData = new FilteredList<>(reportList, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = search.getText().toLowerCase();
                if (person.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
            vbox.getChildren().clear();
            for(Report rp : filteredData) {
                String name = rp.getName();
                String des = rp.getDescription();
                String status = rp.getStatus();

                FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(REPORT_CARD_VIEW_FXML));
                try {
                    AnchorPane Card = fxmlLoader.load();
                    ReportCardController reportCard = fxmlLoader.getController();
                    reportCard.setInfoLabel("Info: "+ des);
//                    reportCard.reportButton.setOnMouseClicked(event -> handleReportCardClick(rp));
                    reportCard.setNameLabel(name);
                    reportCard.setStatusLabel(status);
                    vbox.getChildren().add(Card);
                    AnchorPane customPane = new AnchorPane();
                    customPane.setPrefHeight(3);
                    customPane.setStyle("-fx-background-color: #ffffff;");
                    vbox.getChildren().add(customPane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @FXML
    void add(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(USER_REPORT_ADD_VIEW_FXML));
        try {
            Parent root = fxmlLoader.load();
            UserAddReportController popupController = fxmlLoader.getController();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Add Report!");
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        reportVbox();
//            List<Report> rps = ReportQuery.getReportsByTenantID(tenantID);
//        reportList = FXCollections.observableArrayList(ReportQuery.getReportsByTenantID(tenantID));
//        System.out.println(reportList);
    }

    public void reportVbox() {
//        List<Report> rps = ReportQuery.getReportsByTenantID(tenantID);
//        reportList = FXCollections.observableArrayList(ReportQuery.getReportsByTenantID(tenantID));
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #ffffff;");
        scrollPane.setContent(vbox);
        for(Report rp : reportList) {
            String name = rp.getName();
            String des = rp.getDescription();
            String status = rp.getStatus();

            FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(REPORT_CARD_VIEW_FXML));
            try {
                AnchorPane Card = fxmlLoader.load();
                ReportCardController reportCard = fxmlLoader.getController();
                reportCard.setInfoLabel("Info: "+ des);
                reportCard.reportButton.setOnMouseClicked(event -> handleReportCardClick(rp));
                reportCard.setNameLabel(name);
                reportCard.setStatusLabel(status);
                vbox.getChildren().add(Card);
                AnchorPane customPane = new AnchorPane();
                customPane.setPrefHeight(3);
                customPane.setStyle("-fx-background-color: #ffffff;");
                vbox.getChildren().add(customPane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        scrollPane.setContent(vbox);
        pane.getChildren().add(scrollPane);
    }

    private void handleReportCardClick(Report report) {
//        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(PET_DETAIL_VIEW_FXML));
//        try {
//            Parent root = fxmlLoader.load();
//            PetDetailController popupController = fxmlLoader.getController();
//            popupController.setPet(pet);
//
//            popupController.setNameLabel(pet.getName());
//            popupController.setInfoLabel(pet.getInfo());
//            popupController.setTable();
//
//            Stage popupStage = new Stage();
//            popupStage.initModality(Modality.APPLICATION_MODAL);
//            popupStage.setTitle("Pet Detail");
//            popupStage.setScene(new Scene(root));
//            popupStage.showAndWait();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}


