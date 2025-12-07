package com.senac.rpgsaude.repository;

import com.senac.rpgsaude.entity.Role;
import com.senac.rpgsaude.entity.RoleName; // Certifique-se de importar seu Enum
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}