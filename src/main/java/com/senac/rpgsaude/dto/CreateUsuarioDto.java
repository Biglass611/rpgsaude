package com.senac.rpgsaude.dto;

import com.senac.rpgsaude.entity.RoleName;

public record CreateUsuarioDto(
        String email,
        String senha,
        RoleName role

) {

}
