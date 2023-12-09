package com.management.apartment_management.Controllers.Contract;

import com.management.apartment_management.Controllers.Contract.ButtonController;
import com.management.apartment_management.Models.Contract;
import com.management.apartment_management.Query.ContractQuery;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.management.apartment_management.Utils.Utils.createDialog;

public class ContractController implements Initializable {

    private final ViewUtils viewUtils = new ViewUtils();
    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn<Contract, String> tenantName;

    @FXML
    private TableColumn<Contract, Date> startTime;
    @FXML
    private TableColumn<Contract, Date> endTime;

    @FXML
    private TableColumn<Contract, Contract> delete;
    @FXML
    private TableColumn<Contract, Contract> edit;
    @FXML
    private TableColumn<Contract, String> note;
    @FXML
    private TableColumn indexColumn;
    @FXML
    private Pagination pagination;

    @FXML
    private TextField search;
    @FXML
    private TextField filter;

    @FXML
    private TableView<Contract> table;

    public Preferences pre = Preferences.userRoot();

    private ObservableList<Contract> contractList = FXCollections.observableArrayList();
    private static final int ROWS_PER_PAGE = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        contractList = FXCollections.observableArrayList(ContractQuery.getContract());

        int soDu = contractList.size() % ROWS_PER_PAGE;
        if (soDu != 0) pagination.setPageCount(contractList.size() / ROWS_PER_PAGE + 1);
        else pagination.setPageCount(contractList.size() / ROWS_PER_PAGE);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createTableView);

        table.setRowFactory(tv -> {
            TableRow<Contract> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        detail(event);

                }
            });
            return row;
        });
    }

    private Node createTableView(int pageIndex) {
        indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Contract, Contract>, ObservableValue<Contract>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

        indexColumn.setCellFactory(new Callback<TableColumn<Contract, Contract>, TableCell<Contract, Contract>>() {
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
        indexColumn.setSortable(false);
        tenantName.setCellValueFactory(new PropertyValueFactory<>("tenantName"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        note.setCellValueFactory(new PropertyValueFactory<>("notes"));

        delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        delete.setCellFactory(param -> new ButtonController.DeleteButtonCell());

        edit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        edit.setCellFactory(param -> new ButtonController.EditButtonCell(basePane));

        int lastIndex = 0;
        int displace = contractList.size() % ROWS_PER_PAGE;
        if (displace > 0) {
            lastIndex = contractList.size() / ROWS_PER_PAGE;
        } else {
            lastIndex = contractList.size() / ROWS_PER_PAGE - 1;
        }
        if (contractList.isEmpty()) table.setItems(contractList);
        else {
            if (lastIndex == pageIndex && displace > 0) {
                table.setItems(FXCollections.observableArrayList(contractList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(contractList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
            }
        }
        return table;
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
        FilteredList<Contract> filteredData = new FilteredList<>(contractList, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = search.getText().toLowerCase();
                if (person.getTenantName().toLowerCase().contains(lowerCaseFilter)) {
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
                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Contract, Contract>, ObservableValue<Contract>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

                indexColumn.setCellFactory(new Callback<TableColumn<Contract, Contract>, TableCell<Contract, Contract>>() {
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
                indexColumn.setSortable(false);
                tenantName.setCellValueFactory(new PropertyValueFactory<>("tenantName"));
                startTime.setCellValueFactory(new PropertyValueFactory<>("startDate"));
                endTime.setCellValueFactory(new PropertyValueFactory<>("endDate"));
                note.setCellValueFactory(new PropertyValueFactory<>("notes"));

                delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                delete.setCellFactory(param -> new ButtonController.DeleteButtonCell());

                edit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                edit.setCellFactory(param -> new ButtonController.EditButtonCell(basePane));

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
    void add(ActionEvent event) {

    }

    private boolean isFilterVisible = false;
    @FXML
    void filtering(ActionEvent event) throws IOException {
        isFilterVisible = !isFilterVisible;
        filter.setVisible(isFilterVisible);
    }
    @FXML
    void filter() {
        FilteredList<Contract> filteredData = new FilteredList<>(contractList, b -> true);
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(contract -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                Date endDate = contract.getEndDate(); // Lấy ngày kết thúc từ contract
                // Kiểm tra nếu ngày kết thúc chứa giá trị của filter
                if (endDate != null && endDate.toString().toLowerCase().contains(lowerCaseFilter)) {
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
                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Contract, Contract>, ObservableValue<Contract>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

                indexColumn.setCellFactory(new Callback<TableColumn<Contract, Contract>, TableCell<Contract, Contract>>() {
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
                indexColumn.setSortable(false);
                tenantName.setCellValueFactory(new PropertyValueFactory<>("tenantName"));
                startTime.setCellValueFactory(new PropertyValueFactory<>("startDate"));
                endTime.setCellValueFactory(new PropertyValueFactory<>("endDate"));
                note.setCellValueFactory(new PropertyValueFactory<>("notes"));

                delete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                delete.setCellFactory(param -> new ButtonController.DeleteButtonCell());

                edit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
                edit.setCellFactory(param -> new ButtonController.EditButtonCell(basePane));
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


