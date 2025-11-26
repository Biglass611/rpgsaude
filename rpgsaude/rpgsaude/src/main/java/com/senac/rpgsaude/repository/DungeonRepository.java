package com.senac.rpgsaude.repository;

import com.senac.rpgsaude.entity.Missao;
import com.senac.rpgsaude.entity.Personagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Integer> {

    List<Missao> findByPersonagemAndStatus(Personagem personagem, int status);
}
