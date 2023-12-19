package br.com.clinicapsicologiainfantil.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medico")
public class MedicoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idMedico;

    @NotNull
    @Column(name = "id_especialidade")
    private Long idEspecialidade;

    @NotNull
    @Max(100)
    @Column(name = "nome")
    private String nomeMedico;

    @NotNull
    @Column(name = "crm")
    @Max(30)
    private String crmMedico;

    @NotNull
    @Column(name = "cpf")
    @Max(11)
    private String cpfMedico;

    @Column(name = "email")
    @Max(50)
    private String emailMedico;

    @Column(name = "contato")
    @Max(15)
    private String contatoMedico;
}
