package com.senac.rpgsaude.controller;

import com.senac.rpgsaude.dto.request.DesafioPremioDTORequest;
import com.senac.rpgsaude.dto.response.DesafioPremioDTOResponse;
import com.senac.rpgsaude.service.DesafioPremioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/desafio")
@Tag(name = "Desafio", description = "API para o gerenciamento de desafio")
public class DesafioController {

    private final DesafioService desafioService;

    public DesafioController(DesafioService desafioService) {
        this.desafioService = desafioService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar desafios", description = "Endpoint para listar todao os desafios")
    public ResponseEntity<List<DesafioDTOResponse>> listarDesafios() {
        return ResponseEntity.ok(desafioService.listarDesafios());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Listar desafio por ID", description = "Endpoint para buscar um desafio pelo seu ID")
    public ResponseEntity<DesafioDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(desafioService.listarPorId(id));
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar novo desafio", description = "Endpoint para criar um novo registro de desafio")
    public ResponseEntity<DesafioDTOResponse> criarDesafio(@Valid @RequestBody DesafioDTORequest desafioDTORequest) {
        DesafioDTOResponse novoDesafio = desafioService.criarDesafio(desafioDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDesafio);
    }


    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar desafio", description = "Endpoint para deletar um desafio pelo seu ID")
    public ResponseEntity<Void> deletarDesafio(@PathVariable("id") Integer id) {
        desafioService.deletarDesafio(id);
        return ResponseEntity.noContent().build();
    }
}