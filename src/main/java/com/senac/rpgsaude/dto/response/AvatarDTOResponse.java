package com.senac.rpgsaude.dto.response;

public class AvatarDTOResponse {

    private Long id;
    private String nome;
    private Integer nivel;
    private Integer moedas;
    private String atributos;
    private String nomeUsuario;

    // --- NOVO CAMPO NECESSÁRIO ---
    private Integer usuarioId;

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

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    // --- GETTER E SETTER DO NOVO CAMPO ---
    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}