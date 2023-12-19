package br.com.clinicapsicologiainfantil.controller.repositories;

import br.com.clinicapsicologiainfantil.model.entities.ConsultaEntity;
import br.com.clinicapsicologiainfantil.model.entities.ReceitaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
    @Query(value = "select * from consulta where id_medico = :idMedico", nativeQuery = true)
    List<ConsultaEntity> findByIdMedico(@Param("idMedico") Long idMedico);

    @Query(value = "select * from consulta where id_paciente = ?", nativeQuery = true)
    Optional<ConsultaEntity> findByIdPaciente(@Param("idPaciente") Long idPaciente);

    @Query(value = "select * from consulta where id_paciente in (:idsPacientes)", nativeQuery = true)
    List<ConsultaEntity> findAllByIdPaciente(@Param("idsPacientes") List<Long> idsPacientes);

    @Query(value = "select id from paciente where upper(nome) like %:nome%", nativeQuery = true)
    List<Long> findByNome(@Param("nome") String nome);
}