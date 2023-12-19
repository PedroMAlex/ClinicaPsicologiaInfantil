package br.com.clinicapsicologiainfantil.model.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicoDTO {
    private Long idMedico;
    private String nomeMedico;
    private String crmMedico;
    private String cpfMedico;
    private String emailMedico;
    private String contatoMedico;
    private String especialidadeMedico;
}