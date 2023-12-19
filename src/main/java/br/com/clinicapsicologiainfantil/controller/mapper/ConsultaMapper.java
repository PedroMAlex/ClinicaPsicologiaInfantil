package br.com.clinicapsicologiainfantil.controller.mapper;

import br.com.clinicapsicologiainfantil.controller.services.MedicoService;
import br.com.clinicapsicologiainfantil.controller.services.PacienteService;
import br.com.clinicapsicologiainfantil.model.dtos.ConsultaDTO;
import br.com.clinicapsicologiainfantil.model.dtos.MedicoDTO;
import br.com.clinicapsicologiainfantil.model.dtos.PacienteDTO;
import br.com.clinicapsicologiainfantil.model.entities.ConsultaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ConsultaMapper {
    private final MedicoService medicoService;
    private final PacienteService pacienteService;

    public ConsultaDTO toDTO(ConsultaEntity entity) {
        return ConsultaDTO.builder()
                .idConsulta(entity.getIdConsulta())
                .nomeMedico(findNomeMedicoById(entity.getIdMedico()))
                .nomePaciente(findNomePacienteById(entity.getIdPaciente()))
                .dataConsulta(LocalDate.parse(String.valueOf(entity.getDataConsulta())))
                .horaConsulta(String.valueOf(entity.getHoraConsulta()))
                .build();
    }

    public List<ConsultaDTO> toDTOs(List<ConsultaEntity> entities) {
        List<ConsultaDTO> dtos = new ArrayList<>();
        for (ConsultaEntity entity : entities) {
            ConsultaDTO dto = ConsultaDTO.builder()
                    .idConsulta(entity.getIdConsulta())
                    .nomeMedico(findNomeMedicoById(entity.getIdMedico()))
                    .nomePaciente(findNomePacienteById(entity.getIdPaciente()))
                    .dataConsulta(LocalDate.parse(String.valueOf(entity.getDataConsulta())))
                    .horaConsulta(String.valueOf(entity.getHoraConsulta()))
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }

    public ConsultaEntity toEntity(ConsultaDTO dto) {
        ConsultaEntity entity = new ConsultaEntity();
        entity.setIdConsulta(dto.getIdConsulta());
        entity.setIdPaciente(findIdByNomePaciente(dto.getNomePaciente()));
        entity.setIdMedico(findIdByNomeMedico(dto.getNomeMedico()));
        entity.setDataConsulta(dto.getDataConsulta());
//        String dataConsultaString = dto.getData();

//        try {
//            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            LocalDate dataConsulta = LocalDate.parse(dataConsultaString, dateFormatter);
//            entity.setDataConsulta(dataConsulta);
//        } catch (DateTimeParseException e) {
//            System.err.println("Erro ao converter a data: " + e.getMessage());
//        }

        return entity;
    }


    private String findNomePacienteById(Long idPaciente) {
        var paciente = pacienteService.findById(idPaciente);
        return paciente.getNomePaciente();
    }

    private String findNomeMedicoById(Long idMedico) {
        var medico = medicoService.findById(idMedico);
        return medico.getNomeMedico();
    }

    private Long findIdByNomeMedico(String nomeMedico) {
        MedicoDTO medico = medicoService.findIdMedicoByNome(nomeMedico);
        return (medico != null) ? medico.getIdMedico() : null;
    }
    private Long findIdByNomePaciente(String nomePaciente) {
        PacienteDTO paciente = pacienteService.findIdPacienteByNome(nomePaciente);
        return (paciente != null) ? paciente.getIdPaciente() : null;
    }

}
