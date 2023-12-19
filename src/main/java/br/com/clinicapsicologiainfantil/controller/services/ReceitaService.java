package br.com.clinicapsicologiainfantil.controller.services;

import br.com.clinicapsicologiainfantil.controller.mapper.ReceitaMapper;
import br.com.clinicapsicologiainfantil.controller.repositories.PacienteRepository;
import br.com.clinicapsicologiainfantil.controller.repositories.ReceitaRepository;
import br.com.clinicapsicologiainfantil.model.dtos.ReceitaDTO;
import br.com.clinicapsicologiainfantil.model.entities.ReceitaEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceitaService {
    private final ReceitaMapper mapper;
    private final ReceitaRepository receitaRepository;
    private final PacienteRepository pacienteRepository;

    public List<ReceitaDTO> pesquisarReceitasPorPaciente(String nomePaciente) {
        var receitas = receitaRepository.findAllByIdPaciente(
                pacienteRepository.findByNome(nomePaciente.toUpperCase()));
        if (receitas.size() > 0) {
            return mapper.toDTOs(receitas);
        } else {
            return null;
        }
    }

    public ReceitaDTO save(ReceitaDTO dto) {
        ReceitaEntity entity = mapper.toEntity(dto);
        ReceitaEntity entitySaved = receitaRepository.save(entity);
        return mapper.toDTO(entitySaved);
    }
}