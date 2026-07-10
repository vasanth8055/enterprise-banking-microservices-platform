package com.enterprisebanking.accountservice.service;

import com.enterprisebanking.accountservice.dto.AccountRequestDto;
import com.enterprisebanking.accountservice.dto.AccountResponseDto;
import com.enterprisebanking.accountservice.entity.Account;
import com.enterprisebanking.accountservice.repository.AccountRepository;
import com.enterprisebanking.accountservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import com.enterprisebanking.accountservice.client.UserClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class AccountService {
    private static final Logger logger =
            LoggerFactory.getLogger(AccountService.class);
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserClient userClient;

    public AccountResponseDto createAccount(
            AccountRequestDto requestDto
    ) {
        logger.info("Creating account for user {}", requestDto.getUserId());
        try {

            Object user = userClient.getUserById(
                    requestDto.getUserId()
            );

            if (user == null) {
                throw new ResourceNotFoundException("User not found");
            }

        } catch (Exception e) {
            logger.error("Error validating user {}", requestDto.getUserId(), e);

            throw new RuntimeException(
                    "Error validating user: " + e.getMessage()
            );
        }
        Account account = new Account();

        account.setAccountNumber(
                requestDto.getAccountNumber()
        );

        account.setBalance(
                requestDto.getBalance()
        );

        account.setUserId(
                requestDto.getUserId()
        );
        logger.info("Saving account {}", requestDto.getAccountNumber());

        Account savedAccount =
                accountRepository.save(account);
        logger.info("Account {} created successfully", savedAccount.getId());

        return new AccountResponseDto(
                savedAccount.getId(),
                savedAccount.getAccountNumber(),
                savedAccount.getBalance(),
                savedAccount.getUserId()
        );
    }
    public AccountResponseDto updateBalance(
            Long id,
            Double balance
    ) {
        logger.info("Updating balance for account {}", id);
        Account account =
                accountRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Account not found")
                        );

        account.setBalance(balance);

        Account updatedAccount =
                accountRepository.save(account);
        logger.info("Balance updated successfully for account {}", id);
        return new AccountResponseDto(
                updatedAccount.getId(),
                updatedAccount.getAccountNumber(),
                updatedAccount.getBalance(),
                updatedAccount.getUserId()
        );
    }
    public AccountResponseDto getAccountById(
            Long id
    ) {
        logger.info("Fetching account {}", id);
        Account account =
                accountRepository.findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Account not found")
                        );

        return new AccountResponseDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getUserId()
        );
    }


    public List<AccountResponseDto> getAccountsByUserId(
            Long userId
    ) {
        logger.info("Fetching accounts for user {}", userId);
        return accountRepository.findByUserId(userId)
                .stream()
                .map(account -> new AccountResponseDto(
                        account.getId(),
                        account.getAccountNumber(),
                        account.getBalance(),
                        account.getUserId()
                ))
                .collect(Collectors.toList());
    }
    public List<AccountResponseDto> getAllAccounts() {
        logger.info("Fetching all accounts");
        return accountRepository.findAll()
                .stream()
                .map(this::mapToResponse)   // ❌ This method doesn't exist
                .toList();
    }
    private AccountResponseDto mapToResponse(Account account) {

        return new AccountResponseDto(
                account.getId(),
                account.getAccountNumber(),
                account.getBalance(),
                account.getUserId()
        );
    }
}