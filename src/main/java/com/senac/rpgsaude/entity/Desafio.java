package com.senac.rpgsaude.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "desafio")
public class Desafio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "desafio_id")
    private int id;

    // CORREÇÃO: MappedBy deve apontar para o nome da propriedade na classe Recompensa (desafio)
    @OneToMany(mappedBy = "desafio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recompensa> recompensas;

    @Column(name = "desafio_nome")
    private String nome;

    @Column(name = "desafio_descricao")
    private String descricao;

    @Column(name = "desafio_tipo")
    private String tipo;

    @Column(name = "chefe_id")
    private int chefeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getChefeId() {
        return chefeId;
    }

    public void setChefeId(int chefeId) {
        this.chefeId = chefeId;
    }

    public List<Recompensa> getRecompensas() {
        return recompensas;
    }

    public void setRecompensas(List<Recompensa> recompensas) {
        this.recompensas = recompensas;
    }
}