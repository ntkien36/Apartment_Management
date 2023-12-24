package com.management.apartment_management.Controllers.Report;

import com.management.apartment_management.HomeApplication;
import com.management.apartment_management.Models.Report;
import com.management.apartment_management.Models.Tenant;
import com.management.apartment_management.Query.ReportQuery;
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

import static com.management.apartment_management.Constants.FXMLConstants.*;
import static com.management.apartment_management.Constants.FXMLConstants.USER_REPORT_VIEW_FXML;
import static com.management.apartment_management.Utils.Utils.createDialog;

public class ReportController implements Initializable {

    private final ViewUtils viewUtils = new ViewUtils();
    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn<Report, String> description;
    @FXML
    private TableColumn<Report, Integer> create_by;
    @FXML
    private TableColumn<Report, String> status;
    @FXML
    private TableColumn<Report, String> name;
    @FXML
    private TableColumn indexColumn;
    @FXML
    private Pagination pagination;

    @FXML
    private TextField search;

    @FXML
    private TableView<Report> table;
    public Preferences pre = Preferences.userRoot();


    @FXML
    void add(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(REPORT_ADD_FXML));
        try {
            Parent root = fxmlLoader.load();
            AddReportController popupController = fxmlLoader.getController();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("New Report!!!");
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ObservableList<Report> reportList = FXCollections.observableArrayList();
    private static final int ROWS_PER_PAGE = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reportList = FXCollections.observableArrayList(ReportQuery.getReport());

        int soDu = reportList.size() % ROWS_PER_PAGE;
        if (soDu != 0) pagination.setPageCount(reportList.size() / ROWS_PER_PAGE + 1);
        else pagination.setPageCount(reportList.size() / ROWS_PER_PAGE);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createTableView);

        table.setRowFactory(tv -> {
            TableRow<Report> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {

                    detail(event);

                }
            });
            return row;
        });
    }
    public void detail(MouseEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(REPORT_DETAIL_VIEW_FXML));
        try {
//            Parent root = fxmlLoader.load();
            AnchorPane root = fxmlLoader.load();
            DetailReportController popupController = fxmlLoader.getController();
            Report selectedReport = table.getSelectionModel().getSelectedItem();

            popupController.setReport(selectedReport);
            popupController.setName(selectedReport.getName());
            popupController.setDescription(selectedReport.getDescription());
            popupController.setStatus(selectedReport.getStatus());

            AnchorPane parentPane = (AnchorPane) basePane.getParent();
            parentPane.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Node createTableView(int pageIndex) {
        indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Report, Report>, ObservableValue<Report>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

        indexColumn.setCellFactory(new Callback<TableColumn<Report, Report>, TableCell<Report, Report>>() {
            @Override
            public TableCell<Report, Report> call(TableColumn<Report, Report> param) {
                return new TableCell<Report, Report>() {
                    @Override
                    protected void updateItem(Report item, boolean empty) {
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
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        create_by.setCellValueFactory(new PropertyValueFactory<>("create_by"));
//        nServices.setCellValueFactory(cellData -> {
//            int id = cellData.getValue().getId();
//            return new ReadOnlyObjectWrapper<>(ServiceService.getNumberOfServicesByOwnerID(id)).asString();
//        });

        int lastIndex = 0;
        int displace = reportList.size() % ROWS_PER_PAGE;
        if (displace > 0) {
            lastIndex = reportList.size() / ROWS_PER_PAGE;
        } else {
            lastIndex = reportList.size() / ROWS_PER_PAGE - 1;
        }
        if (reportList.isEmpty()) table.setItems(reportList);
        else {
            if (lastIndex == pageIndex && displace > 0) {
                table.setItems(FXCollections.observableArrayList(reportList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(reportList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
            }
        }
        return table;
    }
    @FXML
    void delete(ActionEvent event) {
        Report selectedReport = table.getSelectionModel().getSelectedItem();
        if(selectedReport == null) {
            createDialog(Alert.AlertType.WARNING,
                    "Warning",
                    "Please select 1 report to continue", "");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm delete report");
            alert.setContentText("You want to delete this report?");
            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(okButton, noButton);
            alert.showAndWait().ifPresent(type -> {
                if (type == okButton) {
                    int ID = selectedReport.getId();
                    int res = ReportQuery.deleteReport(ID);
                    if (res == 1) {
                        createDialog(Alert.AlertType.WARNING, "Notification", "Delete successfully!", "");
                    } else {
                        createDialog(Alert.AlertType.WARNING, "Notification", "Error, try again later!", "");
                    }
                }
            });
        }

    }
    @FXML
    void search(MouseEvent event) {

        FilteredList<Report> filteredData = new FilteredList<>(reportList, b -> true);
        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = search.getText();
                if (person.getDescription().contains(lowerCaseFilter)) {
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
                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Report, Report>, ObservableValue<Report>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

                indexColumn.setCellFactory(new Callback<TableColumn<Report, Report>, TableCell<Report, Report>>() {
                    @Override
                    public TableCell<Report, Report> call(TableColumn<Report, Report> param) {
                        return new TableCell<Report, Report>() {
                            @Override
                            protected void updateItem(Report item, boolean empty) {
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
                name.setCellValueFactory(new PropertyValueFactory<>("name"));
                description.setCellValueFactory(new PropertyValueFactory<>("description"));
                status.setCellValueFactory(new PropertyValueFactory<>("status"));
                create_by.setCellValueFactory(new PropertyValueFactory<>("create_by"));

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
    void filter() {
        FilteredList<Report> filteredData = new FilteredList<>(reportList, b -> true);
        filteredData.setPredicate(person -> person.getStatus().equals("DISAPPROVED"));
        int soDu = filteredData.size() % ROWS_PER_PAGE;
        if (soDu != 0) pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE + 1);
        else pagination.setPageCount(filteredData.size() / ROWS_PER_PAGE);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(pageIndex->{
            indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Report, Report>, ObservableValue<Tenant>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

            indexColumn.setCellFactory(new Callback<TableColumn<Report, Report>, TableCell<Report, Report>>() {
                @Override
                public TableCell<Report, Report> call(TableColumn<Report, Report> param) {
                    return new TableCell<Report, Report>() {
                        @Override
                        protected void updateItem(Report item, boolean empty) {
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
            name.setCellValueFactory(new PropertyValueFactory<>("name"));
            description.setCellValueFactory(new PropertyValueFactory<>("description"));
            status.setCellValueFactory(new PropertyValueFactory<>("status"));
            create_by.setCellValueFactory(new PropertyValueFactory<>("create_by"));

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
}


