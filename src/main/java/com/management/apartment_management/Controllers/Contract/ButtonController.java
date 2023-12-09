package com.management.apartment_management.Controllers.Contract;

import com.management.apartment_management.Controllers.Payment.DetailPaymentController;
import com.management.apartment_management.HomeApplication;
import com.management.apartment_management.Models.Contract;
import com.management.apartment_management.Query.ContractQuery;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

import static com.management.apartment_management.Constants.FXMLConstants.CONTRACT_DETAIL_VIEW_FXML;
import static com.management.apartment_management.Constants.FXMLConstants.PAYMENT_DETAIL_VIEW_FXML;
import static com.management.apartment_management.Utils.Utils.createDialog;

public class ButtonController {
    public static class DeleteButtonCell extends TableCell<Contract, Contract> {
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
                Contract contract = getTableRow().getItem();
                handleDeleteAction(contract);
            });
        }

        private void handleDeleteAction(Contract contract) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm delete contract");
            alert.setContentText("You want to delete this contract?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    int ID = contract.getId();
                    int res = ContractQuery.deleteContract(ID);
                    if (res == 1) {
                        createDialog(Alert.AlertType.WARNING, "Notification", "Delete successfully!", "");
                    } else {
                        createDialog(Alert.AlertType.WARNING, "Notification", "Error, try again later!", "");
                    }
                }
            });
        }

        @Override
        protected void updateItem(Contract item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }
    }

    public static class EditButtonCell extends TableCell<Contract, Contract> {
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
                Contract contract = getTableRow().getItem();
                handleEditAction(contract);
            });
        }

        private void handleEditAction(Contract contract) {
            FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(CONTRACT_DETAIL_VIEW_FXML));
        try {
//            Parent root = fxmlLoader.load();
            AnchorPane root = fxmlLoader.load();
            DetailContractController popupController = fxmlLoader.getController();
            popupController.setContract(contract);
            popupController.setName(contract.getTenantName());
            popupController.setStartDate(contract.getStartDate());
            popupController.setEndDate(contract.getEndDate());
            popupController.setAmount(Integer.toString(ContractQuery.getAmountByContractID(contract.getId())));
            popupController.setNote(contract.getNotes());

            AnchorPane parentPane = (AnchorPane) basePane.getParent();
            parentPane.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
        }

        @Override
        protected void updateItem(Contract item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(editButton);
            }
        }
    }
}
