package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.request.RecompensaDTORequest;
import com.senac.rpgsaude.dto.response.RecompensaDTOResponse;
import com.senac.rpgsaude.entity.Recompensa;
import com.senac.rpgsaude.repository.RecompensaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecompensaService {
    private final RecompensaRepository recompensaRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RecompensaService(RecompensaRepository recompensaRepository, ModelMapper modelMapper) {
        this.recompensaRepository = recompensaRepository;
        this.modelMapper = modelMapper;
    }

    public List<RecompensaDTOResponse> listarRecompensas() {
        return recompensaRepository.findAll().stream()
                .map(recompensa -> modelMapper.map(recompensa, RecompensaDTOResponse.class))
                .collect(Collectors.toList());
    }

    public RecompensaDTOResponse listarPorId(Integer id) {
        Recompensa recompensa = recompensaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recompensa com ID " + id + " não encontrado."));
        return modelMapper.map(recompensa, RecompensaDTOResponse.class);
    }

    public RecompensaDTOResponse criarRecompensa(RecompensaDTORequest recompensaDTORequest) {
        Recompensa recompensa = modelMapper.map(recompensaDTORequest, Recompensa.class);
        Recompensa savedRecompensa = recompensaRepository.save(recompensa);
        return modelMapper.map(savedRecompensa, RecompensaDTOResponse.class);
    }

    public RecompensaDTOResponse atualizarRecompensa(Integer id, RecompensaDTORequest recompensaDTORequest) {
        Recompensa recompensa = recompensaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Recompensa com ID " + id + " não encontrado."));

        modelMapper.map(recompensaDTORequest, recompensa);
        Recompensa updatedRecompensa = recompensaRepository.save(recompensa);
        return modelMapper.map(updatedRecompensa, RecompensaDTOResponse.class);
    }

    public void deletarRecompensa(Integer id) {
        recompensaRepository.deleteById(id);
    }
}