package br.ufjf.ffapi.model.repository;

import br.ufjf.ffapi.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}
