package com.senac.rpgsaude.controller;

import com.senac.rpgsaude.dto.request.RegistroMoedaDTORequest;
import com.senac.rpgsaude.dto.response.RegistroMoedaDTOResponse;
import com.senac.rpgsaude.service.RegistroMoedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/registro-moeda")
@Tag(name = "Registro de Moeda", description = "API para o gerenciamento de registros de moeda")
public class RegistroMoedaController {

    private final RegistroMoedaService registroMoedaService;

    public RegistroMoedaController(RegistroMoedaService registroMoedaService) {
        this.registroMoedaService = registroMoedaService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar registros de moeda", description = "Endpoint para listar todos os registros de moeda")
    public ResponseEntity<List<RegistroMoedaDTOResponse>> listarRegistrosMoeda() {
        return ResponseEntity.ok(registroMoedaService.listarRegistrosMoeda());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Listar registro de moeda por ID", description = "Endpoint para buscar um registro de moeda pelo seu ID")
    public ResponseEntity<RegistroMoedaDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(registroMoedaService.listarPorId(id));
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar novo registro de moeda", description = "Endpoint para criar um novo registro de moeda")
    public ResponseEntity<RegistroMoedaDTOResponse> criarRegistroMoeda(@Valid @RequestBody RegistroMoedaDTORequest registroMoedaDTORequest) {
        RegistroMoedaDTOResponse novoRegistroMoeda = registroMoedaService.criarRegistroMoeda(registroMoedaDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRegistroMoeda);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar registro de moeda", description = "Endpoint para atualizar todos os dados de um registro de moeda")
    public ResponseEntity<RegistroMoedaDTOResponse> atualizarRegistroMoeda(
            @PathVariable("id") Integer id,
            @Valid @RequestBody RegistroMoedaDTORequest registroMoedaDTORequest) {
        RegistroMoedaDTOResponse registroMoedaAtualizado = registroMoedaService.atualizarRegistroMoeda(id, registroMoedaDTORequest);
        return ResponseEntity.ok(registroMoedaAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar registro de moeda", description = "Endpoint para deletar um registro de moeda pelo seu ID")
    public ResponseEntity<Void> deletarRegistroMoeda(@PathVariable("id") Integer id) {
        registroMoedaService.deletarRegistroMoeda(id);
        return ResponseEntity.noContent().build();
    }
}