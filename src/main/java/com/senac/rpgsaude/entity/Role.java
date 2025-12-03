package com.senac.rpgsaude.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority; // ⚠️ Import obrigatório

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority { // ✅ Agora é uma autoridade oficial

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "role_name")
    private RoleName name;

    // =================================================================
    // 👇 MÉTODO OBRIGATÓRIO (GrantedAuthority)
    // =================================================================

    @Override
    public String getAuthority() {
        // Converte o Enum para String.
        // Se o seu Enum for ROLE_ADMIN, isso retorna "ROLE_ADMIN".
        return name.toString();
    }

    // =================================================================
    // Getters e Setters
    // =================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getName() {
        return name;
    }

    public void setName(RoleName name) {
        this.name = name;
    }
}