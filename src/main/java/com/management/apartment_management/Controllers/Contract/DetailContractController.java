package com.management.apartment_management.Controllers.Contract;

import com.management.apartment_management.Models.Contract;
import com.management.apartment_management.Query.ContractQuery;
import com.management.apartment_management.Utils.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static com.management.apartment_management.Constants.FXMLConstants.CONTRACT_VIEW_FXML;
import static com.management.apartment_management.Constants.FXMLConstants.PAYMENT_VIEW_FXML;

public class DetailContractController implements Initializable {
    public Contract contract;
    private final ViewUtils viewUtils = new ViewUtils();
    @FXML
    private TextField amountText;

    @FXML
    private AnchorPane basePane;


    @FXML
    private Text log;

    @FXML
    private Text nameText;

    @FXML
    private TextField noteText;

    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;



    public void setContract(Contract contract) {
        this.contract = contract;
    }
    public void setName(String info) {nameText.setText(info);}
    public void setStartDate(Date info) {startDate.setValue(info.toLocalDate());}
    public void setEndDate(Date info){endDate.setValue(info.toLocalDate());}
    public void setAmount(String info) {amountText.setText(info);}
    public void setNote(String info){noteText.setText(info);}

    @FXML
    void back(ActionEvent event) throws IOException {
        viewUtils.changeAnchorPane(basePane, CONTRACT_VIEW_FXML);
    }
    @FXML
    void onUpdate(ActionEvent event) {
//        String updatedType = this.typeChoice.getValue();
        String updatedAmount = this.amountText.getText();
        String updatedNote = this.noteText.getText();
        Date updatedStartTime = Date.valueOf(this.startDate.getValue());
        Date updatedEndTime = Date.valueOf(this.endDate.getValue());

        if(ContractQuery.updateAmount(this.contract, updatedAmount) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
        if(ContractQuery.updateNote(this.contract, updatedNote) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
        if(ContractQuery.updateStartTime(this.contract, updatedStartTime) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
        if(ContractQuery.updateEndTime(this.contract, updatedEndTime) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}