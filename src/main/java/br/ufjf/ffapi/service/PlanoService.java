package br.ufjf.ffapi.service;

import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.model.entity.Plano;
import br.ufjf.ffapi.model.repository.PlanoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlanoService {
    private PlanoRepository repository;

    public PlanoService(PlanoRepository repository){
        this.repository = repository;
    }

    public List<Plano> getPlanos(){
        return repository.findAll();
    }

    public Optional<Plano> getPlanoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Plano salvar(Plano plano) {
        validar(plano);
        return repository.save(plano);
    }

    @Transactional
    public void excluir(Plano plano) {
        Objects.requireNonNull(plano.getId());
        repository.delete(plano);
    }

    public void validar(Plano plano) {
        if (plano.getNome() == null || plano.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (plano.getNome() == null || plano.getNome().trim().equals("")) {
            throw new RegraNegocioException("Descrição inválida");
        }
        if (plano.getMetaCalorias() < 0 ) {
            throw new RegraNegocioException("Meta de calorias inválida");
        }   
    }
}
