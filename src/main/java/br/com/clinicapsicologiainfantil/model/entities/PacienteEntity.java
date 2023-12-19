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
@Table(name = "paciente")
public class PacienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long idPaciente;

    @NotNull
    @Max(100)
    @Column(name = "nome")
    private String nomePaciente;

    @NotNull
    @Max(11)
    @Column(name = "cpf")
    private String cpfPaciente;

    @Max(50)
    @Column(name = "email")
    private String emailPaciente;

    @Max(15)
    @Column(name = "contato")
    private String contatoPaciente;
}