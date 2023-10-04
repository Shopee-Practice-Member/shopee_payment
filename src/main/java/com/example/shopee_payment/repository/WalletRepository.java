package com.example.shopee_payment.repository;

import com.example.shopee_payment.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String> {

    Optional<Wallet> findByOwnerId(String ownerId);

    @Query("SELECT wallet from Wallet wallet " +
            "inner join UserAccount account " +
            "on wallet.ownerId = account.id " +
            "where account.phoneNumber " +
            "like :phoneNumber")
    Optional<Wallet> findByPhoneNumber(String phoneNumber);
}
