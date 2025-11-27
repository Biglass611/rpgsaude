package com.senac.rpgsaude.dto.request;

import jakarta.validation.constraints.NotNull;

public class DesafioDTORequest {
    @NotNull
    private Integer desafioId; // Chave estrangeira


    // Getters e Setters
    public Integer getDesafioId() {
        return desafioId;
    }
    public void setDesafioId(Integer desafioId) {
        this.desafioId = desafioId;
    }

    public Integer getRecompensaId() {
        return 0;
    }
}