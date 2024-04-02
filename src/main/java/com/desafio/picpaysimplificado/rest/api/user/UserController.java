package com.desafio.picpaysimplificado.rest.api.user;

import com.desafio.picpaysimplificado.rest.api.user.dtos.UserDTO;
import com.desafio.picpaysimplificado.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDTO registerUser(@RequestBody UserDTO userDTO) throws Exception {
        return this.userService.registerUser(userDTO);
    }
}
