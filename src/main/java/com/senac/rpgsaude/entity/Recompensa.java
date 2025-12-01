package com.senac.rpgsaude.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recompensa")
public class Recompensa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recompensa_id")
    private Integer id;

    @Column(name = "recompensa_nome")
    private String nome;

    @Column(name = "recompensa_descricao")
    private String descricao;

    @Column(name = "recompensa_item")
    private String item;

    @Column(name = "recompensa_valor")
    private Double valor;

    @ManyToOne
    @JoinColumn(name = "desafio_desafio_id", referencedColumnName = "desafio_id", nullable = false)
    private Desafio desafio;

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

    public Desafio getDesafio() {
        return desafio;
    }

    public void setDesafio(Desafio desafio) {
        this.desafio = desafio;
    }
}