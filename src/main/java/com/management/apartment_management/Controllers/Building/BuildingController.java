package com.management.apartment_management.Controllers.Building;

import com.management.apartment_management.HomeApplication;
import com.management.apartment_management.Models.Apartment;
import com.management.apartment_management.Models.Building;
import com.management.apartment_management.Query.ApartmentQuery;
import com.management.apartment_management.Query.BuildingQuery;
import com.management.apartment_management.Controllers.Building.DetailBuildingController;
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
import static com.management.apartment_management.Utils.Utils.createDialog;

public class BuildingController implements Initializable {

    private final ViewUtils viewUtils = new ViewUtils();
    @FXML
    private AnchorPane basePane;

    @FXML
    private TableColumn<Building, String> address, name, nApparts;
    @FXML
    private TableColumn<Building, Integer> total;
    @FXML
    private TableColumn indexColumn;
    @FXML
    private Pagination pagination;

    @FXML
    private TextField search;
    @FXML
    private TextField filter;

    @FXML
    private TableView<Building> table;
    public Preferences pre = Preferences.userRoot();


    @FXML
    void add(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(BUILDING_ADD_FXML));
        try {
            Parent root = fxmlLoader.load();
            AddBuildingController popupController = fxmlLoader.getController();

            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("New Building!!!");
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private ObservableList<Building> buildingList = FXCollections.observableArrayList();
    private static final int ROWS_PER_PAGE = 10;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildingList = FXCollections.observableArrayList(BuildingQuery.getBuilding());

        int soDu = buildingList.size() % ROWS_PER_PAGE;
        if (soDu != 0) pagination.setPageCount(buildingList.size() / ROWS_PER_PAGE + 1);
        else pagination.setPageCount(buildingList.size() / ROWS_PER_PAGE);
        pagination.setMaxPageIndicatorCount(5);
        pagination.setPageFactory(this::createTableView);

        table.setRowFactory(tv -> {
            TableRow<Building> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        detail(event);

                }
            });
            return row;
        });
    }
    public void detail(MouseEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HomeApplication.class.getResource(BUILDING_DETAIL_VIEW_FXML));
        try {
//            Parent root = fxmlLoader.load();
            AnchorPane root = fxmlLoader.load();
            DetailBuildingController popupController = fxmlLoader.getController();
            Building selectedBuilding = table.getSelectionModel().getSelectedItem();
            popupController.nameText.setText(selectedBuilding.getName());
            popupController.idText.setText(String.valueOf(selectedBuilding.getId()));
            popupController.addressText.setText(selectedBuilding.getAddress());
            popupController.totalText.setText(String.valueOf(selectedBuilding.getTotal()));
            popupController.rentText.setText(String.valueOf(ApartmentQuery.getNumberOfApartmentsByBuildingID(selectedBuilding.getId())));
            popupController.building = selectedBuilding;
            popupController.setApartmentview();

            AnchorPane parentPane = (AnchorPane) basePane.getParent();
            parentPane.getChildren().setAll(root);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Node createTableView(int pageIndex) {
        indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Building, Building>, ObservableValue<Building>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

        indexColumn.setCellFactory(new Callback<TableColumn<Building, Building>, TableCell<Building, Building>>() {
            @Override
            public TableCell<Building, Building> call(TableColumn<Building, Building> param) {
                return new TableCell<Building, Building>() {
                    @Override
                    protected void updateItem(Building item, boolean empty) {
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
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        total.setCellValueFactory(new PropertyValueFactory<>("total"));
        nApparts.setCellValueFactory(cellData -> {
            int id = cellData.getValue().getId();
            return new ReadOnlyObjectWrapper<>(ApartmentQuery.getNumberOfApartmentsByBuildingID(id)).asString();
        });
//        nServices.setCellValueFactory(cellData -> {
//            int id = cellData.getValue().getId();
//            return new ReadOnlyObjectWrapper<>(ServiceService.getNumberOfServicesByOwnerID(id)).asString();
//        });

        int lastIndex = 0;
        int displace = buildingList.size() % ROWS_PER_PAGE;
        if (displace > 0) {
            lastIndex = buildingList.size() / ROWS_PER_PAGE;
        } else {
            lastIndex = buildingList.size() / ROWS_PER_PAGE - 1;
        }
        if (buildingList.isEmpty()) table.setItems(buildingList);
        else {
            if (lastIndex == pageIndex && displace > 0) {
                table.setItems(FXCollections.observableArrayList(buildingList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + displace)));
            } else {
                table.setItems(FXCollections.observableArrayList(buildingList.subList(pageIndex * ROWS_PER_PAGE, pageIndex * ROWS_PER_PAGE + ROWS_PER_PAGE)));
            }
        }
        return table;
    }
    @FXML
    void search() {
        FilteredList<Building> filteredData = new FilteredList<>(buildingList, b -> true);
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
                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Building, Building>, ObservableValue<Building>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

                indexColumn.setCellFactory(new Callback<TableColumn<Building, Building>, TableCell<Building, Building>>() {
                    @Override
                    public TableCell<Building, Building> call(TableColumn<Building, Building> param) {
                        return new TableCell<Building, Building>() {
                            @Override
                            protected void updateItem(Building item, boolean empty) {
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
                address.setCellValueFactory(new PropertyValueFactory<>("address"));
                nApparts.setCellValueFactory(cellData -> {
                    int id = cellData.getValue().getId();
                    return new ReadOnlyObjectWrapper<>(ApartmentQuery.getNumberOfApartmentsByBuildingID(id)).asString();
                });
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
    void filter() {
        FilteredList<Building> filteredData = new FilteredList<>(buildingList, b -> true);
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = filter.getText().toLowerCase();
                if (person.getAddress().toLowerCase().contains(lowerCaseFilter)) {
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
                indexColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Building, Building>, ObservableValue<Building>>) b -> new ReadOnlyObjectWrapper(b.getValue()));

                indexColumn.setCellFactory(new Callback<TableColumn<Building, Building>, TableCell<Building, Building>>() {
                    @Override
                    public TableCell<Building, Building> call(TableColumn<Building, Building> param) {
                        return new TableCell<Building, Building>() {
                            @Override
                            protected void updateItem(Building item, boolean empty) {
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
                address.setCellValueFactory(new PropertyValueFactory<>("address"));
                nApparts.setCellValueFactory(cellData -> {
                    int id = cellData.getValue().getId();
                    return new ReadOnlyObjectWrapper<>(ApartmentQuery.getNumberOfApartmentsByBuildingID(id)).asString();
                });
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
//    @FXML
//    void delete(ActionEvent event) {
//        Owner selectedOwner = table.getSelectionModel().getSelectedItem();
//        if(selectedOwner == null) {
//            createDialog(Alert.AlertType.WARNING,
//                    "Cảnh báo",
//                    "Vui lòng chọn 1 chủ nuôi để tiếp tục", "");
//        }
//        else {
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Xác nhận xóa chủ nuôi");
//            alert.setContentText("Đồng chí muốn xóa chủ nuôi này?");
//            ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
//            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
//            alert.getButtonTypes().setAll(okButton, noButton);
//            alert.showAndWait().ifPresent(type -> {
//                if (type == okButton) {
//                    int ID = selectedOwner.getId();
//                    createDialog(Alert.AlertType.WARNING, "Thông báo", "Có lỗi, thử lại sau!", "");
//                    // TODO: delete owner by id
//                }
//            });
//        }
//
//    }
}


