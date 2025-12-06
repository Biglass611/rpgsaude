package com.senac.rpgsaude.dto.request;

public class LoginDTORequest {
    private String email;
    private String senha;

    // Getters
    public String getEmail() { return email; }
    public String getSenha() { return senha; }

    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setSenha(String senha) { this.senha = senha; }
}