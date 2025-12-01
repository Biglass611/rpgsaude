package com.senac.rpgsaude.dto.response;

public class RankingDTOResponse implements Comparable<RankingDTOResponse> {
    private String nomeAvatar;
    private Integer nivel;
    private Integer moedas;

    public RankingDTOResponse(String nomeAvatar, Integer nivel, Integer moedas) {
        this.nomeAvatar = nomeAvatar;
        this.nivel = nivel;
        this.moedas = moedas;
    }

    // Isso permite ordenar a lista automaticamente (do maior nível para o menor)
    @Override
    public int compareTo(RankingDTOResponse outro) {
        return outro.getNivel().compareTo(this.nivel);
    }

    // --- Getters e Setters ---
    public String getNomeAvatar() { return nomeAvatar; }
    public void setNomeAvatar(String nomeAvatar) { this.nomeAvatar = nomeAvatar; }

    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }

    public Integer getMoedas() { return moedas; }
    public void setMoedas(Integer moedas) { this.moedas = moedas; }
}