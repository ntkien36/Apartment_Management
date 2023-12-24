package com.management.apartment_management.Controllers.Apartment;

import com.management.apartment_management.HomeApplication;
import com.management.apartment_management.Models.Apartment;
import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Models.Tenant;
import com.management.apartment_management.Query.ApartmentQuery;
import com.management.apartment_management.Query.BuildingQuery;
import com.management.apartment_management.Controllers.Building.DetailBuildingController;
import com.management.apartment_management.Query.TenantQuery;
import com.management.apartment_management.Utils.ViewUtils;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.management.apartment_management.Constants.FXMLConstants.*;
import static com.management.apartment_management.Constants.FXMLConstants.APARTMENT_DETAIL_VIEW_FXML;
import static com.management.apartment_management.Utils.Utils.createDialog;

public class ApartmentController implements Initializable {

    private final ViewUtils viewUtils = new ViewUtils();

    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn<Apartment, Integer> buildingID ,number, size, rent;
//    @FXML
//    private TableColumn<Apartment, String> buildingname;

    @FXML
    private TableColumn indexColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private TextField filter;

    @FXML
    private TextField search;

    @FXML
    private TableView<Apartment> table;
    public Preferences pre = Preferences.userRoot();

    @FXML
    void add(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(APARTMENT_ADD_FXML));
        try {
            Parent root = fxmlLoader.load();
            AddApartmentController popupController = fxmlLoader.getController();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("New Apartment!!!");
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ObservableList<Apartment> apartmentList = FXCollections.observableArrayList();
    private static final int ROWS_PER_PAGE = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        apartmentList = FXCollections.observableArrayList(ApartmentQuery.getApartment());

        int soDu = apartmentList.size() % ROWS_PER_PAGE;
        if (soDu != 0) pagination.setPageCount(apartmentList.size() / ROWS_PER_PAGE + 1);
        else pagination.setPageCount(apartmentList.size() / ROWS_PER_PAGE);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createTableView);

        table.setRowFactory(tv -> {
            TableRow<Apartment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    detail(event);

                }
            });
            return row;
        });
    }
    public void detail(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(APARTMENT_DETAIL_VIEW_FXML));
        try {
//            Parent root = fxmlLoader.load();
            AnchorPane root = fxmlLoader.load();
            ApartmentDetailController popupController = fxmlLoader.getController();
            Apartment selectedApartment = table.getSelectionModel().getSelectedItem();
            popupController.setApartment(selectedApartment);
            popupController.setNumber(Integer.toString(selectedApartment.getNumber()));
            popupController.setTenantID(Integer.toString(ApartmentQuery.getTenantIDByApartmentID(selectedApartment.getID())));
            popupController.setTenantName(ApartmentQuery.getTenantNameByApartmentID(selectedApartment.getID()));
            popupController.setStartEndDate(ApartmentQuery.getStartEndDateByApartmentID(selectedApartment.getID()));
            popupController.setBill(ApartmentQuery.getBillByApartmentID(selectedApartment.getID()));
            popupController.setStatus(ApartmentQuery.getStatusByApartmentID(selectedApartment.getID()));

            AnchorPane parentPane = (AnchorPane) basePane.getParent();
            parentPane.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node createTableView(int pageIndex) {
        indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Apartment, Apartment>, ObservableValue<Apartment>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

        indexColumn.setCellFactory(new Callback<TableColumn<Apartment, Apartment>, TableCell<Apartment, Apartment>>() {
            @Override
            public TableCell<Apartment, Apartment> call(TableColumn<Apartment, Apartment> param) {
                return new TableCell<Apartment, Apartment>() {
                    @Override
                    protected void updateItem(Apartment item, boolean empty) {
                        super.updateItem(item, empty);

                        if (this.getTableRow() != null && item != null) {
                            setText(this.getTableRow().getIndex() + 1 + pageIndex * ROWS_PER_PAGE + "");
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        indexColumn.setSortable(false);
        buildingID.setCellValueFactory(new PropertyValueFactory<>("buildingID"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        rent.setCellValueFactory(new PropertyValueFactory<>("rent"));
//        nServices.setCellValueFactory(cellData -> {
//            int id = cellData.getValue().getId();
//            return new ReadOnlyObjectWrapper<>(ServiceService.getNumberOfServicesByOwnerID(id)).asString();
//        });

        int lastIndex = 0;
        int displace = apartmentList.size() % ROWS_PER_PAGE;
        if (displace > 0) {
            lastIndex = apartmentList.size() / ROWS_PER_PAGE;
        } else {
            lastIndex = apartmentList.size() / ROWS_PER_PAGE - 1;
        }
        if (apartmentList.isEmpty()) table.setItems(apartmentList);
        else {
            if (lastIndex == pageIndex && displace > 0) {
                table.setItems(FXCollections.observableArrayList(apartmentList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(apartmentList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
            }
        }
        return table;
    }

    @FXML
    void delete(ActionEvent event) {
        Apartment selectedApartment = table.getSelectionModel().getSelectedItem();
        if(selectedApartment == null) {
            createDialog(Alert.AlertType.WARNING,
                    "Warning",
                    "Please select 1 apartment to continue", "");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm delete apartment");
            alert.setContentText("You want to delete this apartment?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    int ID = selectedApartment.getID();
                    int res = ApartmentQuery.deleteApartment(ID);
                    if (res == 1) {
                        createDialog(Alert.AlertType.WARNING, "Notification", "Delete successfully!", "");
                    } else {
                        createDialog(Alert.AlertType.WARNING, "Notification", "Error, try again later!", "");
                    }
                }
            });
        }

    }

    private boolean isFilterVisible = false;
    @FXML
    void filtering(ActionEvent event) throws IOException {
        isFilterVisible = !isFilterVisible;
        filter.setVisible(isFilterVisible);
    }

    @FXML
    void filter(MouseEvent event) {

        FilteredList<Apartment> filteredData = new FilteredList<>(apartmentList, b -> true);
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = filter.getText();
                if (Integer.toString(person.getBuildingID()).contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
            int soDu = filteredData.size() % ROWS_PER_PAGE;
            if (soDu != 0) pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE + 1);
            else pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE);
            pagination.setMaxPageIndicatorCount(5);
            pagination.setPageFactory(pageIndex->{
                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Apartment, Apartment>, ObservableValue<Apartment>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

                indexColumn.setCellFactory(new Callback<TableColumn<Apartment, Apartment>, TableCell<Apartment, Apartment>>() {
                    @Override
                    public TableCell<Apartment, Apartment> call(TableColumn<Apartment, Apartment> param) {
                        return new TableCell<Apartment, Apartment>() {
                            @Override
                            protected void updateItem(Apartment item, boolean empty) {
                                super.updateItem(item, empty);

                                if (this.getTableRow() != null && item != null) {
                                    setText(this.getTableRow().getIndex() + 1 + pageIndex * ROWS_PER_PAGE + "");
                                } else {
                                    setText("");
                                }
                            }
                        };
                    }
                });
                indexColumn.setSortable(false);
                buildingID.setCellValueFactory(new PropertyValueFactory<>("buildingID"));
                number.setCellValueFactory(new PropertyValueFactory<>("number"));
                size.setCellValueFactory(new PropertyValueFactory<>("size"));
                rent.setCellValueFactory(new PropertyValueFactory<>("rent"));
//                nApparts.setCellValueFactory(cellData -> {
//                    int id = cellData.getValue().getId();
//                    return new ReadOnlyObjectWrapper<>(ApartmentQuery.getNumberOfApartmentsByApartmentID(id)).asString();
//                });
                int lastIndex = 0;
                int displace = filteredData.size() % ROWS_PER_PAGE;
                if (displace > 0) {
                    lastIndex = filteredData.size() / ROWS_PER_PAGE;
                } else {
                    lastIndex = filteredData.size() / ROWS_PER_PAGE - 1;
                }
                if (filteredData.isEmpty()) table.setItems(FXCollections.observableArrayList(filteredData));
                else {
                    if (lastIndex == pageIndex && displace > 0) {
                        table.setItems(FXCollections.observableArrayList(filteredData.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
                    } else {
                        table.setItems(FXCollections.observableArrayList(filteredData.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
                    }
                }
                return table;
            });
        });

    }

    @FXML
    void search(MouseEvent event) {

        FilteredList<Apartment> filteredData = new FilteredList<>(apartmentList, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = search.getText();
                if (Integer.toString(person.getNumber()).contains(lowerCaseFilter)) {
                    return true;
                }
                else {
                    return false;
                }
            });
            int soDu = filteredData.size() % ROWS_PER_PAGE;
            if (soDu != 0) pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE + 1);
            else pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE);
            pagination.setMaxPageIndicatorCount(5);
            pagination.setPageFactory(pageIndex->{
                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Apartment, Apartment>, ObservableValue<Apartment>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

                indexColumn.setCellFactory(new Callback<TableColumn<Apartment, Apartment>, TableCell<Apartment, Apartment>>() {
                    @Override
                    public TableCell<Apartment, Apartment> call(TableColumn<Apartment, Apartment> param) {
                        return new TableCell<Apartment, Apartment>() {
                            @Override
                            protected void updateItem(Apartment item, boolean empty) {
                                super.updateItem(item, empty);

                                if (this.getTableRow() != null && item != null) {
                                    setText(this.getTableRow().getIndex() + 1 + pageIndex * ROWS_PER_PAGE + "");
                                } else {
                                    setText("");
                                }
                            }
                        };
                    }
                });
                indexColumn.setSortable(false);
                buildingID.setCellValueFactory(new PropertyValueFactory<>("buildingID"));
                number.setCellValueFactory(new PropertyValueFactory<>("number"));
                size.setCellValueFactory(new PropertyValueFactory<>("size"));
                rent.setCellValueFactory(new PropertyValueFactory<>("rent"));
//                nApparts.setCellValueFactory(cellData -> {
//                    int id = cellData.getValue().getId();
//                    return new ReadOnlyObjectWrapper<>(ApartmentQuery.getNumberOfApartmentsByApartmentID(id)).asString();
//                });
                int lastIndex = 0;
                int displace = filteredData.size() % ROWS_PER_PAGE;
                if (displace > 0) {
                    lastIndex = filteredData.size() / ROWS_PER_PAGE;
                } else {
                    lastIndex = filteredData.size() / ROWS_PER_PAGE - 1;
                }
                if (filteredData.isEmpty()) table.setItems(FXCollections.observableArrayList(filteredData));
                else {
                    if (lastIndex == pageIndex && displace > 0) {
                        table.setItems(FXCollections.observableArrayList(filteredData.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
                    } else {
                        table.setItems(FXCollections.observableArrayList(filteredData.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
                    }
                }
                return table;
            });
        });

    }

}