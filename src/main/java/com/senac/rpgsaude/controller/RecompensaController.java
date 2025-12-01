package com.senac.rpgsaude.controller;

import com.senac.rpgsaude.dto.request.RecompensaDTORequest;
import com.senac.rpgsaude.dto.response.RecompensaDTOResponse;
import com.senac.rpgsaude.service.RecompensaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recompensa")
@Tag(name = "Recompensa", description = "API para o gerenciamento de recompensas")
public class RecompensaController {

    private final RecompensaService recompensaService;

    public RecompensaController(RecompensaService recompensaService) {
        this.recompensaService = recompensaService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar recompensas", description = "Endpoint para listar todos as recompensas")
    public ResponseEntity<List<RecompensaDTOResponse>> listarRecompensas() {
        return ResponseEntity.ok(recompensaService.listarRecompensas());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Listar recompensa por ID", description = "Endpoint para buscar uma recompensa pelo seu ID")
    public ResponseEntity<RecompensaDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(recompensaService.listarPorId(id));
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar novo recompensa", description = "Endpoint para criar um novo registro de recompensa")
    public ResponseEntity<RecompensaDTOResponse> criarRecompensa(@Valid @RequestBody RecompensaDTORequest recompensaDTORequest) {
        RecompensaDTOResponse novoRecompensa = recompensaService.criarRecompensa(recompensaDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRecompensa);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar recompensa", description = "Endpoint para atualizar todos os dados de umarecompensa")
    public ResponseEntity<RecompensaDTOResponse> atualizarRecompensa(
            @PathVariable("id") Integer id,
            @Valid @RequestBody RecompensaDTORequest recompensaDTORequest) {
        RecompensaDTOResponse recompensaAtualizado = recompensaService.atualizarRecompensa(id, recompensaDTORequest);
        return ResponseEntity.ok(recompensaAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar recompensa", description = "Endpoint para deletar uma recompensa pelo seu ID")
    public ResponseEntity<Void> deletarRecompensa(@PathVariable("id") Integer id) {
        recompensaService.deletarRecompensa(id);
        return ResponseEntity.noContent().build();
    }
}