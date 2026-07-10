package com.enterprisebanking.frauddetectionservice.service;

import com.enterprisebanking.frauddetectionservice.entity.FraudAlert;
import com.enterprisebanking.frauddetectionservice.repository.FraudAlertRepository;
import org.springframework.stereotype.Service;
import com.enterprisebanking.common.dto.TransactionEvent;
import java.time.LocalDateTime;

@Service
public class
FraudDetectionService {

    private final FraudAlertRepository repository;

    public FraudDetectionService(FraudAlertRepository repository) {
        this.repository = repository;
    }

    public void analyze(TransactionEvent event) {

        if (event.getAmount() > 50000) {

            FraudAlert alert = new FraudAlert();

            alert.setAccountId(event.getFromAccountId());
            alert.setAmount(event.getAmount());
            alert.setTransactionType(event.getTransactionType());
            alert.setRiskLevel("HIGH");
            alert.setReason("Transaction exceeds ₹50,000");
            alert.setCreatedAt(LocalDateTime.now());

            repository.save(alert);

            System.out.println("Fraud Alert Saved");
        }
    }
}