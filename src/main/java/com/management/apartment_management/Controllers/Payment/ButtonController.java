package com.management.apartment_management.Controllers.Payment;

import com.management.apartment_management.HomeApplication;
import com.management.apartment_management.Models.Payment;
import com.management.apartment_management.Query.PaymentQuery;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static com.management.apartment_management.Constants.FXMLConstants.PAYMENT_DETAIL_VIEW_FXML;
import static com.management.apartment_management.Utils.Utils.createDialog;

public class ButtonController {
    public static class DeleteButtonCell extends TableCell<Payment, Payment> {
        private final Button deleteButton;

        public DeleteButtonCell() {
            deleteButton = new Button();
            ImageView deleteImage =new ImageView(new Image("C:/Users/LENOVO/Java/Apartment_Management/src/main/resources/com/management/apartment_management/icons/delete.png"));
            deleteImage.setFitWidth(15);
            deleteImage.setFitHeight(19);
            deleteButton.setGraphic(deleteImage);
            deleteButton.setStyle("-fx-background-color: #FFFFFF;");
            deleteButton.setOnAction(event -> {
                System.out.println("Mouse clicked on deleteImageView");
                Payment payment = getTableRow().getItem();
                handleDeleteAction(payment);
            });
        }

        private void handleDeleteAction(Payment payment) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm delete payment");
            alert.setContentText("You want to delete this payment?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    int ID = payment.getId();
                    int res = PaymentQuery.deletePayment(ID);
                    if (res == 1) {
                        createDialog(Alert.AlertType.WARNING, "Notification", "Delete successfully!", "");
                    } else {
                        createDialog(Alert.AlertType.WARNING, "Notification", "Error, try again later!", "");
                    }
                }
            });
        }

        @Override
        protected void updateItem(Payment item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }
    }

    public static class EditButtonCell extends TableCell<Payment, Payment> {
        private final Button editButton;
        private AnchorPane basePane;

        public EditButtonCell(AnchorPane basePane) {
            this.basePane = basePane;
            editButton = new Button();
            ImageView deleteImage =new ImageView(new Image("C:/Users/LENOVO/Java/Apartment_Management/src/main/resources/com/management/apartment_management/icons/edit-text.png"));
            deleteImage.setFitWidth(15);
            deleteImage.setFitHeight(19);
            editButton.setGraphic(deleteImage);
            editButton.setStyle("-fx-background-color: #FFFFFF;");
            editButton.setOnAction(event -> {
                Payment payment = getTableRow().getItem();
                handleEditAction(payment);
            });
        }

        private void handleEditAction(Payment payment) {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(PAYMENT_DETAIL_VIEW_FXML));
        try {
//            Parent root = fxmlLoader.load();
            AnchorPane root = fxmlLoader.load();
            DetailPaymentController popupController = fxmlLoader.getController();
            popupController.setPayment(payment);
            popupController.setName(payment.getTenantName());
            popupController.setStartDate(PaymentQuery.getStartDateByPaymentID(payment.getId()));
            popupController.setEndDate(PaymentQuery.getEndDateByPaymentID(payment.getId()));
            popupController.setPaymentDate(payment.getPaymentDate());
            popupController.setAmount(Integer.toString(payment.getAmount()));
            popupController.setNote(payment.getNote());

            AnchorPane parentPane = (AnchorPane) basePane.getParent();
            parentPane.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
        }

        @Override
        protected void updateItem(Payment item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(editButton);
            }
        }
    }
}
