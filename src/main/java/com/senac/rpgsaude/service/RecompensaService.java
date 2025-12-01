package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.request.RecompensaDTORequest;
import com.senac.rpgsaude.dto.response.RecompensaDTOResponse;
import com.senac.rpgsaude.entity.Desafio;
import com.senac.rpgsaude.entity.Recompensa;
import com.senac.rpgsaude.repository.DesafioRepository;
import com.senac.rpgsaude.repository.RecompensaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecompensaService {

    private final RecompensaRepository recompensaRepository;
    private final DesafioRepository desafioRepository;
    private final ModelMapper modelMapper;

    public RecompensaService(RecompensaRepository recompensaRepository, DesafioRepository desafioRepository, ModelMapper modelMapper) {
        this.recompensaRepository = recompensaRepository;
        this.desafioRepository = desafioRepository;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public List<RecompensaDTOResponse> listarRecompensas() {
        return recompensaRepository.findAll().stream()
                .map(rec -> modelMapper.map(rec, RecompensaDTOResponse.class))
                .collect(Collectors.toList());
    }

    // MÉTODO LISTAR POR ID (FALTANDO ANTES)
    @Transactional(readOnly = true)
    public RecompensaDTOResponse listarPorId(Integer id) {
        Recompensa recompensa = recompensaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recompensa não encontrada com ID: " + id));
        return modelMapper.map(recompensa, RecompensaDTOResponse.class);
    }

    @Transactional
    public RecompensaDTOResponse criarRecompensa(RecompensaDTORequest dto) {
        Recompensa recompensa = new Recompensa();
        updateRecompensaFromDto(recompensa, dto);

        Recompensa saved = recompensaRepository.save(recompensa);
        return modelMapper.map(saved, RecompensaDTOResponse.class);
    }

    // MÉTODO ATUALIZAR (FALTANDO ANTES)
    @Transactional
    public RecompensaDTOResponse atualizarRecompensa(Integer id, RecompensaDTORequest dto) {
        Recompensa recompensa = recompensaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recompensa não encontrada com ID: " + id));

        updateRecompensaFromDto(recompensa, dto);

        Recompensa updated = recompensaRepository.save(recompensa);
        return modelMapper.map(updated, RecompensaDTOResponse.class);
    }

    // MÉTODO DELETAR (FALTANDO ANTES)
    @Transactional
    public void deletarRecompensa(Integer id) {
        if (!recompensaRepository.existsById(id)) {
            throw new EntityNotFoundException("Recompensa não encontrada com ID: " + id);
        }
        recompensaRepository.deleteById(id);
    }

    // MÉTODO AUXILIAR (Já existia, mas foi incluído para compilar)
    private void updateRecompensaFromDto(Recompensa recompensa, RecompensaDTORequest dto) {
        recompensa.setNome(dto.getNome());
        recompensa.setDescricao(dto.getDescricao());
        recompensa.setItem(dto.getItem());
        recompensa.setValor(dto.getValor());

        if (dto.getDesafioId() != null) {
            Desafio desafio = desafioRepository.findById(dto.getDesafioId())
                    .orElseThrow(() -> new EntityNotFoundException("Desafio não encontrado com ID: " + dto.getDesafioId()));
            recompensa.setDesafio(desafio);
        }
    }
}