package com.senac.rpgsaude.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dungeon")
public class Dungeon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dungeon_id")
    private int id;

    @Column(name = "dungeon_nome")
    private String nome;

    @Column(name = "dungeon_status")
    private int status;

    @Column(name = "dungeon_dificuldade")
    private int dificuldade;

    @ManyToOne
    @JoinColumn(name = "avatar_id", referencedColumnName = "avatar_id")
    private Avatar avatar;
    @ManyToOne
    @JoinColumn(name = "desafio_id", referencedColumnName = "desafio_id")
    private Desafio desafio;

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

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }
}