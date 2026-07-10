package com.enterprisebanking.statementservice.service;

import com.enterprisebanking.statementservice.entity.Statement;
import com.enterprisebanking.statementservice.repository.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatementService {

    @Autowired
    private StatementRepository repository;

    public List<Statement> getAllStatements(Long accountId) {
        return repository.findByAccountIdOrderByTransactionDateDesc(accountId);
    }

    public List<Statement> getMiniStatement(Long accountId) {
        return repository.findTop5ByAccountIdOrderByTransactionDateDesc(accountId);
    }
}