package br.com.clinicapsicologiainfantil.view.especialidades;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.EspecialidadeService;
import br.com.clinicapsicologiainfantil.model.dtos.EspecialidadeDTO;
import br.com.clinicapsicologiainfantil.model.dtos.MedicoDTO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static br.com.clinicapsicologiainfantil.model.Constants.ID_ZERO;

@Component
@RequiredArgsConstructor
public class CadastroEspecialidadeView implements ApplicationListener<StageReadyEvent> {
    private Stage stage;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final EspecialidadeService service;

    @FXML
    public Button btnExcluir;

    @FXML
    public TextField idTextField;

    @FXML
    public TextField descricaoTextField;

    @FXML
    public void fecharCadastroEspecialidade() {
        this.stage.close();
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    public void cadastroNovaEspecilidade(Stage stage) {
        configTextFields(stage);
        idTextField.setText(ID_ZERO);
    }

    public void alterarEspecialidade(Stage stage, EspecialidadeDTO especialidade) {
        configTextFields(stage);
        this.btnExcluir.setVisible(true);
        idTextField.setText(String.valueOf(especialidade.getIdEspecialidade()));
        descricaoTextField.setText(especialidade.getDescricaoEspecialidade());
    }

    public void excluirEspecialidade() {
        if (service.deleteById(Long.parseLong(idTextField.getText()))) {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.especilidade.header.excluir.sucesso"),
                    msg.get("alerta.especilidade.message.excluir.sucesso"));
            fecharCadastroEspecialidade();
        } else {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.especilidade.header.excluir.erro"),
                    msg.get("alerta.especilidade.message.excluir.erro"));
        }
    }

    public void salvaCadastroEspecialidade(ActionEvent actionEvent) {
        long id = Long.parseLong(idTextField.getText());
        EspecialidadeDTO save = service.save(EspecialidadeDTO.builder()
                .idEspecialidade(id)
                .descricaoEspecialidade(descricaoTextField.getText())
                .build());
        if (Objects.nonNull(save)) {
            if (id == 0) {
                descricaoTextField.setText("");
                alerta.mostrar(stage, TipoAlertEnum.INFO,
                        msg.get("alerta.title.info"),
                        msg.get("alerta.especilidade.header.cadastrada.sucesso"),
                        msg.get("alerta.especilidade.message.cadastrada.sucesso"));
            } else {
                alerta.mostrar(stage, TipoAlertEnum.INFO,
                        msg.get("alerta.title.info"),
                        msg.get("alerta.especilidade.header.alterada.sucesso"),
                        msg.get("alerta.especilidade.message.alterada.sucesso"));
            }
        } else {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.especilidade.header.existente"),
                    msg.get("alerta.especilidade.message.existente"));
        }
    }

    private void configTextFields(Stage stage) {
        this.stage = stage;

        Pattern descricaoPattern = Pattern.compile(".{0,50}");
        descricaoTextField.setTextFormatter(new TextFormatter<>((UnaryOperator<TextFormatter.Change>) change ->
                descricaoPattern.matcher(change.getControlNewText()).matches() ? change : null));
    }
}