package com.management.apartment_management.Controllers.Building;

import com.management.apartment_management.HomeApplication;
import com.management.apartment_management.Models.Apartment;
import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Query.ApartmentQuery;
import com.management.apartment_management.Query.BuildingQuery;
import com.management.apartment_management.Controllers.Building.BuildingController;

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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static com.management.apartment_management.Constants.FXMLConstants.*;
import static com.management.apartment_management.Utils.Utils.createDialog;

public class DetailBuildingController implements Initializable {
    public Building building;
    private final ViewUtils viewUtils = new ViewUtils();

    @FXML
    public TextField addressText;

    @FXML
    private AnchorPane basePane;

    @FXML
    private TextField filter;

    @FXML
    private TableColumn indexColumn;

    @FXML
    public Text nameText;

    @FXML
    private TableColumn<Apartment, Integer> number;

    @FXML
    private Pagination pagination;

    @FXML
    private TableColumn<Apartment, Integer> rent;

    @FXML
    public TextField rentText;

    @FXML
    private TextField search;
    @FXML
    public TextField idText;

    @FXML
    public TableColumn<Apartment, Integer> size;

    @FXML
    private TableView<Apartment> table;

    @FXML
    public TextField totalText;
    public ObservableList<Apartment> apartmentList = FXCollections.observableArrayList();
    private static final int ROWS_PER_PAGE = 10;
//    public DetailBuildingController(Building selectedBuilding) {
//        this.building = selectedBuilding;
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
}
        public void setApartmentview(){
        apartmentList = FXCollections.observableArrayList(ApartmentQuery.getApartmentsByBuildingID(building.getId()));
        int soDu = apartmentList.size() % ROWS_PER_PAGE;
        if (soDu != 0) pagination.setPageCount(apartmentList.size() / ROWS_PER_PAGE + 1);
        else pagination.setPageCount(apartmentList.size() / ROWS_PER_PAGE);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createTableView);

        table.setRowFactory(tv -> {
            TableRow<Apartment> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
//                    detail(event);
                }
            });
            return row;
        });
    }
//    public void createList(ObservableList<Apartment> apartmentList){
    private Node createTableView(int pageIndex) {
        indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Apartment, Apartment>, ObservableValue<Apartment>>) a -> new ReadOnlyObjectWrapper(a.getValue()));

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
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        size.setCellValueFactory(new PropertyValueFactory<>("size"));
        rent.setCellValueFactory(new PropertyValueFactory<>("rent"));
//        nApparts.setCellValueFactory(cellData -> {
//            int id = cellData.getValue().getId();
//            return new ReadOnlyObjectWrapper<>(ApartmentQuery.getNumberOfApartmentsByBuildingID(id)).asString();
//        });
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
    void filter(MouseEvent event) {
        FilteredList<Apartment> filteredData = new FilteredList<>(apartmentList, b -> true);
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = filter.getText().toLowerCase();
                if ( Integer.toString(person.getNumber()).toLowerCase().contains(lowerCaseFilter)) {
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
                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Apartment, Apartment>, ObservableValue<Apartment>>) a -> new ReadOnlyObjectWrapper(a.getValue()));

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
                number.setCellValueFactory(new PropertyValueFactory<>("number"));
                size.setCellValueFactory(new PropertyValueFactory<>("size"));
                rent.setCellValueFactory(new PropertyValueFactory<>("rent"));

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
    @FXML
    void filtering(ActionEvent event) throws IOException {
        isFilterVisible = !isFilterVisible;
        filter.setVisible(isFilterVisible);
    }

    @FXML
    void back(ActionEvent event) throws IOException {
        viewUtils.changeAnchorPane(basePane, BUILDING_VIEW_FXML);
    }

}