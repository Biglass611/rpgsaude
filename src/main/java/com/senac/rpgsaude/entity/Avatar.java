package com.senac.rpgsaude.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "avatar") // Nome correto da tabela
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "avatar_id") // Nome correto da PK
    private Long id;

    @Column(name = "avatar_nome")
    private String nome;

    @Column(name = "avatar_nivel")
    private Integer nivel;

    @Column(name = "avatar_moedas")
    private Integer moedas;

    @Column(name = "avatar_atributos")
    private String atributos; // No banco é VARCHAR

    @OneToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "usuario_id", nullable = false)
    private Usuario usuario;

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Integer getMoedas() {
        return moedas;
    }

    public void setMoedas(Integer moedas) {
        this.moedas = moedas;
    }

    public String getAtributos() {
        return atributos;
    }

    public void setAtributos(String atributos) {
        this.atributos = atributos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}