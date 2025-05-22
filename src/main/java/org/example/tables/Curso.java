package org.example.tables;

import javax.persistence.*;
import java.util.List;

@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String descricao;

    private Integer cargaHoraria;

    @OneToMany(mappedBy = "curso")
    private List<Matricula> matriculaList;
}
