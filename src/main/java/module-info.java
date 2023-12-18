module com.management.apartment_management {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.prefs;


    opens com.management.apartment_management to javafx.fxml;
    exports com.management.apartment_management;
    exports com.management.apartment_management.Utils;
    exports com.management.apartment_management.Models;
    opens com.management.apartment_management.Models to javafx.fxml;
    exports com.management.apartment_management.Controllers;
    exports com.management.apartment_management.Controllers.Building;
    exports com.management.apartment_management.Controllers.Tenant;
    exports com.management.apartment_management.Controllers.Payment;
    exports com.management.apartment_management.Controllers.Contract;
    exports com.management.apartment_management.Controllers.Apartment;
    exports com.management.apartment_management.Controllers.Report;

    opens com.management.apartment_management.Utils to javafx.fxml;
    opens com.management.apartment_management.Controllers to javafx.fxml;
    opens com.management.apartment_management.Controllers.Building to javafx.fxml;
    opens com.management.apartment_management.Controllers.Tenant to javafx.fxml;
    opens com.management.apartment_management.Controllers.Payment to javafx.fxml;
    opens com.management.apartment_management.Controllers.Contract to javafx.fxml;
    opens com.management.apartment_management.Controllers.Apartment to javafx.fxml;
    opens com.management.apartment_management.Controllers.Report to javafx.fxml;
}