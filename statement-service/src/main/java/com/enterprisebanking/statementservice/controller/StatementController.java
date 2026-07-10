package com.enterprisebanking.statementservice.controller;

import com.enterprisebanking.statementservice.entity.Statement;
import com.enterprisebanking.statementservice.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/statements")
public class StatementController {

    @Autowired
    private StatementService service;

    @GetMapping("/{accountId}")
    public List<Statement> getStatement(
            @PathVariable Long accountId) {

        return service.getAllStatements(accountId);
    }

    @GetMapping("/mini/{accountId}")
    public List<Statement> getMiniStatement(
            @PathVariable Long accountId) {

        return service.getMiniStatement(accountId);
    }
}