package com.senac.rpgsaude.controller;

import com.senac.rpgsaude.dto.request.LoginDTORequest; // Import Novo
import com.senac.rpgsaude.dto.response.LoginDTOResponse; // Import Novo
import com.senac.rpgsaude.dto.request.UsuarioDTORequest;
import com.senac.rpgsaude.dto.response.UsuarioDTOResponse;
import com.senac.rpgsaude.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuário", description = "API para o gerenciamento de usuários e autenticação")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // --- CORREÇÃO AQUI ---
    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Autentica um usuário e retorna um token JWT")
    public ResponseEntity<LoginDTOResponse> login(@RequestBody LoginDTORequest loginRequest) {
        LoginDTOResponse token = usuarioService.authenticateUser(loginRequest);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    // ---------------------

    @PostMapping("/criar")
    @Operation(summary = "Criar novo usuário", description = "Endpoint para criar um novo registro de usuário")
    public ResponseEntity<UsuarioDTOResponse> criarUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse novoUsuario = usuarioService.criarUsuario(usuarioDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/listarPorId/{id}")
    public ResponseEntity<UsuarioDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(usuarioService.listarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuario(@PathVariable("id") Integer id, @RequestBody UsuarioDTORequest usuarioDTORequest) {
        return ResponseEntity.ok(usuarioService.atualizarUsuario(id, usuarioDTORequest));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}