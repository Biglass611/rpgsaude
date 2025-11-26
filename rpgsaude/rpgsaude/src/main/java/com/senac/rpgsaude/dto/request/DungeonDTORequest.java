package com.senac.rpgsaude.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class DungeonDTORequest {
    @NotBlank
    private String descricao;
    @NotNull
    private int tipo;
    @NotNull
    private int dificuldade;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
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

    @NotNull
    private int status;
    @NotNull
    private Integer usuarioId;
    @NotNull
    private Integer desafioId;
}