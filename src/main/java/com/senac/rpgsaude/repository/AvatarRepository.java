package com.senac.rpgsaude.repository;

import com.senac.rpgsaude.entity.Avatar;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    @Query("SELECT a FROM Avatar a WHERE a.id IN (SELECT MIN(a2.id) FROM Avatar a2 GROUP BY a2.usuario.id)")
    @Lock(LockModeType.NONE)
    List<Avatar> listarTodosAvatares();

    @Override
    @Lock(LockModeType.NONE)
    Optional<Avatar> findById(Long id);
}