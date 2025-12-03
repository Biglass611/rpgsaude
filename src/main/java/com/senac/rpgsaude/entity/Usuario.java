package com.senac.rpgsaude.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;

    @Column(name = "usuario_email", unique = true)
    private String email;

    @Column(name = "usuario_senha")
    private String senha;

    @Column(name = "usuario_status")
    private Integer status;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="usuario_role",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    // =================================================================================
    // 👇 CONSTRUTORES (NECESSÁRIOS PARA O SERVICE FUNCIONAR)
    // =================================================================================

    // Construtor vazio (Obrigatório para o JPA/Hibernate)
    public Usuario() {
    }

    // Construtor para criar usuário novo no Registro
    public Usuario(String email, String senha, List<Role> roles) {
        this.email = email;
        this.senha = senha;
        this.roles = roles;
        this.status = 1; // Define ativo por padrão ao criar
    }

    // Construtor simples (caso seu service use apenas login e senha inicial)
    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
        this.status = 1;
    }

    // =================================================================================
    // 👇 MÉTODOS DO SPRING SECURITY
    // =================================================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Como sua classe Role já implementa GrantedAuthority, pode retornar direto!
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // =================================================================================
    // 👇 SEUS GETTERS E SETTERS ORIGINAIS
    // =================================================================================

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
