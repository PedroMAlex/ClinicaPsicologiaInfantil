package br.com.clinicapsicologiainfantil.controller.commons.alerts;

import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class Alerta {
    public void mostrar(Stage stage, TipoAlertEnum type, String title, String header, String message) {
        Alert alert = switch (type.getValue()) {
            case 0 -> new Alert(Alert.AlertType.INFORMATION);
            case 1 -> new Alert(Alert.AlertType.WARNING);
            default -> new Alert(Alert.AlertType.ERROR);
        };

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
        alert.show();
    }
}