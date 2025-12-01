package com.senac.rpgsaude.controller;

import com.senac.rpgsaude.dto.LoginUsuarioDto;
import com.senac.rpgsaude.dto.RecoveryJwtTokenDto;
import com.senac.rpgsaude.dto.request.UsuarioDTORequest;
import com.senac.rpgsaude.dto.response.UsuarioDTOResponse;
import com.senac.rpgsaude.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; // Importante para validar o @RequestBody
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users") // Ajustado para bater com SecurityConfig padrão (/users/login)
@Tag(name = "Usuário", description = "API para o gerenciamento de usuários e autenticação")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Autentica um usuário e retorna um token JWT")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginUsuarioDto loginUserDto) {
        System.out.println(">>> 1. TENTATIVA DE LOGIN RECEBIDA");
        System.out.println(">>> Email recebido: " + loginUserDto.email());
        System.out.println(">>> Senha recebida: " + loginUserDto.password());

        try {
            // Chamando o serviço
            RecoveryJwtTokenDto token = usuarioService.authenticateUser(loginUserDto);

            System.out.println(">>> 2. LOGIN SUCESSO! Token gerado.");
            return new ResponseEntity<>(token, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(">>> 3. ERRO NO LOGIN!");
            e.printStackTrace(); // Isso vai imprimir o erro exato no console
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/criar") // Será acessado como /users/criar
    @Operation(summary = "Criar novo usuário", description = "Endpoint para criar um novo registro de usuário")
    public ResponseEntity<UsuarioDTOResponse> criarUsuario(@RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse novoUsuario = usuarioService.criarUsuario(usuarioDTORequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar usuários", description = "Endpoint para listar todos os usuários")
    public ResponseEntity<List<UsuarioDTOResponse>> listarUsuarios() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/listarPorId/{id}")
    @Operation(summary = "Listar usuário por ID", description = "Endpoint para buscar um usuário pelo seu ID")
    public ResponseEntity<UsuarioDTOResponse> listarPorId(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(usuarioService.listarPorId(id));
    }

    @PutMapping("/atualizar/{id}")
    @Operation(summary = "Atualizar usuário", description = "Endpoint para atualizar todos os dados de um usuário")
    public ResponseEntity<UsuarioDTOResponse> atualizarUsuario(
            @PathVariable("id") Integer id,
            @RequestBody UsuarioDTORequest usuarioDTORequest) {
        UsuarioDTOResponse usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioDTORequest);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    @Operation(summary = "Deletar usuário", description = "Endpoint para deletar um usuário pelo seu ID")
    public ResponseEntity<Void> deletarUsuario(@PathVariable("id") Integer id) {
        usuarioService.deletarUsuario(id);
        return ResponseEntity.noContent().build();
    }
}