package br.com.clinicapsicologiainfantil.controller.services;

import br.com.clinicapsicologiainfantil.controller.mapper.EspecialidadeMapper;
import br.com.clinicapsicologiainfantil.controller.repositories.EspecialidadeRepository;
import br.com.clinicapsicologiainfantil.controller.repositories.MedicoRepository;
import br.com.clinicapsicologiainfantil.model.dtos.EspecialidadeDTO;
import br.com.clinicapsicologiainfantil.model.entities.EspecialidadeEntity;
import br.com.clinicapsicologiainfantil.model.entities.MedicoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EspecialidadeService {
    private final EspecialidadeMapper mapper;
    private final MedicoRepository medicoRepository;
    private final EspecialidadeRepository especRepository;

    public List<EspecialidadeDTO> findAll() {
        return mapper.toDTOs(especRepository.findAll());
    }

    public EspecialidadeDTO findById(Long id) {
        Optional<EspecialidadeEntity> entity = especRepository.findById(id);
        return mapper.toDTO(entity.isPresent() ? entity.get() : null);
    }

    public EspecialidadeDTO findByDescricao(String descricao) {
        EspecialidadeEntity entity = especRepository.findByDescricao(descricao);
        return mapper.toDTO(entity);
    }

    public EspecialidadeDTO save(EspecialidadeDTO dto) {
        if (dto.getIdEspecialidade() == 0L) {
            EspecialidadeDTO especialidadeExistente =
                    findByDescricao(dto.getDescricaoEspecialidade());
            if (Objects.nonNull(especialidadeExistente))
                return null;
        }
        return mapper.toDTO(especRepository.save(mapper.toEntity(dto)));
    }

    public boolean deleteById(Long id) {
        Optional<MedicoEntity> medicoEntity = medicoRepository.findByIdEspecialidade(id);
        if (medicoEntity.isPresent()) {
            return false;
        } else {
            especRepository.deleteById(id);
            return true;
        }
    }
}