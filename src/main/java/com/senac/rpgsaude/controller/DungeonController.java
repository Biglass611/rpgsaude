package com.senac.rpgsaude.controller;

import com.senac.rpgsaude.dto.request.DungeonDTORequest;
import com.senac.rpgsaude.dto.response.DungeonDTOResponse;
import com.senac.rpgsaude.service.DungeonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/dungeon")
@Tag(name = "Dungeon", description = "API para o gerenciamento de dungeons")
public class DungeonController {

    private final DungeonService dungeonService;

    public DungeonController(DungeonService dungeonService) {
        this.dungeonService = dungeonService;
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar dungeons", description = "Lista todas as dungeons")
    public ResponseEntity<List<DungeonDTOResponse>> listarDungeons() {
        return ResponseEntity.ok(dungeonService.listarDungeon());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Buscar dungeon por ID", description = "Retorna uma dungeon específica")
    public ResponseEntity<DungeonDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(dungeonService.listarPorId(id));
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar nova dungeon", description = "Cria uma nova dungeon vinculada a um usuário e um desafio")
    public ResponseEntity<DungeonDTOResponse> criarDungeon(@Valid @RequestBody DungeonDTORequest dto) {
        DungeonDTOResponse novaDungeon = dungeonService.criarDungeon(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaDungeon);
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar dungeon", description = "Atualiza dados de uma dungeon")
    public ResponseEntity<DungeonDTOResponse> atualizarDungeon(
            @PathVariable("id") Integer id,
            @Valid @RequestBody DungeonDTORequest dto) {
        return ResponseEntity.ok(dungeonService.atualizarDungeon(id, dto));
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar dungeon", description = "Remove uma dungeon do sistema")
    public ResponseEntity<Void> deletarDungeon(@PathVariable("id") Integer id) {
        dungeonService.deletarDungeon(id);
        return ResponseEntity.noContent().build();
    }
}