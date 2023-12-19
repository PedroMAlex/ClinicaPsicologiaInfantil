package br.com.clinicapsicologiainfantil.controller.mapper;

import br.com.clinicapsicologiainfantil.controller.services.EspecialidadeService;
import br.com.clinicapsicologiainfantil.model.dtos.MedicoDTO;
import br.com.clinicapsicologiainfantil.model.entities.MedicoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MedicoMapper {
    private final EspecialidadeService service;

    public MedicoDTO toDTO(MedicoEntity entity) {
        return MedicoDTO.builder()
                .idMedico(entity.getIdMedico())
                .nomeMedico(entity.getNomeMedico())
                .crmMedico(entity.getCrmMedico())
                .cpfMedico(entity.getCpfMedico())
                .emailMedico(entity.getEmailMedico())
                .contatoMedico(entity.getContatoMedico())
                .especialidadeMedico(findEspecialidadeById(entity.getIdEspecialidade()))
                .build();
    }

    public List<MedicoDTO> toDTOs(List<MedicoEntity> entities) {
        List<MedicoDTO> dtos = new ArrayList<>();
        for (MedicoEntity entity : entities) {
            MedicoDTO dto = MedicoDTO.builder()
                    .idMedico(entity.getIdMedico())
                    .nomeMedico(entity.getNomeMedico())
                    .crmMedico(entity.getCrmMedico())
                    .cpfMedico(entity.getCpfMedico())
                    .emailMedico(entity.getEmailMedico())
                    .contatoMedico(entity.getContatoMedico())
                    .especialidadeMedico(findEspecialidadeById(entity.getIdEspecialidade()))
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }

    public MedicoEntity toEntity(MedicoDTO dto) {
        return new MedicoEntity(dto.getIdMedico(),
                findIdEpecilidadeByDescricao(dto.getEspecialidadeMedico()),
                dto.getNomeMedico(),
                dto.getCrmMedico(),
                dto.getCpfMedico(),
                dto.getEmailMedico(),
                dto.getContatoMedico());
    }

    private String findEspecialidadeById(Long idEspecialidade) {
        var especialidade = service.findById(idEspecialidade);
        return especialidade.getDescricaoEspecialidade();
    }

    private Long findIdEpecilidadeByDescricao(String descricao) {
        var especialidade = service.findByDescricao(descricao);
        return especialidade.getIdEspecialidade();
    }
}