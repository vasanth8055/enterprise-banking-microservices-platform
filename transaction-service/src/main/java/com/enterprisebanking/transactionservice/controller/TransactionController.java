package com.enterprisebanking.transactionservice.controller;

import com.enterprisebanking.transactionservice.dto.TransactionRequestDto;
import com.enterprisebanking.transactionservice.dto.TransactionResponseDto;
import com.enterprisebanking.transactionservice.service.TransactionService;
import com.enterprisebanking.transactionservice.dto.TransferRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Transaction Service", description = "Bank Transaction APIs")
@RestController
@RequestMapping("/api/transactions")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/deposit")
    public TransactionResponseDto deposit(
            @RequestBody TransactionRequestDto requestDto
    ) {

        return transactionService.deposit(requestDto);
    }
    @PostMapping("/withdraw")
    public TransactionResponseDto withdraw(
            @RequestBody TransactionRequestDto requestDto
    ) {

        return transactionService.withdraw(requestDto);
    }
    @PostMapping("/transfer")
    public String transfer(
            @RequestBody TransferRequestDto requestDto
    ) {

        return transactionService.transfer(requestDto);
    }
    @Operation(summary = "Get Transaction History")
    @GetMapping("/account/{accountId}")
    public List<TransactionResponseDto>
    getTransactionsByAccountId(
            @PathVariable Long accountId
    ) {

        return transactionService.getTransactionsByAccountId(accountId);
    }
}