package br.com.clinicapsicologiainfantil.view.consultas;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication;
import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.ConsultaService;
import br.com.clinicapsicologiainfantil.model.dtos.ConsultaDTO;
import br.com.clinicapsicologiainfantil.model.dtos.ReceitaDTO;
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
public class ListarConsultasView implements ApplicationListener<ClinicapsicologiainfantilFXApplication.StageReadyEvent>  {
    public TextField pacienteTextField;
    @FXML
    public TableColumn colunaData;
    @FXML
    public TableColumn colunaHora;
    @FXML
    public TableColumn colunaPaciente;
    @FXML
    public TableColumn colunaMedico;
    @FXML
    public TableColumn colunaId;
    public TableView tableListaConsultas;

    private Stage stage;
    private List<ConsultaDTO> consultas;
    private final MessageFactory msg;
    private final ConsultaService service;
    private final Alerta alerta;
    private final AgendamentoConsultaView agendamentoConsultaView;
    private final ConsultaService consultaService;
    private final ApplicationContext applicationContext;

    @FXML
    public void fecharListaConsultas() {
        this.stage.close();
    }

    @FXML
    public void novaConsulta(ActionEvent actionEvent) {
        try {
            URL resource = getClass().getResource(FXML_CADASTRO_CONSULTA);
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setLocation(resource);
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(parent, WINDOW_SECONDARY_WIDTH, WINDOW_SECONDARY_HEIGHT));
            stage.setTitle(msg.get("titulo.cadastroconsulta"));
            stage.getIcons().add(new Image(ICON_APP));
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();

            agendamentoConsultaView.popularListViewPaciente();
            agendamentoConsultaView.popularListViewMedico();
            AgendamentoConsultaView view = fxmlLoader.getController();
            view.cadastroNavaConsulta(stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    public void listaConsultas(Stage stage) {
        this.stage = stage;
    }

    public void pesquisarReceitasPorPaciente() {
        String nomePaciente = pacienteTextField.getText();

        consultas = consultaService.pesquisarConsultasPorPaciente(nomePaciente);

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
                    msg.get("alerta.consulta.listar.header.vazio"),
                    msg.get("alerta.consulta.listar.message.vazio"));
        }
    }

    private ObservableList<ConsultaDTO> populandoTableView() {
        return FXCollections.observableArrayList(consultas);
    }

    public void tableViewClicked(MouseEvent mouseEvent) {

    }
}