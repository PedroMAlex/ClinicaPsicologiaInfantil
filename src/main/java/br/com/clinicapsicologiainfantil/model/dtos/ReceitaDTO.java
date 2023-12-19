package br.com.clinicapsicologiainfantil.model.dtos;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaDTO {
    private Long idReceita;
    private Long idMedico;
    private Long idPaciente;
    private String nomeMedico;
    private String nomePaciente;
    private LocalDate data;
    private LocalDateTime hora;
    private String prescricao;
}