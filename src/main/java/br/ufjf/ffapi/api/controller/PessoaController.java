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


import br.ufjf.ffapi.api.dto.PessoaDTO;
import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.model.entity.Pessoa;
import br.ufjf.ffapi.service.PessoaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/v1/pessoas")
@RequiredArgsConstructor
@CrossOrigin
public class PessoaController {

    private final PessoaService service;
  
    @GetMapping()
    public ResponseEntity get() {
        List<Pessoa> pessoas = service.getPessoas();
        return ResponseEntity.ok(pessoas.stream().map(PessoaDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter detalhes de uma pessoa")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pessoa encontrada"),
            @ApiResponse(code = 404, message = "Pessoa não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Pessoa> pessoa = service.getPessoaById(id);
        if (!pessoa.isPresent()) {
            return new ResponseEntity("Pessoa não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(pessoa.map(PessoaDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova pessoa")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pessoa salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a pessoa")
    })
    public ResponseEntity post(@RequestBody PessoaDTO dto) {
        try {
            Pessoa pessoa = converter(dto);
            pessoa = service.salvar(pessoa);
            return new ResponseEntity(pessoa, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera uma pessoa")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pessoa alterada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar a pessoa")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PessoaDTO dto) {
        if (!service.getPessoaById(id).isPresent()) {
            return new ResponseEntity("Pessoa não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Pessoa pessoa = converter(dto);
            pessoa.setId(id);
            service.salvar(pessoa);
            return ResponseEntity.ok(pessoa);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui uma pessoa")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pessoa excluída com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a pessoa")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Pessoa> pessoa = service.getPessoaById(id);
        if (!pessoa.isPresent()) {
            return new ResponseEntity("Pessoa não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(pessoa.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Pessoa converter(PessoaDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Pessoa pessoa = modelMapper.map(dto, Pessoa.class);
        return pessoa;
    }
}
