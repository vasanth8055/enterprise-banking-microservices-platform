package com.enterprisebanking.accountservice.dto;

public class AccountResponseDto {

    private Long id;

    private String accountNumber;

    private Double balance;

    private Long userId;

    public AccountResponseDto() {
    }

    public AccountResponseDto(
            Long id,
            String accountNumber,
            Double balance,
            Long userId
    ) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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