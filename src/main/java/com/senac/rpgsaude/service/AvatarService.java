package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.request.AvatarDTORequest;
import com.senac.rpgsaude.dto.response.AvatarDTOResponse;
import com.senac.rpgsaude.entity.Avatar;
import com.senac.rpgsaude.entity.Usuario;
import com.senac.rpgsaude.repository.AvatarRepository;
import com.senac.rpgsaude.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections; // Importante
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvatarService {

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ==================================================================================
    // 1. CRIAR
    // ==================================================================================
    public AvatarDTOResponse criarAvatar(AvatarDTORequest avatarDTORequest, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com email: " + email));

        if (avatarRepository.findByUsuario(usuario).isPresent()) {
            throw new RuntimeException("Este usuário já possui um avatar criado.");
        }

        Avatar avatar = new Avatar();
        avatar.setNome(avatarDTORequest.getNome());
        avatar.setAtributos(avatarDTORequest.getAtributos());
        avatar.setMoedas(avatarDTORequest.getMoedas());
        avatar.setNivel(avatarDTORequest.getNivel());
        avatar.setUsuario(usuario);

        avatar = avatarRepository.save(avatar);

        return converterParaResponse(avatar);
    }

    // ==================================================================================
    // 2. LEITURA (AGORA FILTRANDO PELO USUÁRIO LOGADO)
    // ==================================================================================

    // Mudei o nome do parâmetro para ficar claro que recebemos o EMAIL do token
    public List<AvatarDTOResponse> listarAvatarDoUsuario(String email) {

        // 1. Acha quem é o usuário dono do Token
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        // 2. Busca no banco se existe avatar vinculado a ESTE usuário
        Optional<Avatar> avatarOp = avatarRepository.findByUsuario(usuario);

        // 3. Se existir, retorna uma lista contendo só ele. Se não, retorna lista vazia.
        if (avatarOp.isPresent()) {
            return List.of(converterParaResponse(avatarOp.get()));
        } else {
            return Collections.emptyList(); // Retorna lista vazia []
        }
    }

    public AvatarDTOResponse listarPorId(Long id) {
        Avatar avatar = buscarAvatarPorId(id);
        return converterParaResponse(avatar);
    }

    // ==================================================================================
    // 3. ATUALIZAR, DELETAR e MÉTODOS AUXILIARES (IGUAL AO SEU)
    // ==================================================================================
    public AvatarDTOResponse atualizarAvatar(Long id, AvatarDTORequest request) {
        Avatar avatar = buscarAvatarPorId(id);
        avatar.setNome(request.getNome());
        avatar.setNivel(request.getNivel());
        avatar.setMoedas(request.getMoedas());
        avatar.setAtributos(request.getAtributos());
        avatar = avatarRepository.save(avatar);
        return converterParaResponse(avatar);
    }

    public void deletarAvatar(Long id) {
        if (!avatarRepository.existsById(id)) {
            throw new RuntimeException("Avatar não encontrado para exclusão com ID: " + id);
        }
        avatarRepository.deleteById(id);
    }

    public AvatarDTOResponse adicionarMoedas(Long id, Integer quantidade) {
        Avatar avatar = buscarAvatarPorId(id);
        Integer saldoAtual = avatar.getMoedas() != null ? avatar.getMoedas() : 0;
        avatar.setMoedas(saldoAtual + quantidade);
        avatarRepository.save(avatar);
        return converterParaResponse(avatar);
    }

    public AvatarDTOResponse atualizarAtributos(Long id, String novosAtributos) {
        Avatar avatar = buscarAvatarPorId(id);
        avatar.setAtributos(novosAtributos);
        avatarRepository.save(avatar);
        return converterParaResponse(avatar);
    }

    private Avatar buscarAvatarPorId(Long id) {
        return avatarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avatar não encontrado com ID: " + id));
    }

    private AvatarDTOResponse converterParaResponse(Avatar avatar) {
        AvatarDTOResponse response = new AvatarDTOResponse();
        response.setId(avatar.getId());
        response.setNome(avatar.getNome());
        response.setAtributos(avatar.getAtributos());
        response.setMoedas(avatar.getMoedas());
        response.setNivel(avatar.getNivel());
        if (avatar.getUsuario() != null) {
            response.setUsuarioId(avatar.getUsuario().getId());
        }
        return response;
    }
}