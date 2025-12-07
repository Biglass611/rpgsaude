package com.senac.rpgsaude.dto.request;

public class UsuarioDTORequest {

    // REMOVIDO: nome e telefone

    private String email;
    private String senha;
    private int status;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}
