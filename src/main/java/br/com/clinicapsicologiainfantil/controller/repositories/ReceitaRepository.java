package br.com.clinicapsicologiainfantil.controller.repositories;

import br.com.clinicapsicologiainfantil.model.dtos.PacienteDTO;
import br.com.clinicapsicologiainfantil.model.entities.ConsultaEntity;
import br.com.clinicapsicologiainfantil.model.entities.MedicoEntity;
import br.com.clinicapsicologiainfantil.model.entities.ReceitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReceitaRepository  extends JpaRepository<ReceitaEntity, Long> {
    @Query(value = "select * from receita where id_medico = :idMedico", nativeQuery = true)
    Optional<ReceitaEntity> findByIdMedico(@Param("idMedico") Long idMedico);

    @Query(value = "select * from receita where id_paciente = :idPaciente", nativeQuery = true)
    Optional<ReceitaEntity> findByIdPaciente(@Param("idPaciente") Long idPaciente);

    @Query(value = "select * from receita where id_paciente in (:idsPacientes)", nativeQuery = true)
    List<ReceitaEntity> findAllByIdPaciente(@Param("idsPacientes") List<Long> idsPacientes);


}
