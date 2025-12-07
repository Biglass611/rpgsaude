package com.senac.rpgsaude.controller;

import com.senac.rpgsaude.dto.LoginUserDto;
import com.senac.rpgsaude.dto.RecoveryJwtTokenDto;
import com.senac.rpgsaude.dto.request.UsuarioDTORequest;
import com.senac.rpgsaude.dto.response.UsuarioDTOResponse;
import com.senac.rpgsaude.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") // Deve bater com o SecurityConfig
@Tag(name = "Usuário", description = "API para gerenciamento de usuários e autenticação")
@CrossOrigin(origins = "*") // Libera acesso para celular/web
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // --- 1. LOGIN ---
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Autentica e retorna Token JWT")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = usuarioService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    // --- 2. CRIAR CONTA ---
    @PostMapping("/criar")
    @Operation(summary = "Criar usuário", description = "Cria novo cadastro (Email e Senha)")
    public ResponseEntity<UsuarioDTOResponse> criarUsuario(@Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse novoUsuario = usuarioService.criarUsuario(usuarioDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    // --- 3. LISTAR TODOS ---
    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    // --- 4. BUSCAR POR ID ---
    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<UsuarioDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(usuarioService.listarPorId(id));
    }

    // --- 5. ATUALIZAR ---
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuario(
            @PathVariable("id") Integer id,
            @Valid @RequestBody UsuarioDTORequest usuarioDTORequest) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioDTORequest));
    }

    // --- 6. DELETAR ---
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}