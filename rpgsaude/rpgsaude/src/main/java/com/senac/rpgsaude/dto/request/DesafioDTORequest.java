package com.senac.rpgsaude.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DesafioDTORequest {

    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;

    @NotBlank(message = "A descrição não pode estar vazia")
    private String descricao;

    @NotBlank(message = "O tipo não pode estar vazio")
    private String tipo;

    private Integer chefeId;

    @NotNull(message = "O ID da recompensa é obrigatório")
    private Integer recompensaId;

    // --- Getters e Setters ---

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getChefeId() {
        return chefeId;
    }

    public void setChefeId(Integer chefeId) {
        this.chefeId = chefeId;
    }

    public Integer getRecompensaId() {
        return recompensaId;
    }

    public void setRecompensaId(Integer recompensaId) {
        this.recompensaId = recompensaId;
    }
}