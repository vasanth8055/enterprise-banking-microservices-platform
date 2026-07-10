package com.enterprisebanking.statementservice.kafka;

import com.enterprisebanking.common.dto.TransactionEvent;
import com.enterprisebanking.statementservice.entity.Statement;
import com.enterprisebanking.statementservice.repository.StatementRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class StatementConsumer {

    private final StatementRepository repository;

    public StatementConsumer(StatementRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "transaction-events",
            groupId = "statement-group"
    )
    public void consume(TransactionEvent event) {

        Statement statement = new Statement();

        statement.setTransactionId(event.getTransactionId());
        statement.setAccountId(event.getFromAccountId());
        statement.setAmount(event.getAmount());
        statement.setTransactionType(event.getTransactionType());
        statement.setTransactionDate(LocalDateTime.now());
        statement.setAvailableBalance(event.getAvailableBalance());

        repository.save(statement);

        System.out.println("✅ Statement Saved");
    }
}