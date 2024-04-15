package br.ufjf.ffapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class Pessoa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String nome;
    private int idade;
    private float peso;
    private float altura;
}
