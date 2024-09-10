package br.ufjf.ffapi.api.controller;

import br.ufjf.ffapi.api.dto.CredenciaisDTO;
import br.ufjf.ffapi.api.dto.TokenDTO;
import br.ufjf.ffapi.api.dto.UsuarioDTO;
import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.exception.SenhaInvalidaException;
import br.ufjf.ffapi.model.entity.Usuario;
import br.ufjf.ffapi.security.JwtService;
import br.ufjf.ffapi.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
@CrossOrigin
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @GetMapping()
    public ResponseEntity get() {
        List<Usuario> usuarios = usuarioService.getUsuarios();
        return ResponseEntity.ok(usuarios.stream().map(UsuarioDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter detalhes de um usuário")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Usuário encontrado"),
            @ApiResponse(code = 404, message = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Integer id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        if (!usuario.isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(usuario.map(UsuarioDTO::create));
    }

//    @PostMapping()
//    @ApiOperation("Salva um novo usuário")
//    @ApiResponses({
//            @ApiResponse(code = 201, message = "Usuário salvo com sucesso"),
//            @ApiResponse(code = 400, message = "Erro ao salvar o usuário")
//    })
//    public ResponseEntity post(@RequestBody UsuarioDTO dto) {
//        try {
//            Usuario usuario = converter(dto);
//            usuario = usuarioService.salvar(usuario);
//            return new ResponseEntity(usuario, HttpStatus.CREATED);
//        } catch (RegraNegocioException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    @PutMapping("{id}")
    @ApiOperation("Altera um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar o usuário")
    })
    public ResponseEntity atualizar(@PathVariable("id") Integer id, @RequestBody UsuarioDTO dto) {
        if (!usuarioService.getUsuarioById(id).isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Usuario usuario = converter(dto);
            usuario.setId(id);
            usuarioService.salvar(usuario);
            return ResponseEntity.ok(usuario);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um usuário")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Usuário excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o usuário")
    })
    public ResponseEntity excluir(@PathVariable("id") Integer id) {
        Optional<Usuario> usuario = usuarioService.getUsuarioById(id);
        if (!usuario.isPresent()) {
            return new ResponseEntity("Usuário não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            usuarioService.excluir(usuario.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Usuario converter(UsuarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = modelMapper.map(dto, Usuario.class);
        return usuario;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar(@RequestBody Usuario usuario ){
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioService.salvar(usuario);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciais){
        try{
            Usuario usuario = Usuario.builder()
                    .login(credenciais.getLogin())
                    .senha(credenciais.getSenha()).build();
            UserDetails usuarioAutenticado = usuarioService.autenticar(usuario);
            String token = jwtService.gerarToken(usuario);
            return new TokenDTO(usuario.getLogin(), token);
        } catch (UsernameNotFoundException | SenhaInvalidaException e ){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
