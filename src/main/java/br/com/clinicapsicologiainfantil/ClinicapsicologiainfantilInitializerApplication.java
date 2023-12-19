package br.com.clinicapsicologiainfantil;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static br.com.clinicapsicologiainfantil.model.Constants.*;

@Component
@RequiredArgsConstructor
public class ClinicapsicologiainfantilInitializerApplication implements ApplicationListener<StageReadyEvent> {
    private Stage stage;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final ApplicationContext applicationContext;

    @FXML
    private ImageView imgBackgroud;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FXML_MAIN));
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();

            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, WINDOW_PRIMARY_WIDTH, WINDOW_PRIMARY_HEIGHT));
            stage.setTitle(msg.get("titulo.aplicacao"));
            stage.getIcons().add(new Image(ICON_APP));
            stage.show();
        } catch (IOException e) {
            alerta.mostrar(stage, TipoAlertEnum.ERROR,
                    msg.get("alerta.title.erro"),
                    msg.get("alerta.header.erro"),
                    e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
