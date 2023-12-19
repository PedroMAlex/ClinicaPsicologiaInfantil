package br.com.clinicapsicologiainfantil.view.pacientes;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.PacienteService;
import br.com.clinicapsicologiainfantil.model.dtos.PacienteDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static br.com.clinicapsicologiainfantil.model.Constants.ID_ZERO;

@Component
@RequiredArgsConstructor
public class CadastroPacienteView implements ApplicationListener<StageReadyEvent> {
    private Stage stage;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final PacienteService service;

    @FXML
    public Button btnExcluir;

    @FXML
    public TextField idTextField;

    @FXML
    public TextField nomeTextField;

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField cpfTextField;

    @FXML
    public TextField contatoTextField;

    @FXML
    private void fecharCadastroPaciente() {
        this.stage.close();
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    public void cadastroNovoPaciente(Stage stage) {
        configTextFields(stage);
        idTextField.setText(ID_ZERO);
    }

    public void alterarPaciente(Stage stage, PacienteDTO paciente) {
        configTextFields(stage);
        this.btnExcluir.setVisible(true);
        idTextField.setText(String.valueOf(paciente.getIdPaciente()));
        cpfTextField.setText(paciente.getCpfPaciente());
        nomeTextField.setText(paciente.getNomePaciente());
        emailTextField.setText(paciente.getEmailPaciente());
        contatoTextField.setText(paciente.getContatoPaciente());
    }

    public void salvarCadastroPaciente(ActionEvent actionEvent) {
        if (StringUtils.isBlank(nomeTextField.getText())) {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.paciente.campos.nulos"),
                    msg.get("alerta.paciente.campos.nulos.recomendacao"));
        } else {
            long id = Long.parseLong(idTextField.getText());
            var save = service.save(PacienteDTO.builder()
                    .idPaciente(id)
                    .nomePaciente(nomeTextField.getText())
                    .cpfPaciente(cpfTextField.getText())
                    .emailPaciente(emailTextField.getText())
                    .contatoPaciente(contatoTextField.getText())
                    .build());
            if (Objects.nonNull(save)) {
                if (id == 0) {
                    alerta.mostrar(stage, TipoAlertEnum.WARN,
                            msg.get("alerta.title.info"),
                            msg.get("alerta.paciente.header.cadastrado.sucesso"),
                            msg.get("alerta.paciente.message.cadastrado.sucesso"));
                    limparCampos();
                } else {
                    alerta.mostrar(stage, TipoAlertEnum.WARN,
                            msg.get("alerta.title.info"),
                            msg.get("alerta.paciente.header.alterado.sucesso"),
                            msg.get("alerta.paciente.message.alterado.sucesso"));
                }
            } else {
                alerta.mostrar(stage, TipoAlertEnum.WARN,
                        msg.get("alerta.title.aviso"),
                        msg.get("alerta.paciente.header.existente"),
                        msg.get("alerta.paciente.message.existente"));
            }
        }
    }

    public void excluirPaciente() {
        long id = Long.parseLong(idTextField.getText());
        if (service.deleteById(id)) {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.medico.header.excluir.sucesso"),
                    msg.get("alerta.medico.message.excluir.sucesso"));
            fecharCadastroPaciente();
        } else {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.medico.header.excluir.erro"),
                    msg.get("alerta.medico.message.excluir.erro"));
        }
    }

    private void limparCampos() {
        idTextField.setText(ID_ZERO);
        nomeTextField.setText("");
        cpfTextField.setText("");
        emailTextField.setText("");
        contatoTextField.setText("");
    }

    private void configTextFields(Stage stage) {
        this.stage = stage;

        Pattern nomePattern = Pattern.compile(".{0,100}");
        nomeTextField.setTextFormatter(new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change ->
                nomePattern.matcher(change.getControlNewText()).matches() ? change : null));

        Pattern patternCpf = Pattern.compile(".{0,11}");
        cpfTextField.setTextFormatter(new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change ->
                patternCpf.matcher(change.getControlNewText()).matches() ? change : null));

        Pattern patternEmail = Pattern.compile(".{0,50}");
        emailTextField.setTextFormatter(new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change ->
                patternEmail.matcher(change.getControlNewText()).matches() ? change : null));

        Pattern patternContato = Pattern.compile(".{0,15}");
        contatoTextField.setTextFormatter(new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change ->
                patternContato.matcher(change.getControlNewText()).matches() ? change : null));
    }
}