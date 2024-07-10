package br.ufjf.ffapi.service;

import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.model.entity.Alimento;
import br.ufjf.ffapi.model.repository.AlimentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AlimentoService {

    private AlimentoRepository repository;

    public AlimentoService(AlimentoRepository repository){
        this.repository = repository;
    }

    public List<Alimento> getAlimentos(){
        return repository.findAll();
    }

    public Optional<Alimento> getAlimentoById(Long id){
        return repository.findById(id);
    }

    @Transactional
    public Alimento salvar(Alimento alimento) {
        validar(alimento);
        return repository.save(alimento);
    }

    @Transactional
    public void excluir(Alimento alimento) {
        Objects.requireNonNull(alimento.getId());
        repository.delete(alimento);
    }

    public void validar(Alimento alimento) {
        if (alimento.getNome() == null || alimento.getNome().trim().equals("")) {
            throw new RegraNegocioException("Nome inválido");
        }
        if (alimento.getProteinas() < 0 ) {
            throw new RegraNegocioException("Quantidade de proteínas inválida");
        }
        if (alimento.getCarboidratos() < 0 ) {
            throw new RegraNegocioException("Quantidade de carboidratos inválida");
        }
        if (alimento.getGorduras() < 0) {
            throw new RegraNegocioException("Quantidade de gorduras inválida");
        }        
    }
}
