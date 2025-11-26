package com.senac.rpgsaude.repository;

import com.senac.rpgsaude.entity.Premio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PremioRepository extends JpaRepository<Premio, Integer> {
}
