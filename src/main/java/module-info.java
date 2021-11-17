module ru.zinyakova.kassa {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires lombok;
    requires com.zaxxer.hikari;
    requires java.sql;

    opens ru.zinyakova.kassa to javafx.fxml;
    exports ru.zinyakova.kassa;
    exports ru.zinyakova.kassa.service.dto;
    exports ru.zinyakova.kassa.view;
    opens ru.zinyakova.kassa.service.dto to javafx.fxml;
    opens ru.zinyakova.kassa.view to javafx.fxml;
}
