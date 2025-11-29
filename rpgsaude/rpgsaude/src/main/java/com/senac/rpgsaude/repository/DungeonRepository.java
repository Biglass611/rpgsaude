package com.senac.rpgsaude.repository;

import com.senac.rpgsaude.entity.Dungeon;
import com.senac.rpgsaude.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DungeonRepository extends JpaRepository<Dungeon, Integer> {

    List<Dungeon> findByUsuarioAndStatus(Usuario usuario, int status);
}