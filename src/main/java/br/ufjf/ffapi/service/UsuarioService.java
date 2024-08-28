package br.ufjf.ffapi.service;

import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.model.entity.Usuario;
import br.ufjf.ffapi.model.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository repository;

    public UsuarioService(UsuarioRepository repository){
        this.repository = repository;
    }

    public List<Usuario> getUsuarios(){
        return repository.findAll();
    }

    public Optional<Usuario> getUsuarioById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        validar(usuario);
        return repository.save(usuario);
    }

    @Transactional
    public void excluir(Usuario usuario) {
        Objects.requireNonNull(usuario.getId());
        repository.delete(usuario);
    }

    public void validar(Usuario usuario) {
        if (usuario.getLogin() == null || usuario.getLogin().trim().equals("")) {
            throw new RegraNegocioException("Login inválido");
        }
        if (usuario.getCpf() == null || usuario.getCpf().trim().equals("")) {
            throw new RegraNegocioException("CPF inválido");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().equals("")) {
            throw new RegraNegocioException("Senha inválida");
        }
        if (!usuario.isAdmin()) {
            throw new RegraNegocioException("Admin inválido");
        }
    }
}
