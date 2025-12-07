package com.senac.rpgsaude.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class AvatarDTORequest {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "Atributos são obrigatórios")
    private String atributos; // Mudado para String para casar com a Entity

    @NotNull(message = "Moedas são obrigatórias")
    @PositiveOrZero
    private Integer moedas;   // Mudado para Integer

    @NotNull(message = "O nível é obrigatório")
    @PositiveOrZero
    private Integer nivel;    // Mudado para Integer

    @NotNull(message = "O ID do usuário é obrigatório")
    private Integer usuarioId;

    // --- Getters e Setters ---

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getAtributos() { return atributos; }
    public void setAtributos(String atributos) { this.atributos = atributos; }

    public Integer getMoedas() { return moedas; }
    public void setMoedas(Integer moedas) { this.moedas = moedas; }

    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
}