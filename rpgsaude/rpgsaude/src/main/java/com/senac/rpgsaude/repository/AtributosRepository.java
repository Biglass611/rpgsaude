package com.senac.rpgsaude.repository;

import com.senac.rpgsaude.entity.RegistroXp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroXpRepository extends JpaRepository<RegistroXp,Integer>{

}
