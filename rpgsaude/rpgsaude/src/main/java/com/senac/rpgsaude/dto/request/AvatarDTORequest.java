
package com.senac.rpgsaude.dto.request;
import jakarta.validation.constraints.NotNull;
public class AvatarDTORequest {
    @NotNull
    private Double atributos1;
    @NotNull
    private Double moedas;
    @NotNull
    private Double nivel;
    @NotNull
    private Integer usuarioId;



    public Double getMoedas() {
        return moedas;
    }

    public void setMoedas(Double moedas) {
        this.moedas = moedas;
    }

    public Double getAtributos1() {
        return atributos1;
    }

    public void setAtributos1(Double atributos1) {
        this.atributos1 = atributos1;
    }

    public Double getNivel() {
        return nivel;
    }

    public void setNivel(Double nivel) {
        this.nivel = nivel;
    }




    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
}