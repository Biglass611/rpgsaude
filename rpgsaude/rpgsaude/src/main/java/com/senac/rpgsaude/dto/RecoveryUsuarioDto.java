package com.senac.rpgsaude.dto;

import com.senac.rpgsaude.entity.Role;

import java.util.List;

public record RecoveryUsuarioDto(Long id,
                                 String email,
                                 List<Role> roles) {
}
