package com.desafio.picpaysimplificado.rest.api.user.dtos;

import com.desafio.picpaysimplificado.domain.enums.UserType;

import java.math.BigDecimal;

public record UserDTO(String name,
                      String document,
                      String email,
                      String password,
                      UserType userType,
                      BigDecimal balance) {
}
