package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.request.LoginDTORequest;
import com.senac.rpgsaude.dto.response.LoginDTOResponse;
import com.senac.rpgsaude.dto.request.UsuarioDTORequest;
import com.senac.rpgsaude.dto.response.UsuarioDTOResponse;
import com.senac.rpgsaude.entity.*;
import com.senac.rpgsaude.repository.*;
import com.senac.rpgsaude.security.TokenService; // Importa o novo TokenService
// Removemos o import do JwtTokenService antigo se ele existir

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private AvatarRepository avatarRepository;

    @Autowired private TokenService tokenService; // Usamos o TokenService aqui

    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com email: " + username));
    }

    @Transactional
    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest usuarioDTORequest) {
        if (usuarioRepository.findByEmail(usuarioDTORequest.getEmail()).isPresent()) {
            throw new EntityExistsException("E-mail já cadastrado.");
        }

        Usuario usuario = modelMapper.map(usuarioDTORequest, Usuario.class);
        usuario.setSenha(passwordEncoder.encode(usuarioDTORequest.getSenha()));
        usuario.setStatus(usuarioDTORequest.getStatus());

        Usuario savedUsuario = usuarioRepository.save(usuario);

        Avatar avatar = new Avatar();
        avatar.setUsuario(savedUsuario);
        avatar.setNome("Avatar de " + savedUsuario.getEmail());
        avatar.setNivel(1);
        avatar.setMoedas(0);
        avatar.setAtributos("Força: 1, Agilidade: 1");

        avatarRepository.save(avatar);

        return modelMapper.map(savedUsuario, UsuarioDTOResponse.class);
    }

    public LoginDTOResponse authenticateUser(LoginDTORequest loginRequest) {
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.getSenha(), usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        // Chama o método generateToken do TokenService passando o usuario
        String token = tokenService.generateToken(usuario);

        return new LoginDTOResponse(token);
    }

    // ... (o restante dos métodos listar, atualizar, deletar continua igual)
    @Transactional(readOnly = true)
    public List<UsuarioDTOResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(u -> modelMapper.map(u, UsuarioDTOResponse.class))
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
            usuario.setSenha(passwordEncoder.encode(usuarioDTORequest.getSenha()));
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