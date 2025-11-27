package com.senac.rpgsaude.dto.response;

public class DungeonDTOResponse {
    private int id;
    private int dificuldade;
    private int status;
    private String nomeAvatar;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    public String getNomeAvatar() {
        return nomeAvatar;
    }
    public void setNomeAvatar(String nomeAvatar) {
        this.nomeAvatar = nomeAvatar;
    }
}