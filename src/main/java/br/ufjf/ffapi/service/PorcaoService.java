package br.ufjf.ffapi.service;

import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.model.entity.Porcao;
import br.ufjf.ffapi.model.repository.PorcaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PorcaoService {
    private PorcaoRepository repository;

    public PorcaoService(PorcaoRepository repository){
        this.repository = repository;
    }

    public List<Porcao> getPorcaos(){
        return repository.findAll();
    }

    public Optional<Porcao> getPorcaoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Porcao salvar(Porcao porcao) {
        validar(porcao);
        return repository.save(porcao);
    }

    @Transactional
    public void excluir(Porcao porcao) {
        Objects.requireNonNull(porcao.getId());
        repository.delete(porcao);
    }

    public void validar(Porcao porcao) {
        if (porcao.getQuantidade() <= 0) {
            throw new RegraNegocioException("Quantidade inválida");
        }
        if (porcao.getAlimento() == null || porcao.getAlimento().getId() == null || porcao.getAlimento().getId() == 0) {
            throw new RegraNegocioException("Alimento inválido");
        }
        if (porcao.getRefeicao() == null || porcao.getRefeicao().getId() == null || porcao.getRefeicao().getId() == 0) {
            throw new RegraNegocioException("Refeição inválida");
        }   
    }
    
}
