package br.com.clinicapsicologiainfantil.view.medicos;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.EspecialidadeService;
import br.com.clinicapsicologiainfantil.controller.services.MedicoService;
import br.com.clinicapsicologiainfantil.model.dtos.EspecialidadeDTO;
import br.com.clinicapsicologiainfantil.model.dtos.MedicoDTO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static br.com.clinicapsicologiainfantil.model.Constants.ID_ZERO;

@Component
@RequiredArgsConstructor
public class CadastroMedicoView implements ApplicationListener<StageReadyEvent> {
    private Stage stage;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final MedicoService medicoService;
    private final EspecialidadeService especiliadadeService;

    private String especSelecionada;

    @FXML
    public Button btnExcluir;

    @FXML
    public TextField idTextField;

    @FXML
    public TextField cpfTextField;

    @FXML
    public TextField crmTextField;

    @FXML
    public TextField nomeTextField;

    @FXML
    public TextField emailTextField;

    @FXML
    public TextField contatoTextField;

    @FXML
    public ListView<String> especListView;

    @FXML
    private void fecharCadastroMedico() {
        this.stage.close();
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    public void cadastroNovoMedico(Stage stage) {
        configTextFields(stage);
        idTextField.setText(ID_ZERO);

        especListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                especSelecionada = especListView.getSelectionModel().getSelectedItem();
            }
        });
    }

    public void alterarMedico(Stage stage, MedicoDTO medico) {
        configTextFields(stage);
        this.btnExcluir.setVisible(true);
        this.especSelecionada = medico.getEspecialidadeMedico();

        idTextField.setText(String.valueOf(medico.getIdMedico()));
        cpfTextField.setText(medico.getCpfMedico());
        crmTextField.setText(medico.getCrmMedico());
        nomeTextField.setText(medico.getNomeMedico());
        emailTextField.setText(medico.getEmailMedico());
        contatoTextField.setText(medico.getContatoMedico());
        especListView.getSelectionModel()
                .select(medico.getEspecialidadeMedico());
    }

    public void salvarCadastroMedico() {
        if (StringUtils.isBlank(nomeTextField.getText())) {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.medico.campos.nulos"),
                    msg.get("alerta.medico.campos.nulos.recomendacao"));
        } else {
            long id = Long.parseLong(idTextField.getText());
            MedicoDTO save = medicoService.save(MedicoDTO.builder()
                    .idMedico(id)
                    .nomeMedico(nomeTextField.getText())
                    .crmMedico(crmTextField.getText())
                    .cpfMedico(cpfTextField.getText())
                    .emailMedico(emailTextField.getText())
                    .contatoMedico(contatoTextField.getText())
                    .especialidadeMedico(this.especSelecionada)
                    .build());
            if (Objects.nonNull(save)) {
                if (id == 0) {
                    alerta.mostrar(stage, TipoAlertEnum.INFO,
                            msg.get("alerta.title.info"),
                            msg.get("alerta.medico.header.cadastrado.sucesso"),
                            msg.get("alerta.medico.message.cadastrado.sucesso"));
                    limparCampos();
                } else {
                    alerta.mostrar(stage, TipoAlertEnum.INFO,
                            msg.get("alerta.title.info"),
                            msg.get("alerta.medico.header.alterado.sucesso"),
                            msg.get("alerta.medico.message.alterado.sucesso"));
                }
            } else {
                alerta.mostrar(stage, TipoAlertEnum.WARN,
                        msg.get("alerta.title.aviso"),
                        msg.get("alerta.medico.header.existente"),
                        msg.get("alerta.medico.message.existente"));
            }
        }
    }

    public void excluirMedico() {
        long id = Long.parseLong(idTextField.getText());
        if (medicoService.deleteById(id)) {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.medico.header.excluir.sucesso"),
                    msg.get("alerta.medico.message.excluir.sucesso"));
            fecharCadastroMedico();
        } else {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.medico.header.excluir.erro"),
                    msg.get("alerta.medico.message.excluir.erro"));
        }
    }

    private void popularListView() {
        List<EspecialidadeDTO> especialidades = especiliadadeService.findAll();
        String[] result = new String[especialidades.size()];
        for (int i = 0; i < especialidades.size(); i++) {
            result[i] = especialidades.get(i).getDescricaoEspecialidade();
        }
        especListView.getItems().addAll(result);
    }

    private void limparCampos() {
        idTextField.setText(ID_ZERO);
        nomeTextField.setText("");
        crmTextField.setText("");
        cpfTextField.setText("");
        emailTextField.setText("");
        contatoTextField.setText("");
        especListView.getSelectionModel().select(-1);
    }

    private void configTextFields(Stage stage) {
        this.stage = stage;
        popularListView();

        Pattern nomePattern = Pattern.compile(".{0,100}");
        nomeTextField.setTextFormatter(new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change ->
                nomePattern.matcher(change.getControlNewText()).matches() ? change : null));

        Pattern crmPattern = Pattern.compile(".{0,30}");
        crmTextField.setTextFormatter(new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change ->
                crmPattern.matcher(change.getControlNewText()).matches() ? change : null));

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