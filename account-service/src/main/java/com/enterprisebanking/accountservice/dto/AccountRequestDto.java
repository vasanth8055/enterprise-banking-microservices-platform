package com.enterprisebanking.accountservice.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public class AccountRequestDto {

    @NotBlank
    private String accountNumber;

    @NotNull
    @Positive
    private Double balance;

    @NotNull
    private Long userId;

    public AccountRequestDto() {
    }

    public AccountRequestDto(
            String accountNumber,
            Double balance,
            Long userId
    ) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}