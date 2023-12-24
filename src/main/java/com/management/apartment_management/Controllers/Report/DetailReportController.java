package com.management.apartment_management.Controllers.Report;

import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Models.Report;
//import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Query.ReportQuery;
import com.management.apartment_management.Utils.ViewUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static com.management.apartment_management.Constants.FXMLConstants.REPORT_VIEW_FXML;

public class DetailReportController implements Initializable {
    public Report report;
    private final ViewUtils viewUtils = new ViewUtils();


    @FXML
    public AnchorPane basePane;

    @FXML
    public TextField nameText;

    @FXML
    public TextArea descriptionText;

    @FXML
    public TextField startEndDateText;

    @FXML
    public TextField statusText;

    @FXML
    private Text log;

    public void setReport(Report report) {
        this.report = report;
    }
    public void setName(String info) {nameText.setText(info);}
    public void setDescription(String info) {
        descriptionText.setWrapText(true);
        descriptionText.setText(info);
    }
    public void setStatus(String info) {statusText.setText(info);}

    @FXML
    void back(ActionEvent event) throws IOException {
        viewUtils.changeAnchorPane(basePane, REPORT_VIEW_FXML);
    }
    @FXML
    void onUpdate(ActionEvent event) {
//        String updatedType = this.typeChoice.getValue();
        String updatedStatus = this.statusText.getText();

        if(ReportQuery.updateStatus(this.report, updatedStatus) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}