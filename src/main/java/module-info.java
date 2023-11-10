module com.management.apartment_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;


    opens com.management.apartment_management to javafx.fxml;
    exports com.management.apartment_management;
    exports com.management.apartment_management.Utils;
    exports com.management.apartment_management.Controllers;
    opens com.management.apartment_management.Utils to javafx.fxml;
    opens com.management.apartment_management.Controllers to javafx.fxml;
}