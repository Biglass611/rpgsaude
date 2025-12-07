package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.LoginUserDto;
import com.senac.rpgsaude.dto.RecoveryJwtTokenDto;
import com.senac.rpgsaude.dto.request.UsuarioDTORequest;
import com.senac.rpgsaude.dto.response.UsuarioDTOResponse;
import com.senac.rpgsaude.entity.Role;
import com.senac.rpgsaude.entity.RoleName;
import com.senac.rpgsaude.entity.Usuario;
import com.senac.rpgsaude.repository.RoleRepository;
import com.senac.rpgsaude.repository.UsuarioRepository;
import com.senac.rpgsaude.service.UserService.JwtTokenService;
import com.senac.rpgsaude.service.UserService.UserDetailsImpl;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    // --- LOGIN ---
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Ajuste: Cast para UserDetailsImpl conforme seu JwtTokenService espera
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    // --- CRIAR USUÁRIO ---
    @Transactional
    public UsuarioDTOResponse criarUsuario(UsuarioDTORequest usuarioDTORequest) {
        if (usuarioRepository.findByEmail(usuarioDTORequest.getEmail()).isPresent()) {
            throw new EntityExistsException("E-mail já cadastrado.");
        }

        Usuario usuario = new Usuario();

        // Mapeamento MANUAL (Seguro e evita erros de campos inexistentes)
        usuario.setEmail(usuarioDTORequest.getEmail());
        usuario.setSenha(passwordEncoder.encode(usuarioDTORequest.getSenha()));
        usuario.setStatus(1); // Força status ativo

        // Busca a Role USER pelo nome (Cria se não existir)
        Role role = roleRepository.findByName(RoleName.USER)
                .orElseGet(() -> {
                    Role novaRole = new Role();
                    novaRole.setName(RoleName.USER);
                    return roleRepository.save(novaRole);
                });

        usuario.setRoles(List.of(role));

        // Salva no banco (Sem nome, pois a tabela não tem coluna nome)
        Usuario savedUsuario = usuarioRepository.save(usuario);

        return modelMapper.map(savedUsuario, UsuarioDTOResponse.class);
    }

    // --- LISTAR TODOS ---
    @Transactional(readOnly = true)
    public List<UsuarioDTOResponse> listarUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(u -> modelMapper.map(u, UsuarioDTOResponse.class))
                .collect(Collectors.toList());
    }

    // --- LISTAR POR ID ---
    @Transactional(readOnly = true)
    public UsuarioDTOResponse listarPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));
        return modelMapper.map(usuario, UsuarioDTOResponse.class);
    }

    // --- ATUALIZAR ---
    @Transactional
    public UsuarioDTOResponse atualizarUsuario(Integer id, UsuarioDTORequest usuarioDTORequest) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado."));

        // Atualiza apenas Email, Senha e Status
        if (usuarioDTORequest.getEmail() != null) {
            usuario.setEmail(usuarioDTORequest.getEmail());
        }

        if (usuarioDTORequest.getSenha() != null && !usuarioDTORequest.getSenha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(usuarioDTORequest.getSenha()));
        }

        if (usuarioDTORequest.getStatus() != 0) {
            usuario.setStatus(usuarioDTORequest.getStatus());
        }

        Usuario updated = usuarioRepository.save(usuario);
        return modelMapper.map(updated, UsuarioDTOResponse.class);
    }

    // --- DELETAR ---
    @Transactional
    public void deletarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}