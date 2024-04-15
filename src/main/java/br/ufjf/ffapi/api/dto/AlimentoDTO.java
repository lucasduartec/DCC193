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
    private int proteinas;
    private int carboidratos;
    private int gorduras;

    public static AlimentoDTO create(Alimento alimento){
        ModelMapper modelMapper = new ModelMapper();
        AlimentoDTO dto = modelMapper.map(alimento, AlimentoDTO.class);
        dto.nome = alimento.getNome();
        dto.proteinas = alimento.getProteinas();
        dto.carboidratos = alimento.getCarboidratos();
        dto.gorduras = alimento.getGorduras();
        return dto;
    }
}