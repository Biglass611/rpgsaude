package com.senac.rpgsaude.dto.request;

import jakarta.validation.constraints.NotNull;

public class AtributosDTORequest {
    @NotNull
    private Double quantidade;
    @NotNull
    private Integer avatarId; // Chave estrangeira

    // Getters e Setters
    public Double getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
    public Integer getAvatarId() {
        return avatarId;
    }
    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
    }
}