package com.enterprisebanking.accountservice.repository;

import com.enterprisebanking.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository
        extends JpaRepository<Account, Long> {

    List<Account> findByUserId(Long userId);
}