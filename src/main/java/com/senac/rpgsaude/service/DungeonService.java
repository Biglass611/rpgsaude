package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.request.DungeonDTORequest;
import com.senac.rpgsaude.dto.response.DungeonDTOResponse;
import com.senac.rpgsaude.dto.response.RankingDTOResponse;
import com.senac.rpgsaude.entity.Desafio;
import com.senac.rpgsaude.entity.Dungeon;
import com.senac.rpgsaude.entity.Usuario;
import com.senac.rpgsaude.repository.DesafioRepository;
import com.senac.rpgsaude.repository.DungeonRepository;
import com.senac.rpgsaude.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DungeonService {
    private final DungeonRepository dungeonRepository;
    private final UsuarioRepository usuarioRepository;
    private final DesafioRepository desafioRepository;

    @Autowired
    public DungeonService(DungeonRepository dungeonRepository,
                          UsuarioRepository usuarioRepository,
                          DesafioRepository desafioRepository) {
        this.dungeonRepository = dungeonRepository;
        this.usuarioRepository = usuarioRepository;
        this.desafioRepository = desafioRepository;
    }

    public List<RankingDTOResponse> gerarRankingPorDesafio(Integer desafioId) {
        List<Dungeon> dungeons = dungeonRepository.findByDesafioId(desafioId);
        List<RankingDTOResponse> ranking = new ArrayList<>();

        for (Dungeon d : dungeons) {
            if (d.getUsuario() != null) {
                // Placeholder seguro, pois removemos o getPersonagem() da Entidade Usuario
                ranking.add(new RankingDTOResponse(
                        d.getUsuario().getEmail(),
                        1,
                        0
                ));
            }
        }
        Collections.sort(ranking);
        return ranking;
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
    public DungeonDTOResponse criarDungeon(DungeonDTORequest dto) {
        Dungeon dungeon = new Dungeon();
        updateDungeonFromDto(dungeon, dto);

        Dungeon savedDungeon = dungeonRepository.save(dungeon);
        return toResponseDTO(savedDungeon);
    }

    @Transactional
    public DungeonDTOResponse atualizarDungeon(Integer id, DungeonDTORequest dto) {
        Dungeon dungeon = dungeonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dungeon com ID " + id + " não encontrada."));

        updateDungeonFromDto(dungeon, dto);

        Dungeon updatedDungeon = dungeonRepository.save(dungeon);
        return toResponseDTO(updatedDungeon);
    }

    @Transactional
    public void deletarDungeon(Integer id) {
        if (!dungeonRepository.existsById(id)) {
            throw new EntityNotFoundException("Dungeon com ID " + id + " não encontrada.");
        }
        dungeonRepository.deleteById(id);
    }

    private DungeonDTOResponse toResponseDTO(Dungeon dungeon) {
        DungeonDTOResponse dto = new DungeonDTOResponse();
        dto.setId(dungeon.getId());
        dto.setNome(dungeon.getNome());
        dto.setDificuldade(dungeon.getDificuldade());
        dto.setStatus(dungeon.getStatus());

        if (dungeon.getUsuario() != null) {
            dto.setNomeUsuario(dungeon.getUsuario().getEmail());
        }
        if (dungeon.getDesafio() != null) {
            dto.setNomeDesafio(dungeon.getDesafio().getNome());
            dto.setDesafioId(dungeon.getDesafio().getId());
        }
        return dto;
    }

    private void updateDungeonFromDto(Dungeon dungeon, DungeonDTORequest dto) {
        dungeon.setNome(dto.getNome());
        dungeon.setDificuldade(dto.getDificuldade());
        dungeon.setStatus(dto.getStatus());

        Integer usuarioId = dto.getUsuarioId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + usuarioId + " não encontrado."));
        dungeon.setUsuario(usuario);

        if (dto.getDesafioId() != null) {
            Desafio desafio = desafioRepository.findById(dto.getDesafioId())
                    .orElseThrow(() -> new EntityNotFoundException("Desafio com ID " + dto.getDesafioId() + " não encontrado."));
            dungeon.setDesafio(desafio);
        }
    }
}