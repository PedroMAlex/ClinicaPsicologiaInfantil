package br.com.clinicapsicologiainfantil.model.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EspecialidadeDTO {
    private Long idEspecialidade;
    private String descricaoEspecialidade;
}