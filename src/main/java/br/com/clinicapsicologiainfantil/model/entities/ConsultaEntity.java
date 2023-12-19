package br.com.clinicapsicologiainfantil.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "consulta")
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idConsulta;

    @NotNull
    @Column(name = "id_paciente")
    private Long idPaciente;

    @NotNull
    @Column(name = "id_medico")
    private Long idMedico;

    @NotNull
    @Column(name = "data")
    private LocalDate dataConsulta;

    @NotNull
    @Column(name = "hora")
    private String horaConsulta;

    @Column(name = "devolutiva")
    @Max(255)
    private String devolutivaConsulta;
}