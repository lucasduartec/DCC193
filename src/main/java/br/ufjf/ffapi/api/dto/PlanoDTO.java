package br.ufjf.ffapi.api.dto;

import br.ufjf.ffapi.model.entity.Pessoa;
import br.ufjf.ffapi.model.entity.Plano;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanoDTO {

    private Long id;    
    private String nome;
    private String descricao;
    private int metaCalorias;
    private Long idPessoa;

    public static PlanoDTO create(Plano plano){
        ModelMapper modelMapper = new ModelMapper();
        PlanoDTO dto = modelMapper.map(plano, PlanoDTO.class);
        return dto;
    }
}