package br.com.clinicapsicologiainfantil.view.pacientes;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.PacienteService;
import br.com.clinicapsicologiainfantil.model.dtos.PacienteDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import static br.com.clinicapsicologiainfantil.model.Constants.*;

@Component
@RequiredArgsConstructor
public class ListarPacientesView implements ApplicationListener<StageReadyEvent> {
    private Stage stage;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final PacienteService service;
    private final ApplicationContext applicationContext;

    @FXML
    private TableView<PacienteDTO> tableListaPacientes;

    @FXML
    private TableColumn<PacienteDTO, Long> colunaId;

    @FXML
    private TableColumn<PacienteDTO, String> colunaNome;

    @FXML
    private TableColumn<PacienteDTO, String> colunaCpf;

    @FXML
    private TableColumn<PacienteDTO, String> colunaEmail;

    @FXML
    private TableColumn<PacienteDTO, String> colunaContato;

    @FXML
    private void fecharListaPacientes() {
        this.stage.close();
    }

    @FXML
    private void novoPaciente() {
        abrirForm(null);
    }

    @FXML
    public void tableViewClicked(MouseEvent mouseEvent) {
        abrirForm(tableListaPacientes.getItems()
                .get(tableListaPacientes
                .getSelectionModel().getSelectedIndex()));
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    public void listarPacientes(Stage stage) {
        this.stage = stage;
        colunaId.setCellValueFactory(new PropertyValueFactory<>("idPaciente"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
        colunaCpf.setCellValueFactory(new PropertyValueFactory<>("cpfPaciente"));
        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("emailPaciente"));
        colunaContato.setCellValueFactory(new PropertyValueFactory<>("contatoPaciente"));
        tableListaPacientes.setItems(populandoTableView());
    }

    private ObservableList<PacienteDTO> populandoTableView() {
        return FXCollections.observableArrayList(service.findAll());
    }

    private void abrirForm(PacienteDTO paciente) {
        try {
            URL resource = getClass().getResource(FXML_CADASTRO_PACIENTE);
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setLocation(resource);
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(parent, WINDOW_SECONDARY_WIDTH, WINDOW_SECONDARY_HEIGHT));
            stage.setTitle(msg.get("titulo.cadastropaciente"));
            stage.getIcons().add(new Image(ICON_APP));
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);

            CadastroPacienteView view = fxmlLoader.getController();
            if (Objects.isNull(paciente))
                view.cadastroNovoPaciente(stage);
            else
                view.alterarPaciente(stage, paciente);

            stage.setOnHidden(event -> tableListaPacientes.setItems(populandoTableView()));
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