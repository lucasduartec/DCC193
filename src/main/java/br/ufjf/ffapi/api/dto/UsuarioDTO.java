package br.ufjf.ffapi.api.dto;

import br.ufjf.ffapi.model.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String login;
    private String cpf;
    private String senha;
//    private String repeticaoSenha;
    private boolean admin;

    public static UsuarioDTO create(Usuario usuario){
        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);
        return dto;
    }
}