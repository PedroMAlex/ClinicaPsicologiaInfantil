package br.com.clinicapsicologiainfantil.controller.mapper;

import br.com.clinicapsicologiainfantil.model.dtos.PacienteDTO;
import br.com.clinicapsicologiainfantil.model.entities.PacienteEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PacienteMapper {
    public PacienteDTO toDTO(PacienteEntity entity) {
        return PacienteDTO.builder()
                .idPaciente(entity.getIdPaciente())
                .nomePaciente(entity.getNomePaciente())
                .cpfPaciente(entity.getCpfPaciente())
                .emailPaciente(entity.getEmailPaciente())
                .contatoPaciente(entity.getContatoPaciente())
                .build();
    }

    public List<PacienteDTO> toDTOs(List<PacienteEntity> entities) {
        List<PacienteDTO> dtos = new ArrayList<>();
        for (PacienteEntity entity : entities) {
            PacienteDTO dto = PacienteDTO.builder()
                    .idPaciente(entity.getIdPaciente())
                    .nomePaciente(entity.getNomePaciente())
                    .cpfPaciente(entity.getCpfPaciente())
                    .emailPaciente(entity.getEmailPaciente())
                    .contatoPaciente(entity.getContatoPaciente())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }

    public PacienteEntity toEntity(PacienteDTO dto) {
        return new PacienteEntity(dto.getIdPaciente(),
                dto.getNomePaciente(),
                dto.getCpfPaciente(),
                dto.getEmailPaciente(),
                dto.getContatoPaciente());
    }
}