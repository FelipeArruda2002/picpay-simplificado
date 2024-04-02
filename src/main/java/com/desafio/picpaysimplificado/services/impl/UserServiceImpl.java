package com.desafio.picpaysimplificado.services.impl;

import com.desafio.picpaysimplificado.domain.models.User;
import com.desafio.picpaysimplificado.repositories.UserRepository;
import com.desafio.picpaysimplificado.rest.api.user.dtos.UserDTO;
import com.desafio.picpaysimplificado.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void validateUser(UserDTO userDTO) throws Exception {
        if (this.repository.existsByEmail(userDTO.email())) {
            throw new Exception("Já existe um usuário cadastrado com esse email.");
        }

        if (this.repository.existsByDocument(userDTO.document())) {
            throw new Exception("Já existe um usuário cadastrado com esse documento.");
        }
    }

    @Override
    @Transactional
    public UserDTO registerUser(UserDTO userDTO) throws Exception{
        validateUser(userDTO);
        User user = convertToUser(userDTO);
        saveUser(user);
        return userDTO;
    }

    @Override
    public User userById(Long id) throws Exception {
        return this.repository.findById(id)
                .orElseThrow(() -> new Exception("Usuário não encontrado."));
    }

    @Override
    public void saveUser(User user) {
        this.repository.save(user);
    }

    private User convertToUser(UserDTO userDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        return user;
    }
}
