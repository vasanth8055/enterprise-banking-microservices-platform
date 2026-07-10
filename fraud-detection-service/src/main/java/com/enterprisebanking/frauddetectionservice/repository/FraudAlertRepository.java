package com.enterprisebanking.frauddetectionservice.repository;

import com.enterprisebanking.frauddetectionservice.entity.FraudAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FraudAlertRepository extends JpaRepository<FraudAlert, Long> {
}