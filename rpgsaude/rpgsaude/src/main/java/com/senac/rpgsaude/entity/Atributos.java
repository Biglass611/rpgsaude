package com.senac.rpgsaude.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "atributos")
public class Atributos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atributos_id")
    private Long id;

    @Column(name = "atributos_quantidade")
    private Double quantidade;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }
}