package com.management.apartment_management.Controllers.Apartment;

import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Models.Apartment;
//import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Query.ApartmentQuery;
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

import static com.management.apartment_management.Constants.FXMLConstants.APARTMENT_VIEW_FXML;

public class ApartmentDetailController implements Initializable {
    public Apartment apartment;
    private final ViewUtils viewUtils = new ViewUtils();
    @FXML
    public TextField apartmentIDText;

    @FXML
    public AnchorPane basePane;

    @FXML
    public TextField tenantNameText;

    @FXML
    public TextField tenantIDText;
    public TextField numberText;

    @FXML
    private TextField filter;

    @FXML
    public Text nameText;

    @FXML
    public TextField statusText;

    @FXML
    public TextField billText;

    @FXML
    public TextField startEndDateText;

    @FXML
    private Text log;

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
    public void setNumber(String info) {numberText.setText(info);}
    public void setTenantID(String info) {tenantIDText.setText(info);}
    public void setTenantName(String info){tenantNameText.setText(info);}
    public void setStartEndDate(String info) {startEndDateText.setText(info);}
    public void setBill(String info) {billText.setText(info);}
    public void setStatus(String info){statusText.setText(info);}


    @FXML
    void back(ActionEvent event) throws IOException {
        viewUtils.changeAnchorPane(basePane, APARTMENT_VIEW_FXML);
    }
    @FXML
    void onUpdate(ActionEvent event) {
//        String updatedType = this.typeChoice.getValue();
        String updatedNumber = this.numberText.getText();
//        String updatedTenantID = this.tenantIDText.getText();

        if(ApartmentQuery.updateNumber(this.apartment, updatedNumber) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(javafx.scene.paint.Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
//        if(ApartmentQuery.updateTenantID(this.apartment, updatedTenantID) == 1) {
//            this.log.setText("Update successfully!");
//            this.log.setFill(javafx.scene.paint.Color.GREEN);
//        } else {
//            this.log.setText("Update failed!");
//            this.log.setFill(Color.RED);
//        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}