package br.com.clinicapsicologiainfantil.view.consultas;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.ConsultaService;
import br.com.clinicapsicologiainfantil.controller.services.MedicoService;
import br.com.clinicapsicologiainfantil.controller.services.PacienteService;
import br.com.clinicapsicologiainfantil.model.dtos.ConsultaDTO;
import br.com.clinicapsicologiainfantil.model.dtos.MedicoDTO;
import br.com.clinicapsicologiainfantil.model.dtos.PacienteDTO;
import io.micrometer.common.util.StringUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static br.com.clinicapsicologiainfantil.model.Constants.*;

@Component
@RequiredArgsConstructor
public class AgendamentoConsultaView implements ApplicationListener<StageReadyEvent> {
    private Stage stage;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final PacienteService pacienteService;
    private final ConsultaService consultaService;
    private final MedicoService medicoService;

    public ListView<String> pacienteListView;
    public ListView<String> medicoListView;

    @FXML
    public ComboBox horaComboBox;

    @FXML
    public GridPane consultas;

    @FXML
    public DatePicker datePicker;

    @FXML
    public TextField idTextField;

    @FXML
    public ComboBox<String> minutosComboBox;

    @FXML
    public void fecharConsulta() {
        this.stage.close();
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    public void popularListViewPaciente() {
        List<PacienteDTO> pacientes = pacienteService.findAll();
        String[] result = new String[pacientes.size()];
        for (int i = 0; i < pacientes.size(); i++) {
            result[i] = pacientes.get(i).getNomePaciente();
        }
        pacienteListView.getItems().addAll(result);
    }

    public void popularListViewMedico() {
        List<MedicoDTO> medicos = medicoService.findAll();
        String[] result = new String[medicos.size()];
        for (int i = 0; i < medicos.size(); i++) {
            result[i] = medicos.get(i).getNomeMedico();
        }
        medicoListView.getItems().addAll(result);
    }

    public void cadastroNavaConsulta(Stage stage) {
        configTextFields(stage);
        idTextField.setText(ID_ZERO);
        pacienteListView.getSelectionModel().clearSelection();
        medicoListView.getSelectionModel().clearSelection();
        datePicker.setValue(LocalDate.now());
        horaComboBox.getSelectionModel().clearSelection();

        pacienteListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {});

        medicoListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {});
    }


    public void salvarAgendamentoConsulta() {
        if (StringUtils.isBlank(pacienteListView.getSelectionModel().getSelectedItem())
                || StringUtils.isBlank(medicoListView.getSelectionModel().getSelectedItem())
                || StringUtils.isBlank(String.valueOf(datePicker)) || StringUtils.isBlank(String.valueOf(horaComboBox)) ) {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.consulta.campos.nulos"),
                    msg.get("alerta.consulta.campos.nulos.recomendacao"));
        } else {
            long id = Long.parseLong(idTextField.getText());
            String dataSelecionada = String.valueOf(datePicker.getValue());
            String horaSelecionada = String.valueOf(horaComboBox.getSelectionModel().getSelectedItem());

            ConsultaDTO save = consultaService.save(ConsultaDTO.builder()
                    .idConsulta(id)
                    .nomePaciente(pacienteListView.getSelectionModel().getSelectedItem())
                    .nomeMedico(medicoListView.getSelectionModel().getSelectedItem())
                    .dataConsulta(LocalDate.parse(dataSelecionada))
                    .horaConsulta(horaSelecionada)
                    .build());
            if (Objects.nonNull(save)) {
                if (id == 0) {
                    alerta.mostrar(stage, TipoAlertEnum.INFO,
                            msg.get("alerta.title.info"),
                            msg.get("alerta.consulta.header.cadastrado.sucesso"),
                            msg.get("alerta.consulta.message.cadastrado.sucesso"));
                    limparCampos();
                } else {
                    alerta.mostrar(stage, TipoAlertEnum.INFO,
                            msg.get("alerta.title.info"),
                            msg.get("alerta.consulta.header.alterado.sucesso"),
                            msg.get("alerta.consulta.message.alterado.sucesso"));
                }
            }
        }
    }

//    public void alterarConsulta(Stage stage, ConsultaDTO consulta) {
//        configTextFields(stage);
//        this.btnExcluir.setVisible(true);
//
//        idTextField.setText(String.valueOf(consulta.getIdConsulta()));
//        pacienteListView.getSelectionModel().select(consulta.getNomePaciente()); // Seleciona o paciente na lista
//        medicoListView.getSelectionModel().select(consulta.getNomeMedico()); // Seleciona o médico na lista
//        datePicker.setValue(LocalDate.parse(consulta.getData())); // Define a data no DatePicker
//
//    }

    @FXML
    private void fecharCadastroConsulta() {
        this.stage.close();
    }

    private void limparCampos() {

    }
    private void configTextFields(Stage stage) {
        this.stage = stage;
    }

    public void mostrarAtenderConsulta(Stage stage) {
        this.stage = stage;
    }
}

