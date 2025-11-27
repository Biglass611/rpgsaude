package com.senac.rpgsaude.repository;

import com.senac.rpgsaude.entity.Desafio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesafioRepository extends JpaRepository<Desafio, Integer> {

}
