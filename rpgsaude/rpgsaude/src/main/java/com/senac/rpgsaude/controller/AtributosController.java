package com.senac.rpgsaude.controller;

import com.senac.rpgsaude.dto.request.AtributosDTORequest;
import com.senac.rpgsaude.dto.response.AtributosDTOResponse;
import com.senac.rpgsaude.service.AtributosService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/atributos")
@Tag(name = "Atributos", description = "API para o gerenciamento de atributos")
public class AtributosController {

    private final AtributosService atributosService;

    public AtributosController(AtributosService atributosService) {
        this.atributosService = atributosService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar atributos", description = "Endpoint para listar todos os atributos")
    public ResponseEntity<List<AtributosDTOResponse>> listarAtributoss() {
        return ResponseEntity.ok(atributosService.listarAtributoss());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Listar atributos por ID", description = "Endpoint para buscar um atributos pelo seu ID")
    public ResponseEntity<AtributosDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(atributosService.listarPorId(id));
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar novo atributos ", description = "Endpoint para criar um novo atributos")
    public ResponseEntity<AtributosDTOResponse> criarAtributos(@Valid @RequestBody AtributosDTORequest atributosDTORequest) {
        AtributosDTOResponse novoAtributos = atributosService.criarAtributos(atributosDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAtributos);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar atributos", description = "Endpoint para atualizar todos os dados de um atributos")
    public ResponseEntity<AtributosDTOResponse> atualizarAtributos(
            @PathVariable("id") Integer id,
            @Valid @RequestBody AtributosDTORequest atributosDTORequest) {
        AtributosDTOResponse atributosAtualizado = atributosService.atualizarAtributos(id, atributosDTORequest);
        return ResponseEntity.ok(atributosAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar atributos ", description = "Endpoint para deletar um atributopelo seu ID")
    public ResponseEntity<Void> deletarAtributos(@PathVariable("id") Integer id) {
        atributosService.deletarAtributos(id);
        return ResponseEntity.noContent().build();
    }
}