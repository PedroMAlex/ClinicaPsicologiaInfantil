package br.com.clinicapsicologiainfantil.controller.repositories;

import br.com.clinicapsicologiainfantil.model.entities.MedicoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {
    @Query(value = "select * from medico where id_especialidade = :idEpecilidade", nativeQuery = true)
    Optional<MedicoEntity> findByIdEspecialidade(@Param("idEpecilidade") Long idEpecilidade);

    @Query(value = "select * from medico where cpf = :cpfMedico", nativeQuery = true)
    Optional<MedicoEntity> findByCpf(@Param("cpfMedico") String cpfMedico);

    @Query(value = "select * from medico where nome = :nomeMedico", nativeQuery = true)
    Optional<MedicoEntity> findIdByNome(@Param("nomeMedico") String nomeMedico);

    @Query(value = "select * from medico where upper(nome) like %:nomeMedico%", nativeQuery = true)
    List<MedicoEntity> findByIdByContainNome(@Param("nomeMedico") String nomeMedico);
}