package br.com.clinicapsicologiainfantil.view;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.view.consultas.ListarConsultasAgendadasView;
import br.com.clinicapsicologiainfantil.view.consultas.ListarConsultasView;
import br.com.clinicapsicologiainfantil.view.especialidades.ListarEspecialidadesView;
import br.com.clinicapsicologiainfantil.view.medicos.ListarMedicosView;
import br.com.clinicapsicologiainfantil.view.pacientes.ListarPacientesView;
import br.com.clinicapsicologiainfantil.view.receitas.ListarReceitasView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static br.com.clinicapsicologiainfantil.model.Constants.*;

@Component
@RequiredArgsConstructor
public class MainView implements ApplicationListener<StageReadyEvent> {
    private final Alerta alerta;
    private final MessageFactory msg;
    private final ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    @FXML
    private void mostrarListaPacientes() {
        FXMLLoader fxmlLoader = carregarFxml(FXML_LISTAR_PACIENTES);
        Stage stage = carregarStage(fxmlLoader, msg.get("titulo.listarpacientes"));
        ListarPacientesView view = fxmlLoader.getController();
        view.listarPacientes(stage);
    }

    @FXML
    private void mostrarListaMedicos() {
        FXMLLoader fxmlLoader = carregarFxml(FXML_LISTAR_MEDICOS);
        Stage stage = carregarStage(fxmlLoader, msg.get("titulo.listarmedicos"));
        ListarMedicosView view = fxmlLoader.getController();
        view.listarMedicos(stage);
    }

    @FXML
    private void mostrarListaEspecialidades() {
        FXMLLoader fxmlLoader = carregarFxml(FXML_LISTAR_ESPECILIDADES);
        Stage stage = carregarStage(fxmlLoader, msg.get("titulo.listarespecilidades"));
        ListarEspecialidadesView view = fxmlLoader.getController();
        view.listarEspecilidades(stage);
    }

    @FXML
    public void mostrarListaConsultas() {
        FXMLLoader fxmlLoader = carregarFxml(FXML_LISTAR_CONSULTAS);
        Stage stage = carregarStage(fxmlLoader, msg.get("titulo.listarconsultas"));
        ListarConsultasView view = fxmlLoader.getController();
        view.listaConsultas(stage);
    }

    @FXML
    public void atenderConsulta() {
        FXMLLoader fxmlLoader = carregarFxml(FXML_LISTAR_CONSULTAS_AGENDADAS);
        Stage stage = carregarStage(fxmlLoader, msg.get("titulo.listarconsultasagendadas"));
        ListarConsultasAgendadasView view = fxmlLoader.getController();
        view.listaConsultas(stage);
    }

    @FXML
    public void mostrarListaReceitas() {
        FXMLLoader fxmlLoader = carregarFxml(FXML_LISTAR_RECEITAS);
        Stage stage = carregarStage(fxmlLoader, msg.get("titulo.listarreceitas"));
        ListarReceitasView view = fxmlLoader.getController();
        view.listaReceitas(stage);
    }

    @FXML
    public void mostrarSobre() {
        FXMLLoader fxmlLoader = carregarFxml(FXML_SOBRE);
        Stage stage = carregarStage(fxmlLoader, msg.get("titulo.sobre"));
        SobreView view = fxmlLoader.getController();
        view.mostrarSobre(stage);
    }

    private FXMLLoader carregarFxml(String fxml) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxml));
        fxmlLoader.setControllerFactory(applicationContext::getBean);
        fxmlLoader.setLocation(getClass().getResource(fxml));
        return fxmlLoader;
    }

    private Stage carregarStage(FXMLLoader fxmlLoader, String titulo) {
        try {
            Parent parent = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent, WINDOW_SECONDARY_WIDTH, WINDOW_SECONDARY_HEIGHT));
            stage.getIcons().add(new Image(ICON_APP));
            stage.setTitle(titulo);
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            return stage;
        } catch (IOException e) {
            alerta.mostrar(new Stage(), TipoAlertEnum.ERROR,
                    msg.get("alerta.title.erro"),
                    msg.get("alerta.header.erro"),
                    e.getMessage());
            throw new RuntimeException(e);
        }
    }
}