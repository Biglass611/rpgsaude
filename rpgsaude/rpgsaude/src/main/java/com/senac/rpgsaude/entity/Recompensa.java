package com.senac.rpgsaude.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "recompensa")
public class Recompensa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recompensa_id") // ID da tabela recompensa
    private int id; // Usar id para simplificar

    @Column(name = "recompensa_preco")
    private double preco;

    @Column(name = "recompensa_nome")
    private String nome;

    @Column(name="recompensa_valor")
    private Double valor;

    // Relacionamento com Desafio (lado proprietário da FK 'desafio_id')
    @ManyToOne
    @JoinColumn(name = "desafio_id")
    private Desafio desafio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Desafio getDesafio() {
        return desafio;
    }

    public void setDesafio(Desafio desafio) {
        this.desafio = desafio;
    }
}