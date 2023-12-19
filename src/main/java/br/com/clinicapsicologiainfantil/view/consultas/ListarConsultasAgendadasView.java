package br.com.clinicapsicologiainfantil.view.consultas;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.ConsultaService;
import br.com.clinicapsicologiainfantil.model.dtos.ConsultaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
public class ListarConsultasAgendadasView implements ApplicationListener<StageReadyEvent>  {
    private Stage stage;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final ConsultaService consultaService;
    private final ApplicationContext applicationContext;

    private List<ConsultaDTO> consultas;

    @FXML
    public TableColumn<ConsultaDTO, String> colunaId;

    @FXML
    public TextField medicoTextField;

    @FXML
    public TableColumn<ConsultaDTO, Long> colunaData;

    @FXML
    public TableColumn<ConsultaDTO, String> colunaHora;

    @FXML
    public TableColumn<ConsultaDTO, String> colunaPaciente;

    @FXML
    public TableColumn<ConsultaDTO, String> colunaMedico;

    @FXML
    public TableView<ConsultaDTO> tableListaConsultas;

    @FXML
    public void fecharListaConsultas() {
        this.stage.close();
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    public void listaConsultas(Stage stage) {
        this.stage = stage;
    }

    public void pesquisarConsultasAgendadasPorMedico() {
        String nomeMedico = medicoTextField.getText();

        consultas = consultaService.pesquisarConsultasPorMedico(nomeMedico);

        if (Objects.nonNull(consultas)) {
            colunaId.setCellValueFactory(new PropertyValueFactory<>("idConsulta"));
            colunaData.setCellValueFactory(new PropertyValueFactory<>("dataConsulta"));
            colunaHora.setCellValueFactory(new PropertyValueFactory<>("horaConsulta"));
            colunaPaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
            colunaMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));
            tableListaConsultas.setItems(populandoTableView());
        } else {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.consulta.header.listar.vazio"),
                    msg.get("alerta.consulta.message.listar.vazio"));
        }
    }

    private ObservableList populandoTableView() {
        return FXCollections.observableArrayList(consultas);
    }

    public void tableViewClicked(MouseEvent mouseEvent) {
        abrirForm(tableListaConsultas.getItems()
                .get(tableListaConsultas
                .getSelectionModel().getSelectedIndex()));
    }

    public void pesquisarConsultasPorMedico(ActionEvent actionEvent) {
        pesquisarConsultasAgendadasPorMedico();
    }

    private void abrirForm(ConsultaDTO consulta) {
        try {
            URL resource = getClass().getResource(FXML_ATENDER_CONSULTA);
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setLocation(resource);
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(parent, WINDOW_SECONDARY_WIDTH, WINDOW_SECONDARY_HEIGHT));
            stage.setTitle(msg.get("titulo.atenderconsulta"));
            stage.getIcons().add(new Image(ICON_APP));
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);

            AtenderConsultaView view = fxmlLoader.getController();
            view.atenderConsulta(stage);
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