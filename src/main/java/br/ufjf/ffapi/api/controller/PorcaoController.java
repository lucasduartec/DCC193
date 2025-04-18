package br.ufjf.ffapi.api.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

import br.ufjf.ffapi.api.dto.PorcaoDTO;
import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.model.entity.Porcao;
import br.ufjf.ffapi.service.PorcaoService;


@RestController
@RequestMapping("/api/v1/porcao")
@RequiredArgsConstructor
@CrossOrigin
public class PorcaoController {
    
    private final PorcaoService service;
  
    @GetMapping()
    public ResponseEntity get() {
        List<Porcao> porcoes = service.getPorcaos();
        return ResponseEntity.ok(porcoes.stream().map(PorcaoDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter detalhes de uma porção")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Porção encontrada"),
            @ApiResponse(code = 404, message = "Porção não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Porcao> porcao = service.getPorcaoById(id);
        if (!porcao.isPresent()) {
            return new ResponseEntity("Porção não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(porcao.map(PorcaoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova porção")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Porção salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a porção")
    })
    public ResponseEntity post(@RequestBody PorcaoDTO dto) {
        try {
            Porcao porcao = converter(dto);
            porcao = service.salvar(porcao);
            return new ResponseEntity(porcao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera uma porção")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Porção alterada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar a porção")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PorcaoDTO dto) {
        if (!service.getPorcaoById(id).isPresent()) {
            return new ResponseEntity("Porção não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Porcao porcao = converter(dto);
            porcao.setId(id);
            service.salvar(porcao);
            return ResponseEntity.ok(porcao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui uma porção")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Porção excluída com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a porção")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Porcao> porcao = service.getPorcaoById(id);
        if (!porcao.isPresent()) {
            return new ResponseEntity("Porção não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(porcao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Porcao converter(PorcaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Porcao porcao = modelMapper.map(dto, Porcao.class);
        return porcao;
    }
}
