package com.senac.rpgsaude.controller;

import com.senac.rpgsaude.dto.request.AvatarMoedasDTORequest;
import com.senac.rpgsaude.dto.response.AvatarMoedasDTOResponse;
import com.senac.rpgsaude.service.AvatarMoedasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/avatar-moedas")
@Tag(name = "Avatar de Moedas", description = "API para o gerenciamento de avatars de moedas")
public class AvatarMoedasController {

    private final AvatarMoedasService avatarMoedasService;

    public AvatarMoedasController(AvatarMoedasService avatarMoedasService) {
        this.avatarMoedasService = avatarMoedasService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar moeda de avatares", description = "Endpoint para listar todas moedas do avatar")
    public ResponseEntity<List<AvatarMoedasDTOResponse>> listarAvatarsMoedas() {
        return ResponseEntity.ok(avatarMoedasService.listarAvatarMoeda());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Listar moeda de avatares por ID", description = "Endpoint para buscar moeda de avatares pelo seu ID")
    public ResponseEntity<AvatarMoedasDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(avatarMoedasService.listarPorId(id));
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar novo moeda de avatares", description = "Endpoint para criar moeda de avatares")
    public ResponseEntity<AvatarMoedasDTOResponse> criarAvatarMoedas(@Valid @RequestBody AvatarMoedasDTORequest avatarMoedasDTORequest) {
        AvatarMoedasDTOResponse novoAvatarMoedas = avatarMoedasService.criarAvatarMoedas(avatarMoedasDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAvatarMoedas);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar moeda de avatares", description = "Endpoint para atualizar todos os dados de um moeda de avatares")
    public ResponseEntity<AvatarMoedasDTOResponse> atualizarAvatarMoedas(
            @PathVariable("id") Integer id,
            @Valid @RequestBody AvatarMoedasDTORequest avatarMoedasDTORequest) {
        AvatarMoedasDTOResponse avatarMoedasAtualizado = avatarMoedasService.atualizarAvatarMoedas(id, avatarMoedasDTORequest);
        return ResponseEntity.ok(avatarMoedasAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar moeda de avatares", description = "Endpoint para deletar uma moeda de avatar pelo seu ID")
    public ResponseEntity<Void> deletarAvatarMoedas(@PathVariable("id") Integer id) {
        avatarMoedasService.deletarAvatarMoedas(id);
        return ResponseEntity.noContent().build();
    }
}