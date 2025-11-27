package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.LoginUsuarioDto;
import com.senac.rpgsaude.dto.RecoveryJwtTokenDto;
import com.senac.rpgsaude.dto.request.UsuarioDTORequest;
import com.senac.rpgsaude.dto.response.UsuarioDTOResponse;
import com.senac.rpgsaude.entity.*;
import com.senac.rpgsaude.repository.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final AvatarRepository avatarRepository; // Nome da variável corrigido para minúsculo
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ModelMapper modelMapper,
                          AvatarRepository avatarRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtTokenService jwtTokenService) {
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
        this.avatarRepository = avatarRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @Transactional
    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest usuarioDTORequest) {
        if (usuarioRepository.findByEmail(usuarioDTORequest.getEmail()).isPresent()) {
            throw new EntityExistsException("Este e-mail já está cadastrado.");
        }

        Usuario usuario = modelMapper.map(usuarioDTORequest, Usuario.class);

        // Senha sem criptografia para o banco legado
        usuario.setSenha(usuarioDTORequest.getSenha());
        usuario.setStatus(usuarioDTORequest.getStatus());

        Usuario savedUsuario = usuarioRepository.save(usuario);

        // --- CRIAÇÃO SIMPLIFICADA DO AVATAR ---
        Avatar avatar = new Avatar();
        avatar.setUsuario(savedUsuario);

        // Valores iniciais padrão
        avatar.setNome("Novo Jogador"); // Nome padrão, já que não vem no cadastro
        avatar.setNivel(1);
        avatar.setMoedas(0);
        avatar.setAtributos("Básico"); // String simples, conforme o banco

        avatarRepository.save(avatar);
        // --------------------------------------

        return modelMapper.map(savedUsuario, UsuarioDTOResponse.class);
    }

    public RecoveryJwtTokenDto authenticateUser(LoginUsuarioDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        UsuarioDetailsImpl userDetails = (UsuarioDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTOResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTOResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTOResponse listarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
        return modelMapper.map(usuario, UsuarioDTOResponse.class);
    }

    @Transactional
    public UsuarioDTOResponse atualizarUsuario(Integer id, UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));

        if (usuarioDTORequest.getEmail() != null) usuario.setEmail(usuarioDTORequest.getEmail());
        if(usuarioDTORequest.getSenha() != null && !usuarioDTORequest.getSenha().isEmpty()) {
            usuario.setSenha(usuarioDTORequest.getSenha());
        }
        if (usuarioDTORequest.getStatus() != 0) {
            usuario.setStatus(usuarioDTORequest.getStatus());
        }

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return modelMapper.map(updatedUsuario, UsuarioDTOResponse.class);
    }

    @Transactional
    public void deletarUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado."));
        usuarioRepository.delete(usuario);
    }
}