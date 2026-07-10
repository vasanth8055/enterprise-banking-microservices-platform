package com.enterprisebanking.accountservice.controller;

import com.enterprisebanking.accountservice.dto.AccountRequestDto;
import com.enterprisebanking.accountservice.dto.AccountResponseDto;
import com.enterprisebanking.accountservice.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public AccountResponseDto createAccount(
            @Valid
            @RequestBody AccountRequestDto requestDto
    ) {

        return accountService.createAccount(requestDto);
    }

    @GetMapping("/user/{userId}")
    public List<AccountResponseDto> getAccountsByUserId(
            @PathVariable Long userId
    ) {

        return accountService.getAccountsByUserId(userId);
    }
    @GetMapping("/{id}")
    public AccountResponseDto getAccountById(
            @PathVariable Long id
    ) {

        return accountService.getAccountById(id);
    }
    @PutMapping("/{id}/balance")
    public AccountResponseDto updateBalance(
            @PathVariable Long id,
            @RequestParam Double balance
    ) {

        return accountService.updateBalance(id, balance);
    }
    @GetMapping
    public List<AccountResponseDto> getAllAccounts() {
        return accountService.getAllAccounts();
    }
}