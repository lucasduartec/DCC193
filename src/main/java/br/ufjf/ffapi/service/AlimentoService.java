package br.ufjf.ffapi.service;

import br.ufjf.ffapi.model.entity.Alimento;
import br.ufjf.ffapi.model.repository.AlimentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlimentoService {
    private AlimentoRepository repository;

    public AlimentoService(AlimentoRepository repository){
        this.repository = repository;
    }

    public List<Alimento> getAlimentos(){
        return repository.findAll();
    }

    public List<Alimento> getAlimentoById(Long id){
        return repository.findById(id);
    }
}
