package br.ufjf.ffapi.model.repository;

import br.ufjf.ffapi.model.entity.Plano;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanoRepository extends JpaRepository <Plano, Long> {
}
