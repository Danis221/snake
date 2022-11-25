module ru.itis.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.itis.demo to javafx.fxml;
    exports ru.itis.demo;
}