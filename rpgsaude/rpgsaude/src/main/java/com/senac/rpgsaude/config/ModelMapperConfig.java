package com.senac.rpgsaude.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.modelmapper.PropertyMap;
import com.senac.rpgsaude.entity.Avatar;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Mapeamento explícito para a conversão de Avatar para AvatarDTOResponse
        modelMapper.addMappings(new PropertyMap<Avatar, com.senac.rpgsaude.dto.response.AvatarDTOResponse>() {
            @Override
            protected void configure() {
                // Mapeia o nome do usuário aninhado na entidade Avatar para o DTO
                map().setNomeUsuario(source.getUsuario().getEmail());
            }
        });

        // ... (outros mapeamentos se necessário)

        return modelMapper;
    }
}