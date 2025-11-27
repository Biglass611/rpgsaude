package com.senac.rpgsaude.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate; // Mantido, mas não usado nos campos internos. Pode ser removido.

public class DungeonDTORequest {

    @NotNull(message = "O ID do Avatar é obrigatório.")
    private Integer avatarId;

    @Min(value = 1, message = "A dificuldade deve ser maior ou igual a 1.")
    private int dificuldade;


    private int status;



    public Integer getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Integer avatarId) {
        this.avatarId = avatarId;
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
}