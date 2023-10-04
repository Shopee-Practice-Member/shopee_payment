package com.example.shopee_payment.repository;

import com.example.shopee_payment.model.MoneyTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MoneyTransactionRepository extends JpaRepository<MoneyTransaction, String> {

    List<MoneyTransaction> findByWalletId(String walletId);
}
