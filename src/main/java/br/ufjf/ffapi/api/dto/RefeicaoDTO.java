package br.ufjf.ffapi.api.dto;

import br.ufjf.ffapi.model.entity.Refeicao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefeicaoDTO {

    private Long id;    
    private String nome;
    private String horario;
    private Long idRefeicao;

    public static RefeicaoDTO create(Refeicao refeicao){
        ModelMapper modelMapper = new ModelMapper();
        RefeicaoDTO dto = modelMapper.map(refeicao, RefeicaoDTO.class);
        dto.nome = refeicao.getNome();
        dto.horario = refeicao.getHorario();
        return dto;
    }
}