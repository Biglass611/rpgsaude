package com.senac.rpgsaude.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "registromoeda")
public class AvatarMoedas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registromoeda_id")
    private Long id;

    @Column(name = "registromoeda_quantidade")
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