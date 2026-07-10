package com.enterprisebanking.transactionservice.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.enterprisebanking.common.dto.TransactionEvent;
@Service
public class TransactionProducer {

    @Autowired
    private KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    private static final String TOPIC =
            "transaction-events";

    public void sendTransactionEvent(TransactionEvent event) {
        kafkaTemplate.send(TOPIC, event);

        System.out.println("Transaction Event Sent : " + event.getTransactionType());
    }
}