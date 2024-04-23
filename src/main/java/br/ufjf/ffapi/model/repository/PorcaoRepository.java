package br.ufjf.ffapi.model.repository;

import br.ufjf.ffapi.model.entity.Porcao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PorcaoRepository extends JpaRepository  <Porcao, Long> {
}
