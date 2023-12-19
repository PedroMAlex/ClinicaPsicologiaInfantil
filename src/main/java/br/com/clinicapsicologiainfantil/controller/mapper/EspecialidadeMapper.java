package br.com.clinicapsicologiainfantil.controller.mapper;

import br.com.clinicapsicologiainfantil.model.dtos.EspecialidadeDTO;
import br.com.clinicapsicologiainfantil.model.entities.EspecialidadeEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class EspecialidadeMapper {
    public EspecialidadeDTO toDTO(EspecialidadeEntity entity) {
        return Objects.isNull(entity) ? null :
                EspecialidadeDTO.builder()
                    .idEspecialidade(entity.getIdEspecialidade())
                    .descricaoEspecialidade(entity.getDescricao())
                    .build();
    }

    public List<EspecialidadeDTO> toDTOs(List<EspecialidadeEntity> entities) {
        List<EspecialidadeDTO> dtos = new ArrayList<>();
        for (EspecialidadeEntity entity : entities) {
            EspecialidadeDTO dto = EspecialidadeDTO.builder()
                    .idEspecialidade(entity.getIdEspecialidade())
                    .descricaoEspecialidade(entity.getDescricao())
                    .build();
            dtos.add(dto);
        }
        return dtos;
    }

    public EspecialidadeEntity toEntity(EspecialidadeDTO dto) {
        return new EspecialidadeEntity(
                dto.getIdEspecialidade(),
                dto.getDescricaoEspecialidade());
    }
}