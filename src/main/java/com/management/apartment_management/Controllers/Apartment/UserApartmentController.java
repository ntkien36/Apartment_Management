package com.management.apartment_management.Controllers.Apartment;
import com.management.apartment_management.Models.Apartment;
import com.management.apartment_management.Utils.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static com.management.apartment_management.Constants.FXMLConstants.PAYMENT_VIEW_FXML;
import static com.management.apartment_management.Constants.FXMLConstants.USER_APARTMENT_VIEW_FXML;

public class UserApartmentController implements Initializable  {
        public Apartment apartment;
        private final ViewUtils viewUtils = new ViewUtils();
        @FXML
        private AnchorPane basePane;

        @FXML
        private Text buildingText;

        @FXML
        private TextField endDateText;

        @FXML
        private Text numberText;

        @FXML
        private TextField rentText;

        @FXML
        private TextField sizeText;

        @FXML
        private TextField startDateText;
        private int tenantID;
        public void setTenantID(int tenantID) {
            this.tenantID = tenantID;
        }
        public void setApartment(Apartment apartment) {
            this.apartment = apartment;
        }
        public void setBuilding(String info) {buildingText.setText(info);}
        public void setStartDate(Date info) {startDateText.setText(String.valueOf(info));}
        public void setEndDate(Date info) {endDateText.setText(String.valueOf(info));}
        public void setNumber(String info){numberText.setText(info);}
        public void setSize(String info) {sizeText.setText(info);}
        public void setRent(String info){rentText.setText(info);}

        @Override
        public void initialize(URL location, ResourceBundle resources) {

        }
}

