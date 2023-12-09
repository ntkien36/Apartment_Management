package com.management.apartment_management.Controllers.Payment;

import com.management.apartment_management.Models.Payment;
import com.management.apartment_management.Query.PaymentQuery;
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

import static com.management.apartment_management.Constants.FXMLConstants.PAYMENT_VIEW_FXML;
import static com.management.apartment_management.Constants.FXMLConstants.TENANT_VIEW_FXML;

public class DetailPaymentController implements Initializable {
    public Payment payment;
    private final ViewUtils viewUtils = new ViewUtils();
    @FXML
    private TextField amountText;

    @FXML
    private AnchorPane basePane;

    @FXML
    private Text endDateText;

    @FXML
    private Text log;

    @FXML
    private Text nameText;

    @FXML
    private TextField noteText;

    @FXML
    private DatePicker paymentDate;

    @FXML
    private Text startDateText;


    public void setPayment(Payment payment) {
        this.payment = payment;
    }
    public void setName(String info) {nameText.setText(info);}
    public void setStartDate(String info) {startDateText.setText(info);}
    public void setEndDate(String info) {endDateText.setText(info);}
    public void setPaymentDate(Date info){paymentDate.setValue(info.toLocalDate());}
    public void setAmount(String info) {amountText.setText(info);}
    public void setNote(String info){noteText.setText(info);}

    @FXML
    void back(ActionEvent event) throws IOException {
        viewUtils.changeAnchorPane(basePane, PAYMENT_VIEW_FXML);
    }
    @FXML
    void onUpdate(ActionEvent event) {
//        String updatedType = this.typeChoice.getValue();
        String updatedAmount = this.amountText.getText();
        String updatedNote = this.noteText.getText();
        Date updatedPaymentTime = Date.valueOf(this.paymentDate.getValue());

        if(PaymentQuery.updateAmount(this.payment, updatedAmount) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
        if(PaymentQuery.updateNote(this.payment, updatedNote) == 1) {
            this.log.setText("Update successfully!");
            this.log.setFill(Color.GREEN);
        } else {
            this.log.setText("Update failed!");
            this.log.setFill(Color.RED);
        }
        if(PaymentQuery.updatePaymentTime(this.payment, updatedPaymentTime) == 1) {
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