package com.enterprisebanking.transactionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.enterprisebanking.transactionservice.dto.AccountResponseDto;

@FeignClient(
        name = "account-service",
        url = "http://localhost:8082"
)
public interface AccountClient {

    @GetMapping("/api/accounts/user/{userId}")
    Object getAccountsByUserId(
            @PathVariable Long userId
    );
    @GetMapping("/api/accounts/{id}")
    AccountResponseDto getAccountById(
            @PathVariable Long id
    );

    @PutMapping("/api/accounts/{id}/balance")
    Object updateBalance(
            @PathVariable Long id,
            @RequestParam Double balance
    );
}