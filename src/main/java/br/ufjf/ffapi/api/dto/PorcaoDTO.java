package br.ufjf.ffapi.api.dto;

import br.ufjf.ffapi.model.entity.Porcao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PorcaoDTO {

    private Long id;    
    private int quantidade;
    private Long idAlimento;
    private Long idRefeicao;

    public static PorcaoDTO create(Porcao porcao){
        ModelMapper modelMapper = new ModelMapper();
        PorcaoDTO dto = modelMapper.map(porcao, PorcaoDTO.class);
        return dto;
    }
}