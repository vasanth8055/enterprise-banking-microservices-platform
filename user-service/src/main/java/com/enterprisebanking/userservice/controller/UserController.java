package com.enterprisebanking.userservice.controller;

import com.enterprisebanking.userservice.dto.UserRequestDto;
import com.enterprisebanking.userservice.dto.UserResponseDto;
import com.enterprisebanking.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.enterprisebanking.userservice.dto.LoginRequestDto;
import com.enterprisebanking.userservice.dto.LoginResponseDto;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserResponseDto registerUser(
            @Valid @RequestBody UserRequestDto requestDto) {

        return userService.saveUser(requestDto);
    }

    @PostMapping("/login")
    public LoginResponseDto loginUser(
            @RequestBody LoginRequestDto requestDto) {

        return userService.loginUser(requestDto);
    }

    @GetMapping("/{id}")
    public UserResponseDto getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    @GetMapping
    public List<UserResponseDto> getAllUsers() {

        return userService.getAllUsers();
    }

    @PutMapping("/{id}")
    public UserResponseDto updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDto requestDto) {

        return userService.updateUser(id, requestDto);
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return "User deleted successfully";
    }
}