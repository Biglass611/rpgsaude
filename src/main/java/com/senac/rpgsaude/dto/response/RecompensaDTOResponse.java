package com.senac.rpgsaude.dto.response;

public class RecompensaDTOResponse {
    private Integer id;
    private String nome;
    private String descricao;
    private String item;
    private Double valor;

    // ... Getters e Setters para todos ...
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public String getItem() { return item; }
    public void setItem(String item) { this.item = item; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
}