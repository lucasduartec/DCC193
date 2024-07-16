package br.ufjf.ffapi.api.controller;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.ufjf.ffapi.model.entity.Alimento;
import br.ufjf.ffapi.api.dto.AlimentoDTO;
import br.ufjf.ffapi.service.AlimentoService;
import br.ufjf.ffapi.exception.RegraNegocioException;

@RestController
@RequestMapping("/api/v1/alimentos")
@RequiredArgsConstructor
@CrossOrigin
public class AlimentoController {

    private final AlimentoService service;
  
    @GetMapping()
    public ResponseEntity get() {
        List<Alimento> alimentos = service.getAlimentos();
        return ResponseEntity.ok(alimentos.stream().map(AlimentoDTO::create).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Alimento> alimento = service.getAlimentoById(id);
        if (!alimento.isPresent()) {
            return new ResponseEntity("Alimento não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(alimento.map(AlimentoDTO::create));
    }

    @PostMapping()
    public ResponseEntity post(@RequestBody AlimentoDTO dto) {
        try {
            Alimento alimento = converter(dto);
            alimento = service.salvar(alimento);
            return new ResponseEntity(alimento, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody AlimentoDTO dto) {
        if (!service.getAlimentoById(id).isPresent()) {
            return new ResponseEntity("Alimento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Alimento alimento = converter(dto);
            alimento.setId(id);
            service.salvar(alimento);
            return ResponseEntity.ok(alimento);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Alimento> alimento = service.getAlimentoById(id);
        if (!alimento.isPresent()) {
            return new ResponseEntity("Alimento não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(alimento.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Alimento converter(AlimentoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Alimento alimento = modelMapper.map(dto, Alimento.class);
        return alimento;
    }
}
