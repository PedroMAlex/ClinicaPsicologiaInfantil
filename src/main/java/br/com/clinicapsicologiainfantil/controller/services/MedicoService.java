package br.com.clinicapsicologiainfantil.controller.services;

import br.com.clinicapsicologiainfantil.controller.mapper.MedicoMapper;
import br.com.clinicapsicologiainfantil.controller.repositories.ConsultaRepository;
import br.com.clinicapsicologiainfantil.controller.repositories.MedicoRepository;
import br.com.clinicapsicologiainfantil.model.dtos.MedicoDTO;
import br.com.clinicapsicologiainfantil.model.entities.ConsultaEntity;
import br.com.clinicapsicologiainfantil.model.entities.MedicoEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicoService {
    private final MedicoMapper mapper;
    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;

    public List<MedicoDTO> findAll() {
        return mapper.toDTOs(medicoRepository.findAll());
    }

    public boolean deleteById(Long id) {
        var consultaEntity = consultaRepository.findByIdMedico(id);
        if (consultaEntity.size() > 0) {
            return false;
        } else {
            medicoRepository.deleteById(id);
            return true;
        }
    }

    public MedicoDTO save(MedicoDTO dto) {
        if (StringUtils.isBlank(dto.getNomeMedico()))
            return null;

        if (dto.getIdMedico() == 0) {
            MedicoDTO medicoExistente = findByCpf(dto.getCpfMedico());
            if (Objects.nonNull(medicoExistente))
                return null;
        }
        return mapper.toDTO(medicoRepository.save(mapper.toEntity(dto)));
    }

    public MedicoDTO findByCpf(String cpfMedico) {
        var medicoEntity = medicoRepository.findByCpf(cpfMedico);
        return medicoEntity.map(mapper::toDTO).orElse(null);
    }

    public MedicoDTO findById(Long idMedico) {
        if (idMedico != null) {
            var medicoEntity = medicoRepository.findById(idMedico);
            return medicoEntity.map(mapper::toDTO).orElse(null);
        } else {

            return null;
        }
    }

    public MedicoDTO findIdMedicoByNome(String nomeMedico) {
        Optional<MedicoEntity> medicoEntity = medicoRepository.findIdByNome(nomeMedico);
        return medicoEntity.map(mapper::toDTO).orElse(null);
    }
}