package br.ufjf.ffapi.api.dto;

import br.ufjf.ffapi.model.entity.;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String username;
    private String password;

    public static UsuarioDTO create(Usuario usuario){
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO dto = modelMapper.map(pessoa, UsuarioDTO.class);
        dto.username = pessoa.getUsername();
        dto.password = pessoa.getPassword();
        return dto;
    }
}