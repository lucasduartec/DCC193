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

import br.ufjf.ffapi.api.dto.PlanoDTO;
import br.ufjf.ffapi.exception.RegraNegocioException;
import br.ufjf.ffapi.model.entity.Plano;
import br.ufjf.ffapi.service.PlanoService;


@RestController
@RequestMapping("/api/v1/planos")
@RequiredArgsConstructor
@CrossOrigin
public class PlanoController {
    private final PlanoService service;
  
    @GetMapping()
    public ResponseEntity get() {
        List<Plano> planos = service.getPlanos();
        return ResponseEntity.ok(planos.stream().map(PlanoDTO::create).collect(Collectors.toList()));
    }

    @ApiOperation("Obter detalhes de um plano")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Plano encontrado"),
            @ApiResponse(code = 404, message = "Plano não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Optional<Plano> plano = service.getPlanoById(id);
        if (!plano.isPresent()) {
            return new ResponseEntity("Plano não encontrado", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(plano.map(PlanoDTO::create));
    }

    @PostMapping()
    @ApiOperation("Salva um novo plano")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plano salvo com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao salvar o plano")
    })
    public ResponseEntity post(@RequestBody PlanoDTO dto) {
        try {
            Plano plano = converter(dto);
            plano = service.salvar(plano);
            return new ResponseEntity(plano, HttpStatus.CREATED);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    @ApiOperation("Altera um plano")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plano alterado com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao alterar o plano")
    })
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody PlanoDTO dto) {
        if (!service.getPlanoById(id).isPresent()) {
            return new ResponseEntity("Plano não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            Plano plano = converter(dto);
            plano.setId(id);
            service.salvar(plano);
            return ResponseEntity.ok(plano);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    @ApiOperation("Exclui um plano")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Plano excluído com sucesso"),
            @ApiResponse(code = 400, message = "Erro ao excluir o plano")
    })
    public ResponseEntity excluir(@PathVariable("id") Long id) {
        Optional<Plano> plano = service.getPlanoById(id);
        if (!plano.isPresent()) {
            return new ResponseEntity("Plano não encontrado", HttpStatus.NOT_FOUND);
        }
        try {
            service.excluir(plano.get());
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public Plano converter(PlanoDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        Plano plano = modelMapper.map(dto, Plano.class);
        return plano;
    }
}
