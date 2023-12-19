package br.com.clinicapsicologiainfantil.view.especialidades;

import br.com.clinicapsicologiainfantil.ClinicapsicologiainfantilFXApplication.StageReadyEvent;
import br.com.clinicapsicologiainfantil.controller.commons.alerts.Alerta;
import br.com.clinicapsicologiainfantil.controller.commons.enums.TipoAlertEnum;
import br.com.clinicapsicologiainfantil.controller.commons.messages.MessageFactory;
import br.com.clinicapsicologiainfantil.controller.services.EspecialidadeService;
import br.com.clinicapsicologiainfantil.model.dtos.EspecialidadeDTO;
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
import java.util.Objects;

import static br.com.clinicapsicologiainfantil.model.Constants.*;

@Component
@RequiredArgsConstructor
public class ListarEspecialidadesView implements ApplicationListener<StageReadyEvent> {
    private Stage stage;
    private final Alerta alerta;
    private final MessageFactory msg;
    private final EspecialidadeService service;
    private final ApplicationContext applicationContext;

    @FXML
    private TableView<EspecialidadeDTO> tableListaEspecialidades;

    @FXML
    private TableColumn<EspecialidadeDTO, Long> colunaId;

    @FXML
    private TableColumn<EspecialidadeDTO, String> colunaDescricao;

    @FXML
    private void fecharListaEspecialidades() {
        this.stage.close();
    }

    @FXML
    private void novaEspecilidade() {
        abrirForm(null);
    }

    @FXML
    public void tableViewClicked(MouseEvent mouseEvent) {
        abrirForm(tableListaEspecialidades.getItems()
                .get(tableListaEspecialidades
                        .getSelectionModel().getSelectedIndex()));
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {}

    public void listarEspecilidades(Stage stage) {
        this.stage = stage;
        colunaId.setCellValueFactory(new PropertyValueFactory<>("idEspecialidade"));
        colunaDescricao.setCellValueFactory(new PropertyValueFactory<>("descricaoEspecialidade"));

        tableListaEspecialidades.setItems(populandoTableView());
    }

    private ObservableList<EspecialidadeDTO> populandoTableView() {
        return FXCollections.observableArrayList(service.findAll());
    }

    private void abrirForm(EspecialidadeDTO especialidade) {
        try {
            URL resource = getClass().getResource(FXML_CADASTRO_ESPECILIDADE);
            FXMLLoader fxmlLoader = new FXMLLoader(resource);
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            fxmlLoader.setLocation(resource);
            Parent parent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(parent, WINDOW_SECONDARY_WIDTH, WINDOW_SECONDARY_HEIGHT));
            stage.setTitle(msg.get("titulo.cadastroespecialidade"));
            stage.getIcons().add(new Image(ICON_APP));
            stage.setAlwaysOnTop(true);
            stage.initModality(Modality.APPLICATION_MODAL);

            CadastroEspecialidadeView view = fxmlLoader.getController();
            if (Objects.isNull(especialidade))
                view.cadastroNovaEspecilidade(stage);
            else
                view.alterarEspecialidade(stage, especialidade);

            stage.setOnHidden(event -> tableListaEspecialidades.setItems(populandoTableView()));
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