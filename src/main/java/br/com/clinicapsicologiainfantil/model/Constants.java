package br.com.clinicapsicologiainfantil.model;

import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;

public interface Constants {
    int WINDOW_PRIMARY_WIDTH = 800;
    int WINDOW_PRIMARY_HEIGHT = 600;
    int WINDOW_SECONDARY_WIDTH = 750;
    int WINDOW_SECONDARY_HEIGHT = 500;
    String ID_ZERO = "0";
    String ICON_APP_PDF = "./images/icon.png";
    String ICON_APP = "/br/com/clinicapsicologiainfantil/images/icon.png";
    String FXML_MAIN = "/br/com/clinicapsicologiainfantil/fxml/main.fxml";
    String FXML_SOBRE = "/br/com/clinicapsicologiainfantil/fxml/sobre.fxml";
    String FXML_LISTAR_PACIENTES = "/br/com/clinicapsicologiainfantil/fxml/pacientes/listarPacientes.fxml";
    String FXML_LISTAR_MEDICOS = "/br/com/clinicapsicologiainfantil/fxml/medicos/listarMedicos.fxml";
    String FXML_LISTAR_ESPECILIDADES = "/br/com/clinicapsicologiainfantil/fxml/especialidades/listarEspecialidades.fxml";
    String FXML_LISTAR_CONSULTAS = "/br/com/clinicapsicologiainfantil/fxml/consultas/listarConsultas.fxml";
    String FXML_LISTAR_CONSULTAS_AGENDADAS = "/br/com/clinicapsicologiainfantil/fxml/consultas/listarConsultasAgendadas.fxml";
    String FXML_LISTAR_RECEITAS = "/br/com/clinicapsicologiainfantil/fxml/receitas/listarReceitas.fxml";
    String FXML_CADASTRO_ESPECILIDADE = "/br/com/clinicapsicologiainfantil/fxml/especialidades/cadastroEspecialidade.fxml";
    String FXML_CADASTRO_MEDICO = "/br/com/clinicapsicologiainfantil/fxml/medicos/cadastroMedico.fxml";
    String FXML_CADASTRO_PACIENTE = "/br/com/clinicapsicologiainfantil/fxml/pacientes/cadastroPaciente.fxml";
    String FXML_CADASTRO_CONSULTA = "/br/com/clinicapsicologiainfantil/fxml/consultas/agendamentoConsulta.fxml";
    String FXML_CADASTRO_RECEITA = "/br/com/clinicapsicologiainfantil/fxml/receitas/cadastroReceita.fxml";
    String FXML_ATENDER_CONSULTA = "/br/com/clinicapsicologiainfantil/fxml/consultas/atenderConsulta.fxml";
    Font FONT_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 12);
    Font FONT_BOLD = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
    String MASK_DATE = "dd/MM/yyyy";
    int IMAGE_SCALE = 100;
    int MARGIN_LEFT = 36;
    int MARGIN_RIGHT = 36;
    int MARGIN_TOP = 36;
    int MARGIN_BOTTON = 36;
}