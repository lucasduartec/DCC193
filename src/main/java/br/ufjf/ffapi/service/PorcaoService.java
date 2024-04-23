package br.ufjf.ffapi.service;

import br.ufjf.ffapi.model.entity.Porcao;
import br.ufjf.ffapi.model.repository.PorcaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PorcaoService {
    private PorcaoRepository repository;

    public PorcaoService(PorcaoRepository repository){
        this.repository = repository;
    }

    public List<Porcao> getPorcaos(){
        return repository.findAll();
    }

    public List<Porcao> getPorcaoById(Long id){
        return repository.findById(id);
    }
}
