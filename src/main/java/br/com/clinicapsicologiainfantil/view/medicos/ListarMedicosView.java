package br.com.clinicapsicologiainfantil.view.medicos;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.MedicoService;
import br.com.clinicapsicologiainfantil.model.dtos.MedicoDTO;
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
import java.util.List;
import java.util.Objects;

import static br.com.clinicapsicologiainfantil.model.Constants.*;

@Component
@RequiredArgsConstructor
public class ListarMedicosView implements ApplicationListener<StageReadyEvent> {
    private Stage stage;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final MedicoService service;
    private final ApplicationContext applicationContext;

    @FXML
    private TableView<MedicoDTO> tableListaMedicos;

    @FXML
    private TableColumn<MedicoDTO, Long> colunaId;

    @FXML
    private TableColumn<MedicoDTO, String> colunaNome;

    @FXML
    private TableColumn<MedicoDTO, String> colunaCrm;

    @FXML
    private TableColumn<MedicoDTO, String> colunaContato;

    @FXML
    private TableColumn<MedicoDTO, String> colunaEspecilidade;

    @FXML
    private void novoMedico() {
        abrirForm(null);
    }

    @FXML
    private void fecharListaMedicos() {
        this.stage.close();
    }

    @FXML
    public void tableViewClicked(MouseEvent mouseEvent) {
        abrirForm(tableListaMedicos.getItems()
                .get(tableListaMedicos
                .getSelectionModel().getSelectedIndex()));
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    private void atualizarListaMedicos() {
        tableListaMedicos.getItems().clear();
        tableListaMedicos.setItems(populandoTableView());
    }

    public void listarMedicos(Stage stage) {
        this.stage = stage;
        colunaId.setCellValueFactory(new PropertyValueFactory<>("idMedico"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));
        colunaCrm.setCellValueFactory(new PropertyValueFactory<>("crmMedico"));
        colunaContato.setCellValueFactory(new PropertyValueFactory<>("contatoMedico"));
        colunaEspecilidade.setCellValueFactory(new PropertyValueFactory<>("especialidadeMedico"));

        tableListaMedicos.setItems(populandoTableView());
    }

    private ObservableList<MedicoDTO> populandoTableView() {
        List<MedicoDTO> medicos = service.findAll();
        return FXCollections.observableArrayList(medicos);
    }

    private void abrirForm(MedicoDTO medico) {
        try {
            URL resource = getClass().getResource(FXML_CADASTRO_MEDICO);
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setLocation(resource);
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(parent, WINDOW_SECONDARY_WIDTH, WINDOW_SECONDARY_HEIGHT));
            stage.setTitle(msg.get("titulo.cadastromedico"));
            stage.getIcons().add(new Image(ICON_APP));
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);

            CadastroMedicoView view = fxmlLoader.getController();
            if (Objects.isNull(medico))
                view.cadastroNovoMedico(stage);
            else
                view.alterarMedico(stage, medico);

            stage.setOnHidden(event -> atualizarListaMedicos());
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