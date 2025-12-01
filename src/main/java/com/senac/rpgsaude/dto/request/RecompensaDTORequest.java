package com.senac.rpgsaude.dto.request;

public class RecompensaDTORequest {

    private String nome;
    private String descricao;
    private String item;
    private Double valor;
    private Integer desafioId; // Obrigatório para vincular ao Desafio

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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getDesafioId() {
        return desafioId;
    }

    public void setDesafioId(Integer desafioId) {
        this.desafioId = desafioId;
    }
}