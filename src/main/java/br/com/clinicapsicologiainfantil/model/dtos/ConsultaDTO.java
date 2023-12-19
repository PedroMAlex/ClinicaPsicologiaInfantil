package br.com.clinicapsicologiainfantil.model.dtos;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ConsultaDTO {
    private Long idConsulta;
    private String nomeMedico;
    private Long idMedico;
    private Long idPaciente;
    private String nomePaciente;
    private LocalDate dataConsulta;
    private String horaConsulta;
}
