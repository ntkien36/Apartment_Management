package com.management.apartment_management.Controllers.Tenant;

import com.management.apartment_management.Controllers.Tenant.AddTenantController;
//import com.management.apartment_management.Controllers.Tenant.DetailTenantController;
import com.management.apartment_management.HomeApplication;
import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Models.Tenant;
import com.management.apartment_management.Query.ApartmentQuery;
import com.management.apartment_management.Query.BuildingQuery;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.management.apartment_management.Constants.FXMLConstants.TENANT_ADD_FXML;
import static com.management.apartment_management.Constants.FXMLConstants.TENANT_DETAIL_VIEW_FXML;
import static com.management.apartment_management.Utils.Utils.createDialog;
//import static com.management.apartment_management.Constants.FXMLConstants.TENANT_DETAIL_VIEW_FXML;

public class TenantController implements Initializable {

    private final ViewUtils viewUtils = new ViewUtils();
    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn<Tenant, String> name, contact, status, startEndDate;
    @FXML
    private TableColumn<Tenant, Integer> apartmentID;

    @FXML
    private TableColumn indexColumn;
    @FXML
    private Pagination pagination;

    @FXML
    private TextField search;
    @FXML
    private TextField filter;

    @FXML
    private TableView<Tenant> table;
    public Preferences pre = Preferences.userRoot();

    private ObservableList<Tenant> tenantList = FXCollections.observableArrayList();
    private static final int ROWS_PER_PAGE = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tenantList = FXCollections.observableArrayList(TenantQuery.getTenant());

        int soDu = tenantList.size() % ROWS_PER_PAGE;
        if (soDu != 0) pagination.setPageCount(tenantList.size() / ROWS_PER_PAGE + 1);
        else pagination.setPageCount(tenantList.size() / ROWS_PER_PAGE);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createTableView);

        table.setRowFactory(tv -> {
            TableRow<Tenant> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        detail(event);

                }
            });
            return row;
        });
    }

    private Node createTableView(int pageIndex) {
        indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Tenant, Tenant>, ObservableValue<Tenant>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

        indexColumn.setCellFactory(new Callback<TableColumn<Tenant, Tenant>, TableCell<Tenant, Tenant>>() {
            @Override
            public TableCell<Tenant, Tenant> call(TableColumn<Tenant, Tenant> param) {
                return new TableCell<Tenant, Tenant>() {
                    @Override
                    protected void updateItem(Tenant item, boolean empty) {
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
        apartmentID.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        startEndDate.setCellValueFactory(new PropertyValueFactory<>("startEndDate"));

//        nServices.setCellValueFactory(cellData -> {
//            int id = cellData.getValue().getId();
//            return new ReadOnlyObjectWrapper<>(ServiceService.getNumberOfServicesByOwnerID(id)).asString();
//        });

        int lastIndex = 0;
        int displace = tenantList.size() % ROWS_PER_PAGE;
        if (displace > 0) {
            lastIndex = tenantList.size() / ROWS_PER_PAGE;
        } else {
            lastIndex = tenantList.size() / ROWS_PER_PAGE - 1;
        }
        if (tenantList.isEmpty()) table.setItems(tenantList);
        else {
            if (lastIndex == pageIndex && displace > 0) {
                table.setItems(FXCollections.observableArrayList(tenantList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(tenantList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
            }
        }
        return table;
    }

    @FXML
    void add(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(TENANT_ADD_FXML));
        try {
            Parent root = fxmlLoader.load();
            AddTenantController popupController = fxmlLoader.getController();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("New Tenant!!!");
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void detail(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(TENANT_DETAIL_VIEW_FXML));
        try {
//            Parent root = fxmlLoader.load();
            AnchorPane root = fxmlLoader.load();
            DetailTenantController popupController = fxmlLoader.getController();
            Tenant selectedTenant = table.getSelectionModel().getSelectedItem();
            popupController.setTenant(selectedTenant);
            popupController.setName(selectedTenant.getName());
            popupController.setContact(selectedTenant.getContact());
            popupController.setStatus(selectedTenant.getStatus());
            popupController.setStartEndDate(selectedTenant.getStartEndDate());
            popupController.setApartmentID(Integer.toString(selectedTenant.getApartmentID()));
            popupController.setNumber(Integer.toString(TenantQuery.getApartmentNumberByTenantID(selectedTenant.getId())));
            popupController.setBuildingName(TenantQuery.getBuildingNameByTenantID(selectedTenant.getId()));
//            popupController.tenant = selectedTenant;
//            popupController.setApartmentview();

            AnchorPane parentPane = (AnchorPane) basePane.getParent();
            parentPane.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void search() {
        FilteredList<Tenant> filteredData = new FilteredList<>(tenantList, b -> true);
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
            int soDu = filteredData.size() % ROWS_PER_PAGE;
            if (soDu != 0) pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE + 1);
            else pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE);
            pagination.setMaxPageIndicatorCount(5);
            pagination.setPageFactory(pageIndex->{
                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Tenant, Tenant>, ObservableValue<Tenant>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

                indexColumn.setCellFactory(new Callback<TableColumn<Tenant, Tenant>, TableCell<Tenant, Tenant>>() {
                    @Override
                    public TableCell<Tenant, Tenant> call(TableColumn<Tenant, Tenant> param) {
                        return new TableCell<Tenant, Tenant>() {
                            @Override
                            protected void updateItem(Tenant item, boolean empty) {
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
                apartmentID.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                status.setCellValueFactory(new PropertyValueFactory<>("status"));
                contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
                startEndDate.setCellValueFactory(new PropertyValueFactory<>("startEndDate"));
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

    private boolean isFilterVisible = false;
//    @FXML
//    void filtering(ActionEvent event) throws IOException {
//        isFilterVisible = !isFilterVisible;
//        filter.setVisible(isFilterVisible);
//    }
    @FXML
    void filter() {
        FilteredList<Tenant> filteredData = new FilteredList<>(tenantList, b -> true);
        filteredData.setPredicate(person -> person.getStatus().equals("ACTIVE"));
            int soDu = filteredData.size() % ROWS_PER_PAGE;
            if (soDu != 0) pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE + 1);
            else pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE);
            pagination.setMaxPageIndicatorCount(5);
            pagination.setPageFactory(pageIndex->{
                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Tenant, Tenant>, ObservableValue<Tenant>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

                indexColumn.setCellFactory(new Callback<TableColumn<Tenant, Tenant>, TableCell<Tenant, Tenant>>() {
                    @Override
                    public TableCell<Tenant, Tenant> call(TableColumn<Tenant, Tenant> param) {
                        return new TableCell<Tenant, Tenant>() {
                            @Override
                            protected void updateItem(Tenant item, boolean empty) {
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
                apartmentID.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                status.setCellValueFactory(new PropertyValueFactory<>("status"));
                contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
                startEndDate.setCellValueFactory(new PropertyValueFactory<>("startEndDate"));
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
//        });
    }
    @FXML
    void delete(ActionEvent event) {
        Tenant selectedTenant = table.getSelectionModel().getSelectedItem();
        if(selectedTenant == null) {
            createDialog(Alert.AlertType.WARNING,
                    "Warning",
                    "Please select 1 tenant to continue", "");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm delete tenant");
            alert.setContentText("You want to delete this tenant?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    int ID = selectedTenant.getId();
                    int res = TenantQuery.deleteTenant(ID);
                    if (res == 1) {
                        createDialog(Alert.AlertType.WARNING, "Notification", "Delete successfully!", "");
                    } else {
                        createDialog(Alert.AlertType.WARNING, "Notification", "Error, try again later!", "");
                    }
                }
            });
        }

    }
}


