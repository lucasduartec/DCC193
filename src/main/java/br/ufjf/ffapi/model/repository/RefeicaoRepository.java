package br.ufjf.ffapi.model.repository;

import br.ufjf.ffapi.model.entity.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefeicaoRepository extends JpaRepository <Refeicao, Long> {
}
