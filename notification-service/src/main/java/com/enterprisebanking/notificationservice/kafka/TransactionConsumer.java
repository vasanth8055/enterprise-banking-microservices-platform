package com.enterprisebanking.notificationservice.kafka;

import com.enterprisebanking.common.dto.TransactionEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TransactionConsumer {

    @KafkaListener(
            topics = "transaction-events",
            groupId = "notification-group"
    )
    public void consume(TransactionEvent event) {

        System.out.println("========== Notification ==========");
        System.out.println("Type : " + event.getTransactionType());
        System.out.println("Amount : " + event.getAmount());
        System.out.println("From : " + event.getFromAccountId());
        System.out.println("To : " + event.getToAccountId());
        System.out.println("==================================");
    }
}