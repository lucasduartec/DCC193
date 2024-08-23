package br.ufjf.ffapi.api.dto;


import br.ufjf.ffapi.model.entity.Alimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlimentoDTO {

    private Long id;    
    private String nome;
    private float proteinas;
    private float carboidratos;
    private float gorduras;

    public static AlimentoDTO create(Alimento alimento){
        ModelMapper modelMapper = new ModelMapper();
        AlimentoDTO dto = modelMapper.map(alimento, AlimentoDTO.class);
        return dto;
    }
}