package br.com.clinicapsicologiainfantil.controller.repositories;

import br.com.clinicapsicologiainfantil.model.entities.EspecialidadeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EspecialidadeRepository extends JpaRepository<EspecialidadeEntity, Long> {
    EspecialidadeEntity findByDescricao(@Param("descricao") String descricao);
}