package br.com.clinicapsicologiainfantil.view.consultas;

import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.ConsultaService;
import br.com.clinicapsicologiainfantil.controller.services.MedicoService;
import br.com.clinicapsicologiainfantil.model.dtos.ConsultaDTO;
import br.com.clinicapsicologiainfantil.model.dtos.MedicoDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AtenderConsultaView {
    private Stage stage;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final ConsultaService consultaService;

    private ConsultaDTO consulta;

    @FXML
    public TextField idTextField;

    @FXML
    public TextField medicoTextField;

    @FXML
    public TextField pacienteTextField;

    @FXML
    public TextArea devolutivaTextArea;

    @FXML
    public TableColumn<ConsultaDTO, Long> colunaId;

    @FXML
    public TableColumn<ConsultaDTO, String> colunaData;

    @FXML
    public TableColumn<ConsultaDTO, String> colunaHora;

    @FXML
    public TableColumn<ConsultaDTO, String> colunaPaciente;

    @FXML
    public TableColumn<ConsultaDTO, String> colunaMedico;

    @FXML
    public TableView<ConsultaDTO> tableListaConsultas;

    public void pesquisarConsultasPorPaciente(ActionEvent actionEvent) {}

    public void salvarAtendimentoConsulta(ActionEvent actionEvent) {
        configTextFields(stage);
    }

    public void fecharAtenderConsulta(ActionEvent actionEvent) {}

    public void atenderConsulta(Stage stage) {
        configTextFields(stage);

        idTextField.setText(String.valueOf(consulta.getIdMedico()));
        medicoTextField.setText(consulta.getNomeMedico());
        pacienteTextField.setText(consulta.getNomePaciente());
        devolutivaTextArea.setText(consulta.getNomeMedico());
    }

    private void configTextFields(Stage stage) {
        this.stage = stage;
    }
}