package com.senac.rpgsaude.repository;

import com.senac.rpgsaude.entity.TabelaPremio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabelaPremioRepository extends JpaRepository<TabelaPremio, Integer> {

}
