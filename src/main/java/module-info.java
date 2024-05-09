module org.caro.caroserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.caro.caroserver to javafx.fxml;
    exports org.caro.caroserver;
    exports controller;
    exports dao;
}