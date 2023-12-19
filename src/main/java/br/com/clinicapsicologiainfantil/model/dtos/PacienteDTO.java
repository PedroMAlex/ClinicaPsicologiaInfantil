package br.com.clinicapsicologiainfantil.model.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteDTO {
    private Long idPaciente;
    private String nomePaciente;
    private String cpfPaciente;
    private String emailPaciente;
    private String contatoPaciente;
}