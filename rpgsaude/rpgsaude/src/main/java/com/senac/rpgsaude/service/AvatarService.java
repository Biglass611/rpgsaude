package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.request.AvatarDTORequest;
import com.senac.rpgsaude.dto.response.AvatarDTOResponse;
import com.senac.rpgsaude.entity.Avatar;
import com.senac.rpgsaude.entity.Usuario;
import com.senac.rpgsaude.repository.AvatarRepository;
import com.senac.rpgsaude.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;

    // Removidos: AvatarMoedasRepository e AtributosRepository (não existem mais)
    @Autowired
    public AvatarService(AvatarRepository avatarRepository, UsuarioRepository usuarioRepository, ModelMapper modelMapper) {
        this.avatarRepository = avatarRepository;
        this.usuarioRepository = usuarioRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public AvatarDTOResponse criarAvatar(AvatarDTORequest avatarDTORequest) {
        Usuario usuario = usuarioRepository.findById(avatarDTORequest.getUsuarioId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + avatarDTORequest.getUsuarioId() + " não encontrado."));

        Avatar avatar = new Avatar();

        // Conversão de Tipos: O DTO manda Double, mas o Banco é Integer/String
        if (avatarDTORequest.getNivel() != null) {
            avatar.setNivel(avatarDTORequest.getNivel().intValue());
        }
        if (avatarDTORequest.getMoedas() != null) {
            avatar.setMoedas(avatarDTORequest.getMoedas().intValue());
        }
        if (avatarDTORequest.getAtributos1() != null) {
            // Converte o valor numérico para String, pois o banco é VARCHAR
            avatar.setAtributos(String.valueOf(avatarDTORequest.getAtributos1()));
        }

        // Define nome padrão se não vier no request (opcional)
        avatar.setNome("Avatar de " + usuario.getEmail());

        avatar.setUsuario(usuario);

        // Salva diretamente na tabela 'avatar'
        Avatar savedAvatar = avatarRepository.save(avatar);

        return modelMapper.map(savedAvatar, AvatarDTOResponse.class);
    }

    @Transactional(readOnly = true)
    public List<AvatarDTOResponse> listarAvatar() {
        return avatarRepository.listarTodosAvatares().stream()
                .map(avatar -> modelMapper.map(avatar, AvatarDTOResponse.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AvatarDTOResponse listarPorId(Long id) {
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avatar com ID " + id + " não encontrado."));
        return modelMapper.map(avatar, AvatarDTOResponse.class);
    }

    @Transactional
    public AvatarDTOResponse atualizarAvatar(Long id, AvatarDTORequest avatarDTORequest) {
        Avatar avatar = avatarRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Avatar com ID " + id + " não encontrado."));

        // Atualização manual para garantir conversão de tipos
        if (avatarDTORequest.getNivel() != null) avatar.setNivel(avatarDTORequest.getNivel().intValue());
        if (avatarDTORequest.getMoedas() != null) avatar.setMoedas(avatarDTORequest.getMoedas().intValue());
        if (avatarDTORequest.getAtributos1() != null) avatar.setAtributos(String.valueOf(avatarDTORequest.getAtributos1()));

        if (avatarDTORequest.getUsuarioId() != null) {
            Usuario usuario = usuarioRepository.findById(avatarDTORequest.getUsuarioId())
                    .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + avatarDTORequest.getUsuarioId() + " não encontrado."));
            avatar.setUsuario(usuario);
        }

        Avatar updatedAvatar = avatarRepository.save(avatar);
        return modelMapper.map(updatedAvatar, AvatarDTOResponse.class);
    }

    @Transactional
    public void deletarAvatar(Long id) {
        if (!avatarRepository.existsById(id)) {
            throw new EntityNotFoundException("Avatar com ID " + id + " não encontrado.");
        }
        avatarRepository.deleteById(id);
    }

    @Transactional
    public void adicionarMoedas(Long avatarId, Integer quantidade) {
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new EntityNotFoundException("Avatar com ID " + avatarId + " não encontrado."));

        int novaQuantidade = (avatar.getMoedas() != null ? avatar.getMoedas() : 0) + quantidade;
        avatar.setMoedas(novaQuantidade);

        avatarRepository.save(avatar);
    }
    // ... dentro de AvatarService ...

    @Transactional
    public void atualizarAtributos(Long avatarId, String novosAtributos) {
        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new EntityNotFoundException("Avatar com ID " + avatarId + " não encontrado."));

        avatar.setAtributos(novosAtributos);
        avatarRepository.save(avatar);
    }
}