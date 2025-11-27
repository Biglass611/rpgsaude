package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.request.DungeonDTORequest;
import com.senac.rpgsaude.dto.response.DungeonDTOResponse;
import com.senac.rpgsaude.entity.Dungeon;
import com.senac.rpgsaude.entity.Avatar;
import com.senac.rpgsaude.repository.DungeonRepository;
import com.senac.rpgsaude.repository.AvatarRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DungeonService {
    private final DungeonRepository dungeonRepository;
    private final AvatarRepository avatarRepository;

    @Autowired
    public DungeonService(DungeonRepository dungeonRepository, AvatarRepository avatarRepository) {
        this.dungeonRepository = dungeonRepository;
        this.avatarRepository = avatarRepository;
    }

    public List<DungeonDTOResponse> listarDungeon() {
        return dungeonRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DungeonDTOResponse listarPorId(Integer id) {
        Dungeon dungeon = dungeonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dungeon com ID " + id + " não encontrada."));
        return toResponseDTO(dungeon);
    }

    @Transactional
    public DungeonDTOResponse criarDungeon(DungeonDTORequest dungeonDTORequest) {
        Dungeon dungeon = new Dungeon();
        updateDungeonFromDto(dungeon, dungeonDTORequest);

        Dungeon savedDungeon = dungeonRepository.save(dungeon);
        return toResponseDTO(savedDungeon);
    }

    @Transactional
    public DungeonDTOResponse atualizarDungeon(Integer id, DungeonDTORequest dungeonDTORequest) {
        Dungeon dungeon = dungeonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dungeon com ID " + id + " não encontrada."));

        updateDungeonFromDto(dungeon, dungeonDTORequest);

        Dungeon updatedDungeon = dungeonRepository.save(dungeon);
        return toResponseDTO(updatedDungeon);
    }

    @Transactional
    public void deletarDungeon(Integer id) {
        dungeonRepository.deleteById(id);
    }

    // Métodos utilitários para conversão
    private DungeonDTOResponse toResponseDTO(Dungeon dungeon) {
        DungeonDTOResponse dto = new DungeonDTOResponse();
        dto.setId(dungeon.getId());
        dto.setDificuldade(dungeon.getDificuldade());
        dto.setStatus(dungeon.getStatus());
        if (dungeon.getAvatar() != null) {
            dto.setNomeAvatar(dungeon.getAvatar().getUsuario().getEmail());
        }
        return dto;
    }

    private void updateDungeonFromDto(Dungeon dungeon, DungeonDTORequest dto) {

        dungeon.setDificuldade(dto.getDificuldade());
        dungeon.setStatus(dto.getStatus());

        // CORREÇÃO: Convertemos o Integer do DTO para Long, pois o AvatarRepository espera Long.
        Long avatarId = Long.valueOf(dto.getAvatarId());

        Avatar avatar = avatarRepository.findById(avatarId)
                .orElseThrow(() -> new EntityNotFoundException("Avatar com ID " + avatarId + " não encontrado."));
        dungeon.setAvatar(avatar);
    }
}