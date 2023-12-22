package com.jawbr.testepratico.controller;

import com.jawbr.testepratico.dto.request.UserRequestDTO;
import com.jawbr.testepratico.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        userService.createUser(userRequestDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
