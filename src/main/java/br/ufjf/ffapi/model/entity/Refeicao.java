package br.ufjf.ffapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String nome;
    private String horario;
    @ManyToOne
    private Refeicao refeicao;
}
