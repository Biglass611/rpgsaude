package com.senac.rpgsaude.dto.response;

import java.time.LocalDate;

public class UsuarioDTOResponse {
    private int id;
    private String email;
    private int status;
    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
}