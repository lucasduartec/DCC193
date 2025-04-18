package br.ufjf.ffapi.model.entity;

import javax.persistence.*;

import br.ufjf.ffapi.api.dto.PessoaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Plano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private int metaCalorias;
    @ManyToOne
    private Pessoa pessoa;
    @ManyToMany
    private List<Refeicao> refeicoes;
}
