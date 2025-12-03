package com.senac.rpgsaude.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario implements UserDetails { // ✅ Adicionado implements UserDetails

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

    // Relacionamento com Roles (Perfis)
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(name="usuario_role",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private List<Role> roles;

    // =================================================================================
    // 👇 MÉTODOS OBRIGATÓRIOS DO SPRING SECURITY (UserDetails)
    // =================================================================================

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna a lista de perfis (roles) do banco de dados.
        // O Spring precisa que a sua classe "Role" implemente GrantedAuthority (veja nota abaixo)
        return (Collection<? extends GrantedAuthority>) this.roles;
    }

    @Override
    public String getPassword() {
        return this.senha; // ✅ Retorna sua coluna 'usuario_senha'
    }

    @Override
    public String getUsername() {
        return this.email; // ✅ Retorna sua coluna 'usuario_email' como login
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
        // Se você quiser bloquear usuários com status 0, mude para:
        // return this.status != null && this.status == 1;
        return true; // Por enquanto, deixei todos ativos
    }

    // =================================================================================
    // 👇 SEUS GETTERS E SETTERS ORIGINAIS (Mantidos intactos)
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