package br.com.clinicapsicologiainfantil.controller.repositories;

import br.com.clinicapsicologiainfantil.model.entities.PacienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<PacienteEntity, Long> {
    @Query(value = "select * from paciente where cpf = :cpfPaciente", nativeQuery = true)
    Optional<PacienteEntity> findByCpf(@Param("cpfPaciente") String cpfPaciente);

    @Query(value = "select id from paciente where upper(nome) like %:nome%", nativeQuery = true)
    List<Long> findByNome(@Param("nome") String nome);

    @Query(value = "select * from paciente where nome = :nomePaciente", nativeQuery = true)
    Optional<PacienteEntity> findIdByNome(@Param("nomePaciente") String nomePaciente);
}