package br.ufjf.ffapi.service;

import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.model.entity.Pessoa;
import br.ufjf.ffapi.model.repository.PessoaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PessoaService {
    private PessoaRepository repository;

    public PessoaService(PessoaRepository repository){
        this.repository = repository;
    }   

    public List<Pessoa> getPessoas(){
        return repository.findAll();
    }

    public Optional<Pessoa> getPessoaById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        validar(pessoa);
        return repository.save(pessoa);
    }

    @Transactional
    public void excluir(Pessoa pessoa) {
        Objects.requireNonNull(pessoa.getId());
        repository.delete(pessoa);
    }

    public void validar(Pessoa pessoa) {
        if (pessoa.getNome() == null || pessoa.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (pessoa.getIdade() <=0 ) {
            throw new RegraNegocioException("Idade inválida");
        }
        if (pessoa.getPeso() <= 0 ) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (pessoa.getAltura() <= 0) {
            throw new RegraNegocioException("Nome inválido");
        }        
    }
}
