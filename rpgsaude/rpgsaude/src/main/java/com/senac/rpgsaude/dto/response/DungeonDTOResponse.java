package com.senac.rpgsaude.dto.response;

public class DungeonDTOResponse {
    private Integer id;
    private String nome;
    private Integer dificuldade;
    private Integer status;
    private String nomeUsuario; // Para exibir de quem é a dungeon
    private String nomeDesafio; // Para exibir qual é o desafio

    // --- Getters e Setters ---

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeDesafio() {
        return nomeDesafio;
    }

    public void setNomeDesafio(String nomeDesafio) {
        this.nomeDesafio = nomeDesafio;
    }
}