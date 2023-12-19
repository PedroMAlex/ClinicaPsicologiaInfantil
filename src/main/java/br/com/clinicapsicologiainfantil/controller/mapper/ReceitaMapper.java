package br.com.clinicapsicologiainfantil.controller.mapper;

import br.com.clinicapsicologiainfantil.controller.services.MedicoService;
import br.com.clinicapsicologiainfantil.controller.services.PacienteService;
import br.com.clinicapsicologiainfantil.model.dtos.ReceitaDTO;
import br.com.clinicapsicologiainfantil.model.entities.ReceitaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReceitaMapper {
    private final MedicoService medicoService;
    private final PacienteService pacienteService;

    public ReceitaDTO toDTO(ReceitaEntity entity) {
        return ReceitaDTO.builder()
                .idReceita(entity.getIdReceita())
                .nomeMedico(findNomeMedicoById(entity.getIdMedico()))
                .nomePaciente(findNomePacienteById(entity.getIdPaciente()))
                .data(entity.getData())
                .hora(entity.getHora())
                .prescricao(entity.getPrescricaoReceita())
                .build();
    }

    public List<ReceitaDTO> toDTOs(List<ReceitaEntity> entities) {
        List<ReceitaDTO> dtos = new ArrayList<>();
        for (ReceitaEntity entity : entities) {
            dtos.add(ReceitaDTO.builder()
                    .idReceita(entity.getIdReceita())
                    .nomePaciente((findNomePacienteById(entity.getIdPaciente())))
                    .nomeMedico(findNomeMedicoById(entity.getIdMedico()))
                    .data(entity.getData())
                    .hora(entity.getHora())
                    .prescricao(entity.getPrescricaoReceita())
                    .build());
        }
        return dtos;
    }

    public ReceitaEntity toEntity(ReceitaDTO dto) {
        ReceitaEntity entity = new ReceitaEntity();
        entity.setIdReceita(dto.getIdReceita());
        entity.setIdPaciente(dto.getIdPaciente() == null
                ? findIdPacienteByNome(dto.getNomePaciente())
                : dto.getIdPaciente());
        entity.setIdMedico(dto.getIdMedico() == null
                ? findIdMedicoByNome(dto.getNomeMedico())
                : dto.getIdMedico());
        entity.setData(dto.getData());
        entity.setHora(dto.getHora());
        entity.setPrescricaoReceita(dto.getPrescricao());

        return entity;
    }

    private Long findIdMedicoByNome(String nomeMedico) {
        var medico = medicoService.findIdMedicoByNome(nomeMedico);
        return medico.getIdMedico();
    }

    private Long findIdPacienteByNome(String nomePaciente) {
        var paciente = pacienteService.findIdPacienteByNome(nomePaciente);
        return paciente.getIdPaciente();
    }

    private String findNomePacienteById(Long idPaciente) {
        var paciente = pacienteService.findById(idPaciente);
        return paciente.getNomePaciente();
    }

    private String findNomeMedicoById(Long idMedico) {
        var medico = medicoService.findById(idMedico);
        return medico.getNomeMedico();
    }
}