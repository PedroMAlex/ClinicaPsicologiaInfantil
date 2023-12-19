package br.com.clinicapsicologiainfantil.view;

import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class SobreView {
    private Stage stage;

    public void mostrarSobre(Stage stage) {
        this.stage = stage;
    }

    public void fecharSobre() {
        this.stage.close();
    }
}
