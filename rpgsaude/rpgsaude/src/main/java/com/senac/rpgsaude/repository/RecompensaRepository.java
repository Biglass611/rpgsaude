package com.senac.rpgsaude.repository;

import com.senac.rpgsaude.entity.Recompensa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecompensaRepository extends JpaRepository<Recompensa, Integer> {
}
