package br.com.clinicapsicologiainfantil.view.receitas;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.commons.pdf.GerarPdf;
import br.com.clinicapsicologiainfantil.controller.services.MedicoService;
import br.com.clinicapsicologiainfantil.controller.services.PacienteService;
import br.com.clinicapsicologiainfantil.controller.services.ReceitaService;
import br.com.clinicapsicologiainfantil.model.dtos.MedicoDTO;
import br.com.clinicapsicologiainfantil.model.dtos.PacienteDTO;
import br.com.clinicapsicologiainfantil.model.dtos.ReceitaDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static br.com.clinicapsicologiainfantil.model.Constants.ID_ZERO;

@Component
@RequiredArgsConstructor
public class CadastroReceitaView implements ApplicationListener<StageReadyEvent>{
    private Stage stage;
    private final Alerta alerta;
    private final GerarPdf gerarPdf;
    private final MessageFactory msg;
    private final MedicoService medicoService;
    private final ReceitaService receitaService;
    private final PacienteService pacienteService;

    private String nomeMedico;

    private String nomePaciente;

    @FXML
    public Button btnImprimir;

    @FXML
    public TextField idTextField;

    @FXML
    public TextField pacienteTextField;

    @FXML
    public TextField medicoTextField;

    @FXML
    public TextArea prescricaoTextArea;

    @FXML
    public ListView<String> pacienteListView;

    @FXML
    public ListView<String> medicoListView;

    @FXML
    public void fecharReceita() {
        this.stage.close();
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    public void cadastroNovaReceita(Stage stage) {
        configTextFields(stage);
        idTextField.setText(ID_ZERO);
        pacienteListView.setVisible(true);
        pacienteTextField.setVisible(false);
        medicoListView.setVisible(true);
        medicoTextField.setVisible(false);
        btnImprimir.setVisible(false);

        pacienteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                nomePaciente = pacienteListView.getSelectionModel().getSelectedItem();
            }
        });

        medicoListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                nomeMedico = medicoListView.getSelectionModel().getSelectedItem();
            }
        });

        popularMedicosListView();
        popularPacientesListView();
    }

    public void visualizarReceita(Stage stage, ReceitaDTO receita) {
        configTextFields(stage);
        pacienteListView.setVisible(false);
        pacienteTextField.setVisible(true);
        medicoListView.setVisible(false);
        medicoTextField.setVisible(true);

        idTextField.setText(String.valueOf(receita.getIdReceita()));
        pacienteTextField.setText(receita.getNomePaciente());
        medicoTextField.setText(receita.getNomeMedico());
        prescricaoTextArea.setText(receita.getPrescricao());
    }

    public void popularPacientesListView() {
        List<PacienteDTO> pacientes = pacienteService.findAll();
        String[] result = new String[pacientes.size()];
        for (int i = 0; i < pacientes.size(); i++) {
            result[i] = pacientes.get(i).getNomePaciente();
        }
        pacienteListView.getItems().addAll(result);
    }

    public void popularMedicosListView() {
        List<MedicoDTO> medicos = medicoService.findAll();
        String[] result = new String[medicos.size()];
        for (int i = 0; i < medicos.size(); i++) {
            result[i] = medicos.get(i).getNomeMedico();
        }
        medicoListView.getItems().addAll(result);
    }

    public void imprimirReceita() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(msg.get("chooser.title"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos PDF", "*.pdf"));

        File file = fileChooser.showSaveDialog(stage);
        if (Objects.nonNull(file)) {
            gerarPdf.criar(
                    pacienteListView.getSelectionModel().getSelectedItem(),
                    medicoListView.getSelectionModel().getSelectedItem(),
                    prescricaoTextArea.getText(), file.getAbsolutePath());
        }
    }

    public void salvarReceita() {
        if (StringUtils.isBlank(prescricaoTextArea.getText())) {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.receita.campos.nulos"),
                    msg.get("alerta.receita.campos.nulos.recomendacao"));
        } else {
            long id = Long.parseLong(idTextField.getText());
            receitaService.save(ReceitaDTO.builder()
                    .idReceita(Long.parseLong(idTextField.getText()))
                    .nomeMedico(Objects.isNull(nomeMedico) ? medicoTextField.getText() : nomeMedico)
                    .nomePaciente(Objects.isNull(nomePaciente) ? pacienteTextField.getText() : nomePaciente)
                    .data(LocalDate.now())
                    .hora(LocalDateTime.now())
                    .prescricao(prescricaoTextArea.getText())
                    .build());
            btnImprimir.setVisible(true);
            if (id == 0) {
                alerta.mostrar(stage, TipoAlertEnum.WARN,
                        msg.get("alerta.title.info"),
                        msg.get("alerta.receita.header.cadastrado.sucesso"),
                        msg.get("alerta.receita.message.cadastrado.sucesso"));
                limparCampos();
            } else {
                alerta.mostrar(stage, TipoAlertEnum.WARN,
                        msg.get("alerta.title.info"),
                        msg.get("alerta.receita.header.alterado.sucesso"),
                        msg.get("alerta.receita.message.alterado.sucesso"));
            }
        }
    }

    private void limparCampos() {
        idTextField.setText(ID_ZERO);
        prescricaoTextArea.setText("");
        medicoListView.getSelectionModel().select(-1);;
        pacienteListView.getSelectionModel().select(-1);
    }

    private void configTextFields(Stage stage) {
        this.stage = stage;

        Pattern prescricaoPattern = Pattern.compile(".{0,255}");
        prescricaoTextArea.setTextFormatter(new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change ->
                prescricaoPattern.matcher(change.getControlNewText()).matches() ? change : null));
    }
}