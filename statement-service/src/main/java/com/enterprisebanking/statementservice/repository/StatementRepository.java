package com.enterprisebanking.statementservice.repository;

import com.enterprisebanking.statementservice.entity.Statement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatementRepository extends JpaRepository<Statement, Long> {

    List<Statement> findByAccountIdOrderByTransactionDateDesc(Long accountId);

    List<Statement> findTop5ByAccountIdOrderByTransactionDateDesc(Long accountId);
}