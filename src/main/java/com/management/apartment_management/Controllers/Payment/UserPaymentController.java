package com.management.apartment_management.Controllers.Payment;

//import com.management.apartment_management.Controllers.Tenant.AddTenantController;
//import com.management.apartment_management.Controllers.Tenant.DetailTenantController;
import com.management.apartment_management.Models.Contract;
import com.management.apartment_management.Models.Payment;
import com.management.apartment_management.Query.ContractQuery;
import com.management.apartment_management.Query.PaymentQuery;
import com.management.apartment_management.Utils.ViewUtils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.management.apartment_management.Utils.Utils.createDialog;
//import static com.management.apartment_management.Constants.FXMLConstants.TENANT_DETAIL_VIEW_FXML;

public class UserPaymentController implements Initializable {

    private final ViewUtils viewUtils = new ViewUtils();
    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn<Payment, String> note;
    @FXML
    private TableColumn<Contract, String> note1;
    @FXML
    private TableColumn<Payment, Integer> amount;
    @FXML
    private TableColumn<Contract, Integer> amount1;
    @FXML
    private TableColumn<Payment, Date> timePayment;
    @FXML
    private TableColumn<Contract, Date> timePayment1;
    @FXML
    private TableColumn indexColumn, indexColumn1;
    @FXML
    private Pagination pagination, pagination1;

    @FXML
    private TextField search;
    @FXML
    private TextField filter;

    @FXML
    private TableView<Payment> table;
    @FXML
    private TableView<Contract> table1;
    public Preferences pre = Preferences.userRoot();

    public ObservableList<Payment> paymentList = FXCollections.observableArrayList();
    public ObservableList<Contract> paymentList1 = FXCollections.observableArrayList();
    private static final int ROWS_PER_PAGE = 3;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Preferences pre = Preferences.userRoot();
        String tenantID = pre.get("tenant_id", "");
        System.out.println(tenantID);
        paymentList = FXCollections.observableArrayList(PaymentQuery.getPaymentByTenantID(Integer.parseInt(tenantID)));
        paymentList1 = FXCollections.observableArrayList(ContractQuery.getNotYetPaymentByTenantID(Integer.parseInt(tenantID)));

        int soDu = paymentList.size() % ROWS_PER_PAGE;
        if (soDu != 0) pagination.setPageCount(paymentList.size() / ROWS_PER_PAGE + 1);
        else pagination.setPageCount(paymentList.size() / ROWS_PER_PAGE);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createHistoryTableView);

        table.setRowFactory(tv -> {
            TableRow<Payment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        detail(event);

                }
            });
            return row;
        });

        int soDu1 = paymentList1.size() % ROWS_PER_PAGE;
        if (soDu1 != 0) pagination1.setPageCount(paymentList1.size() / ROWS_PER_PAGE + 1);
        else pagination1.setPageCount(paymentList1.size() / ROWS_PER_PAGE);
        pagination1.setMaxPageIndicatorCount(5);
        pagination1.setPageFactory(this::createNotYetTableView);

        table1.setRowFactory(tv -> {
            TableRow<Contract> row1 = new TableRow<>();
            row1.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row1.isEmpty())) {
                    detail(event);

                }
            });
            return row1;
        });
    }

    private Node createHistoryTableView(int pageIndex) {
        indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Payment, Payment>, ObservableValue<Payment>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

        indexColumn.setCellFactory(new Callback<TableColumn<Payment, Payment>, TableCell<Payment, Payment>>() {
            @Override
            public TableCell<Payment, Payment> call(TableColumn<Payment, Payment> param) {
                return new TableCell<Payment, Payment>() {
                    @Override
                    protected void updateItem(Payment item, boolean empty) {
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
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        timePayment.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        note.setCellValueFactory(new PropertyValueFactory<>("note"));


        int lastIndex = 0;
        int displace = paymentList.size() % ROWS_PER_PAGE;
        if (displace > 0) {
            lastIndex = paymentList.size() / ROWS_PER_PAGE;
        } else {
            lastIndex = paymentList.size() / ROWS_PER_PAGE - 1;
        }
        if (paymentList.isEmpty()) table.setItems(paymentList);
        else {
            if (lastIndex == pageIndex && displace > 0) {
                table.setItems(FXCollections.observableArrayList(paymentList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(paymentList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
            }
        }
        return table;
    }

    private Node createNotYetTableView(int pageIndex) {
        indexColumn1.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Contract, Contract>, ObservableValue<Payment>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

        indexColumn1.setCellFactory(new Callback<TableColumn<Contract, Contract>, TableCell<Contract, Contract>>() {
            @Override
            public TableCell<Contract, Contract> call(TableColumn<Contract, Contract> param) {
                return new TableCell<Contract, Contract>() {
                    @Override
                    protected void updateItem(Contract item, boolean empty) {
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
        indexColumn1.setSortable(false);
        amount1.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        timePayment1.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        note1.setCellValueFactory(new PropertyValueFactory<>("notes"));
        //
        int lastIndex = 0;
        int displace = paymentList1.size() % ROWS_PER_PAGE;
        if (displace > 0) {
            lastIndex = paymentList1.size() / ROWS_PER_PAGE;
        } else {
            lastIndex = paymentList1.size() / ROWS_PER_PAGE - 1;
        }
        if (paymentList1.isEmpty()) table1.setItems(paymentList1);
        else {
            if (lastIndex == pageIndex && displace > 0) {
                table1.setItems(FXCollections.observableArrayList(paymentList1.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
            } else {
                table1.setItems(FXCollections.observableArrayList(paymentList1.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
            }
        }
        return table1;
    }
    void handleDeleteAction(Payment payment) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete tenant");
        alert.setContentText("You want to delete this tenant?");
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


    public void detail(MouseEvent event) {
//        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(TENANT_DETAIL_VIEW_FXML));
//        try {
////            Parent root = fxmlLoader.load();
//            AnchorPane root = fxmlLoader.load();
//            DetailTenantController popupController = fxmlLoader.getController();
//            Tenant selectedTenant = table.getSelectionModel().getSelectedItem();
//            popupController.setTenant(selectedTenant);
//            popupController.setName(selectedTenant.getName());
//            popupController.setContact(selectedTenant.getContact());
//            popupController.setStatus(selectedTenant.getStatus());
//            popupController.setStartEndDate(selectedTenant.getStartEndDate());
//            popupController.setApartmentID(Integer.toString(selectedTenant.getApartmentID()));
//            popupController.setNumber(Integer.toString(TenantQuery.getApartmentNumberByTenantID(selectedTenant.getId())));
//            popupController.setBuildingName(TenantQuery.getBuildingNameByTenantID(selectedTenant.getId()));
////            popupController.tenant = selectedTenant;
////            popupController.setApartmentview();
//
//            AnchorPane parentPane = (AnchorPane) basePane.getParent();
//            parentPane.getChildren().setAll(root);
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @FXML
    void search() {
        FilteredList<Contract> filteredData = new FilteredList<>(paymentList1, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = search.getText().toLowerCase();
                if (person.getNotes().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
            int soDu = filteredData.size() % ROWS_PER_PAGE;
            if (soDu != 0) pagination1.setPageCount(filteredData.size() / ROWS_PER_PAGE + 1);
            else pagination1.setPageCount(filteredData.size() / ROWS_PER_PAGE);
            pagination1.setMaxPageIndicatorCount(5);
            pagination1.setPageFactory(pageIndex->{
                indexColumn1.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Contract, Contract>, ObservableValue<Contract>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

                indexColumn1.setCellFactory(new Callback<TableColumn<Contract, Contract>, TableCell<Contract, Contract>>() {
                    @Override
                    public TableCell<Contract, Contract> call(TableColumn<Contract, Contract> param) {
                        return new TableCell<Contract, Contract>() {
                            @Override
                            protected void updateItem(Contract item, boolean empty) {
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
                indexColumn1.setSortable(false);
                amount1.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
                timePayment1.setCellValueFactory(new PropertyValueFactory<>("endDate"));
                note1.setCellValueFactory(new PropertyValueFactory<>("notes"));
                int lastIndex = 0;
                int displace = filteredData.size() % ROWS_PER_PAGE;
                if (displace > 0) {
                    lastIndex = filteredData.size() / ROWS_PER_PAGE;
                } else {
                    lastIndex = filteredData.size() / ROWS_PER_PAGE - 1;
                }
                if (filteredData.isEmpty()) table1.setItems(FXCollections.observableArrayList(filteredData));
                else {
                    if (lastIndex == pageIndex && displace > 0) {
                        table1.setItems(FXCollections.observableArrayList(filteredData.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
                    } else {
                        table1.setItems(FXCollections.observableArrayList(filteredData.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
                    }
                }
                return table1;
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
//        FilteredList<Tenant> filteredData = new FilteredList<>(tenantList, b -> true);
//        filteredData.setPredicate(person -> person.getStatus().equals("ACTIVE"));
//            int soDu = filteredData.size() % ROWS_PER_PAGE;
//            if (soDu != 0) pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE + 1);
//            else pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE);
//            pagination.setMaxPageIndicatorCount(5);
//            pagination.setPageFactory(pageIndex->{
//                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Tenant, Tenant>, ObservableValue<Tenant>>) b -> new ReadOnlyObjectWrapper(b.getValue()));
//
//                indexColumn.setCellFactory(new Callback<TableColumn<Tenant, Tenant>, TableCell<Tenant, Tenant>>() {
//                    @Override
//                    public TableCell<Tenant, Tenant> call(TableColumn<Tenant, Tenant> param) {
//                        return new TableCell<Tenant, Tenant>() {
//                            @Override
//                            protected void updateItem(Tenant item, boolean empty) {
//                                super.updateItem(item, empty);
//
//                                if (this.getTableRow() != null && item != null) {
//                                    setText(this.getTableRow().getIndex() + 1 + pageIndex * ROWS_PER_PAGE + "");
//                                } else {
//                                    setText("");
//                                }
//                            }
//                        };
//                    }
//                });
//                indexColumn.setSortable(false);
//                apartmentID.setCellValueFactory(new PropertyValueFactory<>("apartmentID"));
//                name.setCellValueFactory(new PropertyValueFactory<>("name"));
//                status.setCellValueFactory(new PropertyValueFactory<>("status"));
//                contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
//                startEndDate.setCellValueFactory(new PropertyValueFactory<>("startEndDate"));
//                int lastIndex = 0;
//                int displace = filteredData.size() % ROWS_PER_PAGE;
//                if (displace > 0) {
//                    lastIndex = filteredData.size() / ROWS_PER_PAGE;
//                } else {
//                    lastIndex = filteredData.size() / ROWS_PER_PAGE - 1;
//                }
//                if (filteredData.isEmpty()) table.setItems(FXCollections.observableArrayList(filteredData));
//                else {
//                    if (lastIndex == pageIndex && displace > 0) {
//                        table.setItems(FXCollections.observableArrayList(filteredData.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
//                    } else {
//                        table.setItems(FXCollections.observableArrayList(filteredData.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
//                    }
//                }
//                return table;
//            });
////        });
    }

}


