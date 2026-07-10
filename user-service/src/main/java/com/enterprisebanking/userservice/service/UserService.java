package com.enterprisebanking.userservice.service;

import com.enterprisebanking.userservice.dto.UserRequestDto;
import com.enterprisebanking.userservice.dto.UserResponseDto;
import com.enterprisebanking.userservice.entity.User;
import com.enterprisebanking.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.enterprisebanking.userservice.exception.UserNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.enterprisebanking.userservice.dto.LoginRequestDto;
import com.enterprisebanking.userservice.dto.LoginResponseDto;
import com.enterprisebanking.userservice.jwt.JwtUtil;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public UserResponseDto saveUser(UserRequestDto requestDto) {

        User user = new User();

        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setPassword(
                passwordEncoder.encode(requestDto.getPassword())
        );
        User savedUser = userRepository.save(user);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );

    }

    public LoginResponseDto loginUser(LoginRequestDto requestDto) {

        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("Invalid email")
                );

        boolean passwordMatches = passwordEncoder.matches(
                requestDto.getPassword(),
                user.getPassword()
        );

        if (!passwordMatches) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new LoginResponseDto(token);
    }

    public UserResponseDto getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("User not found with ID: " + id)
                );

        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }


    public List<UserResponseDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> new UserResponseDto(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .collect(Collectors.toList());
    }

    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {

        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return null;
        }

        existingUser.setName(requestDto.getName());
        existingUser.setEmail(requestDto.getEmail());
        existingUser.setPassword(requestDto.getPassword());

        User updatedUser = userRepository.save(existingUser);

        return new UserResponseDto(
                updatedUser.getId(),
                updatedUser.getName(),
                updatedUser.getEmail()
        );
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}