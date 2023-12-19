package br.com.clinicapsicologiainfantil.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "receita")
public class ReceitaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idReceita;

    @NotNull
    @Column(name = "id_paciente")
    private Long idPaciente;

    @NotNull
    @Column(name = "id_medico")
    private Long idMedico;

    @NotNull
    @Column(name = "data")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @NotNull
    @Column(name = "hora")
    private LocalDateTime hora;

    @Column(name = "prescricao")
    @Max(255)
    private String prescricaoReceita;
}