package br.ufjf.ffapi.service;

import br.ufjf.ffapi.model.entity.Refeicao;
import br.ufjf.ffapi.model.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefeicaoService {
    private RefeicaoRepository repository;

    public RefeicaoService(RefeicaoRepository repository){
        this.repository = repository;
    }

    public List<Refeicao> getRefeicaos(){
        return repository.findAll();
    }

    public List<Refeicao> getRefeicaoById(Long id){
        return repository.findById(id);
    }
}
