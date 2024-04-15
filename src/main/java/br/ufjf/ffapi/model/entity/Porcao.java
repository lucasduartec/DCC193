package br.ufjf.ffapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Porcao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int quantidade;
    @OneToOne
    private Alimento alimento;
    @ManyToOne
    private Refeicao refeicao;
}
