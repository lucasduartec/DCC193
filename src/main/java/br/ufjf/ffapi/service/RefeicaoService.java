package br.ufjf.ffapi.service;

import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.model.entity.Refeicao;
import br.ufjf.ffapi.model.repository.RefeicaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RefeicaoService {
    private RefeicaoRepository repository;

    public RefeicaoService(RefeicaoRepository repository){
        this.repository = repository;
    }

    public List<Refeicao> getRefeicaos(){
        return repository.findAll();
    }

    public Optional<Refeicao> getRefeicaoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Refeicao salvar(Refeicao refeicao) {
        validar(refeicao);
        return repository.save(refeicao);
    }

    @Transactional
    public void excluir(Refeicao refeicao) {
        Objects.requireNonNull(refeicao.getId());
        repository.delete(refeicao);
    }

    public void validar(Refeicao refeicao) {
        if (refeicao.getNome() == null || refeicao.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (refeicao.getHorario() == null || refeicao.getHorario().trim().equals("")) {
            throw new RegraNegocioException("Horário inválido");
        }
        if (refeicao.getPorcao() == null || refeicao.getPorcao().getId() == null || refeicao.getPorcao().getId() == 0) {
            throw new RegraNegocioException("Porção inválida");
        }   
    }
}
