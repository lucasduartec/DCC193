package br.ufjf.ffapi.service;

import br.ufjf.ffapi.model.entity.Plano;
import br.ufjf.ffapi.model.repository.PlanoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoService {
    private PlanoRepository repository;

    public PlanoService(PlanoRepository repository){
        this.repository = repository;
    }

    public List<Plano> getPlanos(){
        return repository.findAll();
    }

    public List<Plano> getPlanoById(Long id){
        return repository.findById(id);
    }
}
