package com.senac.rpgsaude.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dungeon")
public class Dungeon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dungeon_id")
    private Integer id;

    @Column(name = "dungeon_nome")
    private String nome;

    @Column(name = "dungeon_dificuldade")
    private Integer dificuldade;

    @Column(name = "dungeon_status")
    private Integer status;

    // Mapeamento para Usuario (conforme seu banco: usuario_id)
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Mapeamento para Desafio (conforme seu banco: desafio_id)
    @ManyToOne
    @JoinColumn(name = "desafio_id", nullable = false)
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Desafio getDesafio() {
        return desafio;
    }

    public void setDesafio(Desafio desafio) {
        this.desafio = desafio;
    }
}