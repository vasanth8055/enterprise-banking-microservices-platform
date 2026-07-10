package com.enterprisebanking.frauddetectionservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.enterprisebanking.common.dto.TransactionEvent;
@Service
public class FraudDetectionConsumer {

    @KafkaListener(
            topics = "transaction-events",
            groupId = "fraud-group"
    )
    public void consume(TransactionEvent event){

        System.out.println("Received Transaction");

        System.out.println(event.getTransactionType());

        System.out.println(event.getAmount());

        if(event.getAmount()>10000){

            System.out.println("⚠ FRAUD ALERT");
        }
    }
}