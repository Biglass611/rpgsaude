package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.request.AvatarMoedasDTORequest;
import com.senac.rpgsaude.dto.response.AvatarMoedasDTOResponse;
import com.senac.rpgsaude.entity.AvatarMoedas;
import com.senac.rpgsaude.repository.AvatarMoedasRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AvatarMoedasService {
    private final AvatarMoedasRepository avatarMoedasRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AvatarMoedasService(AvatarMoedasRepository avatarMoedasRepository, ModelMapper modelMapper) {
        this.avatarMoedasRepository = avatarMoedasRepository;
        this.modelMapper = modelMapper;
    }

    public List<AvatarMoedasDTOResponse> listarAvatarMoeda() {
        return avatarMoedasRepository.findAll().stream()
                .map(registro -> modelMapper.map(registro, AvatarMoedasDTOResponse.class))
                .collect(Collectors.toList());
    }

    public AvatarMoedasDTOResponse listarPorId(Integer id) {
        AvatarMoedas avatarMoedas = avatarMoedasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Moedas de Avatar com ID " + id + " não encontrado."));
        return modelMapper.map(avatarMoedas, AvatarMoedasDTOResponse.class);
    }

    public AvatarMoedasDTOResponse criarAvatarMoedas(AvatarMoedasDTORequest avatarMoedasDTORequest) {
        AvatarMoedas avatarMoedas = modelMapper.map(avatarMoedasDTORequest, AvatarMoedas.class);
        AvatarMoedas savedAvatarMoedas = avatarMoedasRepository.save(avatarMoedas);
        return modelMapper.map(savedAvatarMoedas, AvatarMoedasDTOResponse.class);
    }

    public AvatarMoedasDTOResponse atualizarAvatarMoedas(Integer id, AvatarMoedasDTORequest avatarMoedasDTORequest) {
        AvatarMoedas avatarMoedas = avatarMoedasRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Moedas de Avatar com ID " + id + " não encontrado."));

        modelMapper.map(avatarMoedasDTORequest, avatarMoedas);

        AvatarMoedas updatedAvatarMoedas = avatarMoedasRepository.save(avatarMoedas);
        return modelMapper.map(updatedAvatarMoedas, AvatarMoedasDTOResponse.class);
    }

    public void deletarAvatarMoedas(Integer id) {
        avatarMoedasRepository.deleteById(id);
    }
}