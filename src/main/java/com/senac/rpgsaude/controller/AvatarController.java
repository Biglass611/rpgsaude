package com.senac.rpgsaude.controller;

import com.senac.rpgsaude.dto.request.AvatarDTORequest;
import com.senac.rpgsaude.dto.response.AvatarDTOResponse;
import com.senac.rpgsaude.service.AvatarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("api/avatar")
@Tag(name = "Avatar", description = "API para o gerenciamento de avatares")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar avatar do usuário", description = "Retorna apenas o avatar pertencente ao usuário logado")
    public ResponseEntity<List<AvatarDTOResponse>> listarAvatares(Authentication authentication) {
        // 1. O Spring Security pega o email do token
        String email = authentication.getName();

        // 2. Chamamos o novo método que busca pelo EMAIL, e não por filtro de nome
        return ResponseEntity.ok(avatarService.listarAvatarDoUsuario(email));
    }

    // ===========================================================
    // O RESTO DO CÓDIGO PERMANECE IGUAL
    // ===========================================================

    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<AvatarDTOResponse> listarPorId(@PathVariable("id") Long id) {
        AvatarDTOResponse avatar = avatarService.listarPorId(id);
        return ResponseEntity.ok(avatar);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar novo avatar", description = "Cria um avatar vinculado ao usuário logado")
    public ResponseEntity<AvatarDTOResponse> criarAvatar(
            @Valid @RequestBody AvatarDTORequest avatarDTORequest,
            Authentication authentication) {

        String email = authentication.getName();
        AvatarDTOResponse novoAvatar = avatarService.criarAvatar(avatarDTORequest, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAvatar);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<AvatarDTOResponse> atualizarAvatar(
            @PathVariable("id") Long id,
            @Valid @RequestBody AvatarDTORequest avatarDTORequest) {
        AvatarDTOResponse avatarAtualizado = avatarService.atualizarAvatar(id, avatarDTORequest);
        return ResponseEntity.ok(avatarAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarAvatar(@PathVariable("id") Long id) {
        avatarService.deletarAvatar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/adicionar-moedas")
    public ResponseEntity<Void> adicionarMoedas(
            @PathVariable("id") Long id,
            @RequestBody Integer quantidade) {
        avatarService.adicionarMoedas(id, quantidade);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/atualizar-atributos")
    public ResponseEntity<Void> atualizarAtributos(
            @PathVariable("id") Long id,
            @RequestBody String atributos) {
        avatarService.atualizarAtributos(id, atributos);
        return ResponseEntity.ok().build();
    }
}