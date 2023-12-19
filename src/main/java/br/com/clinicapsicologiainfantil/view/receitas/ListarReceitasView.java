package br.com.clinicapsicologiainfantil.view.receitas;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.ReceitaService;
import br.com.clinicapsicologiainfantil.model.dtos.PacienteDTO;
import br.com.clinicapsicologiainfantil.model.dtos.ReceitaDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static br.com.clinicapsicologiainfantil.model.Constants.*;

@Component
@RequiredArgsConstructor
public class ListarReceitasView implements ApplicationListener<StageReadyEvent>  {
    private Stage stage;
    private List<ReceitaDTO> receitas;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final ReceitaService service;
    private final ApplicationContext applicationContext;

    @FXML
    public Button btnPesquisarPaciente;

    @FXML
    private TextField pacienteTextField;

    @FXML
    private TableView<ReceitaDTO> tableListaReceitas;

    @FXML
    private TableColumn<PacienteDTO, Long> colunaId;

    @FXML
    private TableColumn<ReceitaDTO, LocalDate> colunaData;

    @FXML
    private TableColumn<ReceitaDTO, String> colunaPaciente;

    @FXML
    private TableColumn<ReceitaDTO, String> colunaMedico;

    @FXML
    public void fecharListaReceitas() {
        this.stage.close();
    }

    @FXML
    public void novaReceita() {
        abrirForm(null);
    }

    @FXML
    public void tableViewClicked(MouseEvent mouseEvent) {
        abrirForm(tableListaReceitas.getItems()
                .get(tableListaReceitas
                .getSelectionModel().getSelectedIndex()));
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    private void abrirForm(ReceitaDTO receita) {
        try {
            URL resource = getClass().getResource(FXML_CADASTRO_RECEITA);
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setLocation(resource);
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(parent, WINDOW_SECONDARY_WIDTH, WINDOW_SECONDARY_HEIGHT));
            stage.setTitle(msg.get("titulo.cadastroreceita"));
            stage.getIcons().add(new Image(ICON_APP));
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);

            CadastroReceitaView view = fxmlLoader.getController();
            if (Objects.isNull(receita))
                view.cadastroNovaReceita(stage);
            else
                view.visualizarReceita(stage, receita);

            stage.setOnHidden(event -> tableListaReceitas.setItems(populandoTableView()));
            stage.show();
        } catch (IOException e) {
            alerta.mostrar(stage, TipoAlertEnum.ERROR,
                    msg.get("alerta.title.erro"),
                    msg.get("alerta.header.erro"),
                    e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void listaReceitas(Stage stage) {
        this.stage = stage;
    }

    public void pesquisarReceitasPorPaciente() {
        if (StringUtils.isBlank(pacienteTextField.getText())) {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.paciente.header.pesquisar.vazio"),
                    msg.get("alerta.paciente.message.pesquisar.vazio"));
        }

        receitas = service.pesquisarReceitasPorPaciente(pacienteTextField.getText());
        if (Objects.nonNull(receitas)) {
            colunaId.setCellValueFactory(new PropertyValueFactory<>("idReceita"));
            colunaData.setCellValueFactory(new PropertyValueFactory<>("hora"));
            colunaPaciente.setCellValueFactory(new PropertyValueFactory<>("nomePaciente"));
            colunaMedico.setCellValueFactory(new PropertyValueFactory<>("nomeMedico"));
            tableListaReceitas.setItems(populandoTableView());
        } else {
            alerta.mostrar(stage, TipoAlertEnum.WARN,
                    msg.get("alerta.title.aviso"),
                    msg.get("alerta.receita.listar.header.vazio"),
                    msg.get("alerta.receita.listar.message.vazio"));
        }
    }

    private ObservableList<ReceitaDTO> populandoTableView() {
        return FXCollections.observableArrayList(receitas);
    }
}