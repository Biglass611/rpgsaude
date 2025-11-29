package com.senac.rpgsaude.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DungeonDTORequest {

    @NotBlank(message = "O nome da dungeon é obrigatório")
    private String nome;

    @NotNull(message = "A dificuldade é obrigatória")
    private Integer dificuldade;

    @NotNull(message = "O status é obrigatório")
    private Integer status;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Integer usuarioId;

    @NotNull(message = "O ID do desafio é obrigatório")
    private Integer desafioId;

    // --- Getters e Setters ---

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Integer dificuldade) {
        this.dificuldade = dificuldade;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getDesafioId() {
        return desafioId;
    }

    public void setDesafioId(Integer desafioId) {
        this.desafioId = desafioId;
    }
}