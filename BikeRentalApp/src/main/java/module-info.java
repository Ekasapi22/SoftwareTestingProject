module org.rentandroll.mrabschlussprojekt {

    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires org.mariadb.jdbc;
    requires org.mockito;
    requires junit;
    requires org.testfx;
    requires org.testfx.junit;


    opens org.mr.abschlussprojekt.bikeRental.model to javafx.base;
    opens org.mr.abschlussprojekt.bikeRental to javafx.fxml;
    exports org.mr.abschlussprojekt.bikeRental;
    exports org.mr.abschlussprojekt.bikeRental.gui;
    opens org.mr.abschlussprojekt.bikeRental.gui to javafx.fxml;

}