package org.example.tables;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String email;

    private LocalDate dataNascimento;

    @OneToOne(mappedBy = "aluno", cascade = CascadeType.ALL)
    private Matricula matricula;
}
