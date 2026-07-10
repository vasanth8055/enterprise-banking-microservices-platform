package com.enterprisebanking.transactionservice.service;

import com.enterprisebanking.transactionservice.client.AccountClient;
import com.enterprisebanking.transactionservice.dto.TransactionRequestDto;
import com.enterprisebanking.transactionservice.dto.TransactionResponseDto;
import com.enterprisebanking.transactionservice.entity.Transaction;
import com.enterprisebanking.transactionservice.repository.TransactionRepository;
import com.enterprisebanking.transactionservice.dto.AccountResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import com.enterprisebanking.transactionservice.kafka.TransactionProducer;
import com.enterprisebanking.transactionservice.dto.TransferRequestDto;
import com.enterprisebanking.common.dto.TransactionEvent;
@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountClient accountClient;
    @Autowired
    private TransactionProducer transactionProducer;

    public TransactionResponseDto deposit(
            TransactionRequestDto requestDto
    ) {

        AccountResponseDto account =
                accountClient.getAccountById(
                        requestDto.getAccountId()
                );

        Double currentBalance =
                account.getBalance();

        Double newBalance =
                currentBalance + requestDto.getAmount();

        accountClient.updateBalance(
                requestDto.getAccountId(),
                newBalance
        );

        Transaction transaction = new Transaction();

        transaction.setAccountId(
                requestDto.getAccountId()
        );

        transaction.setAmount(
                requestDto.getAmount()
        );

        transaction.setType("DEPOSIT");

        transaction.setTimestamp(
                LocalDateTime.now()
        );

        Transaction savedTransaction =
                transactionRepository.save(transaction);
        TransactionEvent event = new TransactionEvent();
        event.setTransactionId(savedTransaction.getId());
        event.setFromAccountId(requestDto.getAccountId());
        event.setToAccountId(null);
        event.setAmount(requestDto.getAmount());
        event.setTransactionType("DEPOSIT");
        event.setStatus("SUCCESS");
        event.setAvailableBalance(newBalance);
        transactionProducer.sendTransactionEvent(event);

        return new TransactionResponseDto(
                savedTransaction.getId(),
                savedTransaction.getAccountId(),
                savedTransaction.getAmount(),
                savedTransaction.getType(),
                savedTransaction.getTimestamp()
        );
    }
    public TransactionResponseDto withdraw(
            TransactionRequestDto requestDto
    ) {

        AccountResponseDto account =
                accountClient.getAccountById(
                        requestDto.getAccountId()
                );

        Double currentBalance =
                account.getBalance();

        if (currentBalance < requestDto.getAmount()) {

            throw new RuntimeException(
                    "Insufficient balance"
            );
        }

        Double newBalance =
                currentBalance - requestDto.getAmount();

        accountClient.updateBalance(
                requestDto.getAccountId(),
                newBalance
        );

        Transaction transaction = new Transaction();

        transaction.setAccountId(
                requestDto.getAccountId()
        );

        transaction.setAmount(
                requestDto.getAmount()
        );

        transaction.setType("WITHDRAW");

        transaction.setTimestamp(
                LocalDateTime.now()
        );

        Transaction savedTransaction =
                transactionRepository.save(transaction);
        TransactionEvent event = new TransactionEvent();
        event.setTransactionId(savedTransaction.getId());
        event.setFromAccountId(requestDto.getAccountId());
        event.setToAccountId(null);
        event.setAmount(requestDto.getAmount());
        event.setTransactionType("WITHDRAW");
        event.setStatus("SUCCESS");
        event.setAvailableBalance(newBalance);
        transactionProducer.sendTransactionEvent(event);

        return new TransactionResponseDto(
                savedTransaction.getId(),
                savedTransaction.getAccountId(),
                savedTransaction.getAmount(),
                savedTransaction.getType(),
                savedTransaction.getTimestamp()
        );
    }
    public String transfer(
            TransferRequestDto requestDto
    ) {

        AccountResponseDto fromAccount =
                accountClient.getAccountById(
                        requestDto.getFromAccountId()
                );

        AccountResponseDto toAccount =
                accountClient.getAccountById(
                        requestDto.getToAccountId()
                );

        if (fromAccount.getBalance()
                < requestDto.getAmount()) {

            throw new RuntimeException(
                    "Insufficient balance"
            );
        }

        Double senderNewBalance =
                fromAccount.getBalance()
                        - requestDto.getAmount();

        Double receiverNewBalance =
                toAccount.getBalance()
                        + requestDto.getAmount();

        accountClient.updateBalance(
                fromAccount.getId(),
                senderNewBalance
        );

        accountClient.updateBalance(
                toAccount.getId(),
                receiverNewBalance
        );

        Transaction transaction =
                new Transaction();

        transaction.setAccountId(
                fromAccount.getId()
        );

        transaction.setAmount(
                requestDto.getAmount()
        );

        transaction.setType("TRANSFER");

        transaction.setTimestamp(
                LocalDateTime.now()
        );

        Transaction savedTransaction =
                transactionRepository.save(transaction);
        TransactionEvent event = new TransactionEvent();
        event.setTransactionId(savedTransaction.getId());
        event.setFromAccountId(requestDto.getFromAccountId());
        event.setToAccountId(requestDto.getToAccountId());
        event.setAmount(requestDto.getAmount());
        event.setTransactionType("TRANSFER");
        event.setStatus("SUCCESS");
        event.setAvailableBalance(senderNewBalance);
        transactionProducer.sendTransactionEvent(event);

        return "Transfer successful";
    }
    public List<TransactionResponseDto>
    getTransactionsByAccountId(Long accountId) {

        List<Transaction> transactions =
                transactionRepository
                        .findByAccountId(accountId);

        return transactions.stream()
                .map(transaction ->
                        new TransactionResponseDto(
                                transaction.getId(),
                                transaction.getAccountId(),
                                transaction.getAmount(),
                                transaction.getType(),
                                transaction.getTimestamp()
                        )
                )
                .toList();
    }
}