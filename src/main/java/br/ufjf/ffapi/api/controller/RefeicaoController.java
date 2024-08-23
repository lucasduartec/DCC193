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

import br.ufjf.ffapi.api.dto.RefeicaoDTO;
import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.model.entity.Refeicao;
import br.ufjf.ffapi.service.RefeicaoService;


@RestController
@RequestMapping("/api/v1/refeicoes")
@RequiredArgsConstructor
@CrossOrigin
public class RefeicaoController {
    private final RefeicaoService service;
  
    @GetMapping()
    public ResponseEntity get() {
        List<Refeicao> refeicoes = service.getRefeicaos();
        return ResponseEntity.ok(refeicoes.stream().map(RefeicaoDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter detalhes de uma refeição")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Refeição encontrado"),
            @ApiResponse(code = 404, message = "Refeição não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Refeicao> refeicao = service.getRefeicaoById(id);
        if (!refeicao.isPresent()) {
            return new ResponseEntity("Refeição não encontrada", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(refeicao.map(RefeicaoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva uma nova refeição")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Refeição salva com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar a refeição")
    })
    public ResponseEntity post(@RequestBody RefeicaoDTO dto) {
        try {
            Refeicao refeicao = converter(dto);
            refeicao = service.salvar(refeicao);
            return new ResponseEntity(refeicao, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera uma refeição")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Refeição alterada com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar a refeição")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody RefeicaoDTO dto) {
        if (!service.getRefeicaoById(id).isPresent()) {
            return new ResponseEntity("Refeição não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            Refeicao refeicao = converter(dto);
            refeicao.setId(id);
            service.salvar(refeicao);
            return ResponseEntity.ok(refeicao);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui uma refeição")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Refeição excluída com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir a refeição")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Refeicao> refeicao = service.getRefeicaoById(id);
        if (!refeicao.isPresent()) {
            return new ResponseEntity("Refeição não encontrada", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(refeicao.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Refeicao converter(RefeicaoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Refeicao refeicao = modelMapper.map(dto, Refeicao.class);
        return refeicao;
    }
}
