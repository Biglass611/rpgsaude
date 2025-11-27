package com.senac.rpgsaude.service;

import com.senac.rpgsaude.dto.request.AtributosDTORequest;
import com.senac.rpgsaude.dto.request.AtributosDTORequest;
import com.senac.rpgsaude.dto.response.AtributosDTOResponse;
import com.senac.rpgsaude.entity.Atributos;
import com.senac.rpgsaude.entity.Atributos;
import com.senac.rpgsaude.repository.AtributosRepository;
import com.senac.rpgsaude.repository.AtributosRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
    public class AtributosService {
    private final AtributosRepository atributosRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AtributosService(AtributosRepository atributosRepository, ModelMapper modelMapper) {
        this.atributosRepository = atributosRepository;
        this.modelMapper = modelMapper;
    }

    public List<AtributosDTOResponse> listarAtributoss() {
        return atributosRepository.findAll().stream()
                .map(atributos -> modelMapper.map(atributos, AtributosDTOResponse.class))
                .collect(Collectors.toList());
    }

    public AtributosDTOResponse listarPorId(Integer id) {
        Atributos atributos = atributosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atributos com ID " + id + " não encontrado."));
        return modelMapper.map(atributos, AtributosDTOResponse.class);
    }

    public AtributosDTOResponse criarAtributos(AtributosDTORequest atributosDTORequest) {
        Atributos atributos = modelMapper.map(atributosDTORequest, Atributos.class);
        Atributos savedAtributos = atributosRepository.save(atributos);
        return modelMapper.map(savedAtributos, AtributosDTOResponse.class);
    }

    public AtributosDTOResponse atualizarAtributos(Integer id, AtributosDTORequest atributosDTORequest) {
        Atributos atributos = atributosRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atributos com ID " + id + " não encontrado."));

        modelMapper.map(atributosDTORequest, atributos);

        Atributos updatedAtributos = atributosRepository.save(atributos);
        return modelMapper.map(updatedAtributos, AtributosDTOResponse.class);
    }

    public void deletarAtributos(Integer id) {
        atributosRepository.deleteById(id);
    }
}