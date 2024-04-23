package br.ufjf.ffapi.service;

import br.ufjf.ffapi.model.entity.Pessoa;
import br.ufjf.ffapi.model.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {
    private PessoaRepository repository;

    public PessoaService(PessoaRepository repository){
        this.repository = repository;
    }

    public List<Pessoa> getPessoas(){
        return repository.findAll();
    }

    public List<Pessoa> getPessoaById(Long id){
        return repository.findById(id);
    }
}
