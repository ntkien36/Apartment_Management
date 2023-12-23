package com.management.apartment_management.Controllers.Tenant;

import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Models.Tenant;
//import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Query.TenantQuery;
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

import static com.management.apartment_management.Constants.FXMLConstants.TENANT_VIEW_FXML;

public class DetailTenantController implements Initializable {
    public Tenant tenant;
    private final ViewUtils viewUtils = new ViewUtils();
    @FXML
    public TextField apartmentIDText;

    @FXML
    public AnchorPane basePane;

    @FXML
    public TextField buildingNameText;

    @FXML
    public TextField contactText;

    @FXML
    private TextField filter;

    @FXML
    public TextField nameText;

    @FXML
    public TextField numberText;

    @FXML
    public TextField startEndDateText;

    @FXML
    public TextField statusText;

    @FXML
    private Text log;

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
    public void setName(String info) {nameText.setText(info);}
    public void setContact(String info) {contactText.setText(info);}
    public void setStatus(String info) {statusText.setText(info);}
    public void setStartEndDate(String info) {startEndDateText.setText(info);}
    public void setApartmentID(String info) {apartmentIDText.setText(info);}
    public void setNumber(String info){numberText.setText(info);}
    public void setBuildingName(String info){buildingNameText.setText(info);}

    @FXML
    void back(ActionEvent event) throws IOException {
        viewUtils.changeAnchorPane(basePane, TENANT_VIEW_FXML);
    }
    @FXML
    void onUpdate(ActionEvent event) {
//        String updatedType = this.typeChoice.getValue();
        String updatedStatus = this.statusText.getText();
        String updatedcontact = this.contactText.getText();
        String updatedName = this.nameText.getText();

        if(TenantQuery.updateStatus(this.tenant, updatedStatus) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
        if(TenantQuery.updatecontact(this.tenant, updatedcontact) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
        if(TenantQuery.updateName(this.tenant, updatedName) == 1) {
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