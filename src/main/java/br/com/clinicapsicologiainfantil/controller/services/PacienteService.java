package br.com.clinicapsicologiainfantil.controller.services;

import br.com.clinicapsicologiainfantil.controller.mapper.PacienteMapper;
import br.com.clinicapsicologiainfantil.controller.repositories.ConsultaRepository;
import br.com.clinicapsicologiainfantil.controller.repositories.PacienteRepository;
import br.com.clinicapsicologiainfantil.model.dtos.PacienteDTO;
import br.com.clinicapsicologiainfantil.model.entities.ConsultaEntity;
import br.com.clinicapsicologiainfantil.model.entities.PacienteEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteMapper mapper;
    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;

    public List<PacienteDTO> findAll() {
        return mapper.toDTOs(pacienteRepository.findAll());
    }

    public boolean deleteById(Long id) {
        Optional<ConsultaEntity> consultaEntity = consultaRepository.findByIdPaciente(id);
        if (consultaEntity.isPresent()) {
            return false;
        } else {
            pacienteRepository.deleteById(id);
            return true;
        }
    }

    public PacienteDTO save(PacienteDTO dto) {
        if (StringUtils.isBlank(dto.getNomePaciente()))
            return null;

        if (dto.getIdPaciente() == 0) {
            PacienteDTO pacienteExistente = findByCpf(dto.getCpfPaciente());
            if (Objects.nonNull(pacienteExistente))
                return null;
        }

        return mapper.toDTO(pacienteRepository.save(mapper.toEntity(dto)));
    }

    public PacienteDTO findByCpf(String cpfPaciente) {
        var pacienteEntity = pacienteRepository.findByCpf(cpfPaciente);
        return pacienteEntity.map(mapper::toDTO).orElse(null);
    }

    public PacienteDTO findById(Long idPaciente) {
        var pacienteEntity = pacienteRepository.findById(idPaciente);
        return pacienteEntity.map(mapper::toDTO).orElse(null);
    }

    public PacienteDTO findIdPacienteByNome(String nomePaciente) {
        Optional<PacienteEntity> pacienteEntity =
                pacienteRepository.findIdByNome(nomePaciente);
        return pacienteEntity.map(mapper::toDTO).orElse(null);
    }
}