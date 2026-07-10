package com.enterprisebanking.transactionservice.dto;

import java.time.LocalDateTime;

public class TransactionResponseDto {

    private Long id;

    private Long accountId;

    private Double amount;

    private String type;

    private LocalDateTime timestamp;

    public TransactionResponseDto() {
    }

    public TransactionResponseDto(
            Long id,
            Long accountId,
            Double amount,
            String type,
            LocalDateTime timestamp
    ) {
        this.id = id;
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}