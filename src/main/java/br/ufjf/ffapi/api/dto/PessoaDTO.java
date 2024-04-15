package br.ufjf.ffapi.api.dto;

import br.ufjf.ffapi.model.entity.;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDTO {

    private Long id;
    private String nome;   
    private int idade;
    private float peso;
    private float altura;

    public static PessoaDTO create(Pessoa pessoa){
        ModelMapper modelMapper = new ModelMapper();
        PessoaDTO dto = modelMapper.map(pessoa, PessoaDTO.class);
        dto.nome = pessoa.getNome();
        dto.idade = pessoa.getIdade();
        dto.peso = pessoa.getPeso();
        dto.altura = pessoa.getAltura();
        return dto;
    }
}