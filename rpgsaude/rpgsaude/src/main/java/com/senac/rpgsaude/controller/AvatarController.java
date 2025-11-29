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

@RestController
@RequestMapping("api/avatar")
@Tag(name = "Avatar", description = "API para o gerenciamento de avatares")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar avatares", description = "Endpoint para listar todos os avatares")
    public ResponseEntity<List<AvatarDTOResponse>> listarAvatares() {
        // CORRIGIDO: Chama 'listarAvatar' (que tem a lógica) em vez de 'listarAvatares' (que estava retornando vazio no service antigo)
        return ResponseEntity.ok(avatarService.listarAvatar());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Listar avatar por ID", description = "Endpoint para buscar um avatar pelo seu ID")
    // CORRIGIDO: Tipo do ID alterado de Integer para Long
    public ResponseEntity<AvatarDTOResponse> listarPorId(@PathVariable("id") Long id) {
        AvatarDTOResponse avatar = avatarService.listarPorId(id);
        return ResponseEntity.ok(avatar);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar novo avatar", description = "Endpoint para criar um novo registro de avatar")
    public ResponseEntity<AvatarDTOResponse> criarAvatar(@Valid @RequestBody AvatarDTORequest avatarDTORequest) {
        AvatarDTOResponse novoAvatar = avatarService.criarAvatar(avatarDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoAvatar);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar avatar", description = "Endpoint para atualizar todos os dados de um avatar")
    public ResponseEntity<AvatarDTOResponse> atualizarAvatar(
            @PathVariable("id") Long id, // CORRIGIDO: Tipo do ID alterado de Integer para Long
            @Valid @RequestBody AvatarDTORequest avatarDTORequest) {
        AvatarDTOResponse avatarAtualizado = avatarService.atualizarAvatar(id, avatarDTORequest);
        return ResponseEntity.ok(avatarAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar avatar", description = "Endpoint para deletar um avatar pelo seu ID")
    // CORRIGIDO: Tipo do ID alterado de Integer para Long
    public ResponseEntity<Void> deletarAvatar(@PathVariable("id") Long id) {
        avatarService.deletarAvatar(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{id}/adicionar-moedas")
    @Operation(summary = "Adicionar moedas", description = "Adiciona uma quantidade de moedas ao avatar")
    public ResponseEntity<Void> adicionarMoedas(
            @PathVariable("id") Long id,
            @RequestBody Integer quantidade) {

        avatarService.adicionarMoedas(id, quantidade);
        return ResponseEntity.ok().build();
    }
    // ... dentro de AvatarController ...

    @PutMapping("/{id}/atualizar-atributos")
    @Operation(summary = "Atualizar atributos", description = "Define os atributos do avatar (Ex: 'Força: 10, Agilidade: 5')")
    public ResponseEntity<Void> atualizarAtributos(
            @PathVariable("id") Long id,
            @RequestBody String atributos) {

        avatarService.atualizarAtributos(id, atributos);
        return ResponseEntity.ok().build();
    }
}