package br.ufjf.ffapi.model.repository;

import br.ufjf.ffapi.model.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository <Pessoa, Long> {
}
