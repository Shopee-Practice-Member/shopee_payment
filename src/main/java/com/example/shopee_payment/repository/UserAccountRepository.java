package com.example.shopee_payment.repository;

import com.example.shopee_payment.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {

    Optional<UserAccount> findUserAccountByPhoneNumber(String phoneNumber);

    Optional<UserAccount> findUserAccountByIdentifyCard(String identifyCard);
}
