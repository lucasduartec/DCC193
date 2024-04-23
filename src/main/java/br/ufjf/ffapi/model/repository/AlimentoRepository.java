package br.ufjf.ffapi.model.repository;

import br.ufjf.ffapi.model.entity.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository <Alimento, Long> {
}
