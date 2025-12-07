package com.senac.rpgsaude.dto.response;

import com.senac.rpgsaude.entity.Avatar;

public class AvatarDTOResponse {

    // Se o seu Avatar usa Long, mantenha Long aqui.
    // Se der erro aqui também, mude para Integer.
    private Long id;

    private String nome;
    private Integer nivel;
    private Integer moedas;
    private String atributos;

    // CORREÇÃO AQUI: Mudado de Long para Integer para bater com sua Entidade Usuario
    private Integer usuarioId;

    public AvatarDTOResponse() {}

    public AvatarDTOResponse(Avatar avatar) {
        this.id = avatar.getId();
        this.nome = avatar.getNome();
        this.nivel = avatar.getNivel();
        this.moedas = avatar.getMoedas();
        this.atributos = avatar.getAtributos();

        if (avatar.getUsuario() != null) {
            // Agora o Java vai aceitar pq Integer casa com Integer
            this.usuarioId = avatar.getUsuario().getId();
        }
    }

    // ================= GETTERS E SETTERS =================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public Integer getMoedas() { return moedas; }
    public void setMoedas(Integer moedas) { this.moedas = moedas; }

    public String getAtributos() { return atributos; }
    public void setAtributos(String atributos) { this.atributos = atributos; }

    // GETTER E SETTER CORRIGIDOS PARA INTEGER
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
}