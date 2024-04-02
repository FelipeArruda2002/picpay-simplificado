package com.desafio.picpaysimplificado.services;

import com.desafio.picpaysimplificado.domain.models.User;
import com.desafio.picpaysimplificado.rest.api.user.dtos.UserDTO;

public interface UserService {

    void validateUser(UserDTO userDTO) throws Exception;

    UserDTO registerUser(UserDTO userDTO) throws Exception;

    User userById(Long id) throws Exception;

    void saveUser(User user);
}
