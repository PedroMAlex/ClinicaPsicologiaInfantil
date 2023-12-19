package br.com.clinicapsicologiainfantil.controller.services;

import br.com.clinicapsicologiainfantil.controller.mapper.ConsultaMapper;
import br.com.clinicapsicologiainfantil.controller.repositories.ConsultaRepository;
import br.com.clinicapsicologiainfantil.controller.repositories.MedicoRepository;
import br.com.clinicapsicologiainfantil.model.dtos.ConsultaDTO;
import br.com.clinicapsicologiainfantil.model.entities.ConsultaEntity;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultaService {
    private final ConsultaMapper mapper;
    private final MedicoRepository medicoRepository;
    private final ConsultaRepository consultaRepository;

    public ConsultaDTO save(ConsultaDTO dto) {
        if (isValidConsulta(dto)) {
            if (dto.getIdConsulta() != 0) {
                ConsultaDTO consultaExistente = findConsultaById(dto.getIdConsulta());
                if (Objects.nonNull(consultaExistente)) {
                    consultaExistente.setNomeMedico(dto.getNomeMedico());
                    consultaExistente.setNomePaciente(dto.getNomePaciente());
                    consultaExistente.setDataConsulta(dto.getDataConsulta());
                    consultaExistente.setHoraConsulta(dto.getHoraConsulta());
                    return mapper.toDTO(consultaRepository.save(mapper.toEntity(consultaExistente)));
                } else {
                    return null;
                }
            } else {
                ConsultaEntity entity = mapper.toEntity(dto);
                ConsultaEntity save = consultaRepository.save(entity);
                return mapper.toDTO(save);
            }
        } else {

            return null;
        }
    }

    private boolean isValidConsulta(ConsultaDTO dto) {
        return !StringUtils.isAnyBlank(dto.getNomeMedico(),
                dto.getNomePaciente(), dto.getHoraConsulta());
    }

    private ConsultaDTO findConsultaById(long id) {
        Optional<ConsultaEntity> consultaEntity = consultaRepository.findById(id);
        return consultaEntity.map(mapper::toDTO).orElse(null);
    }

    public List<ConsultaDTO> pesquisarConsultasPorPaciente(String nomePaciente) {
        var consultas = consultaRepository.findAllByIdPaciente(
                consultaRepository.findByNome(nomePaciente.toUpperCase()));
        if(consultas.size() > 0){
            return mapper.toDTOs(consultas);
        } else {
            return null;
        }
    }

    public List<ConsultaDTO> pesquisarConsultasPorMedico(String nomeMedico) {
        long idMedico = 0L;
        var medicoEntity = medicoRepository.findByIdByContainNome(nomeMedico.toUpperCase());
        if (medicoEntity.size() > 0)
            idMedico = medicoEntity.get(0).getIdMedico();
        else
            return null;

        var consultas = consultaRepository.findByIdMedico(idMedico);
        if(consultas.size() > 0){
            return mapper.toDTOs(consultas);
        } else {
            return null;
        }
    }
}