package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.request.DesafioDTORequest;
import com.senac.rpgsaude.dto.response.DesafioDTOResponse;
import com.senac.rpgsaude.entity.Desafio;
import com.senac.rpgsaude.entity.Recompensa;
import com.senac.rpgsaude.repository.DesafioRepository;
import com.senac.rpgsaude.repository.RecompensaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesafioService {
    private final DesafioRepository desafioRepository;
    private final RecompensaRepository recompensaRepository;
    @Autowired
    public DesafioService(DesafioRepository desafioRepository,  RecompensaRepository recompensaRepository) {
        this.desafioRepository = desafioRepository;
        this.recompensaRepository = recompensaRepository;
    }

    public List<DesafioDTOResponse> listarDesafios() {
        return desafioRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DesafioDTOResponse listarPorId(Integer id) {
        Desafio desafio = desafioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tabela de Prêmio com ID " + id + " não encontrada."));
        return toResponseDTO(desafio);
    }

    @Transactional
    public DesafioDTOResponse criarDesafio(DesafioDTORequest desafioDTORequest) {
        Desafio desafio = new Desafio();
        updateDesafioFromDto(desafio, desafioDTORequest);

        Desafio savedDesafio = desafioRepository.save(desafio);
        return toResponseDTO(savedDesafio);
    }

    @Transactional
    public void deletarDesafio(Integer id) {
        desafioRepository.deleteById(id);
    }

    private DesafioDTOResponse toResponseDTO(Desafio desafio) {
        DesafioDTOResponse dto = new DesafioDTOResponse();
        dto.setId(desafio.getId());

        // CORREÇÃO #1: Acessar a lista de recompensas e pegar o nome da primeira (ou tratar vazia)
        if (desafio.getRecompensas() != null && !desafio.getRecompensas().isEmpty()) {
            dto.setNomeRecompensa(desafio.getRecompensas().get(0).getNome());
        }
        return dto;
    }

    private void updateDesafioFromDto(Desafio desafio, DesafioDTORequest dto) {
        desafio.setNome(dto.getNome());
        desafio.setDescricao(dto.getDescricao());
        desafio.setTipo(dto.getTipo());
        desafio.setChefeId(dto.getChefeId());

        Recompensa recompensa = recompensaRepository.findById(dto.getRecompensaId())
                .orElseThrow(() -> new EntityNotFoundException("Prêmio com ID " + dto.getRecompensaId() + " não encontrado."));

        recompensa.setDesafio(desafio);
        desafio.setRecompensas(List.of(recompensa));
    }
}