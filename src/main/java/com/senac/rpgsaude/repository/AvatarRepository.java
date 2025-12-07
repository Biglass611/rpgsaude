package com.senac.rpgsaude.repository;

import com.senac.rpgsaude.entity.Avatar;
import com.senac.rpgsaude.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    // Esse método busca se existe UM avatar para ESSE usuário específico
    Optional<Avatar> findByUsuario(Usuario usuario);

    List<Avatar> findByNomeContainingIgnoreCase(String nome);
}