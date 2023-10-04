package com.example.shopee_payment.service;

import com.example.shopee_payment.model.UserAccount;
import com.example.shopee_payment.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountService {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserAccountRepository userAccountRepository;

    public String createUserAccount(UserAccount userAccount) {
        var savedUserAccount =  userAccountRepository.save(userAccount);
        walletService.saveWallet(savedUserAccount.getId());
        return savedUserAccount.getId();
    }

    public String updateUserAccount(UserAccount userAccount) {
        var currentAccount = userAccountRepository.findById(userAccount.getId()).orElseThrow(() -> new IllegalArgumentException(String.format("Account with id %s is not existed", userAccount.getId())));
        currentAccount.setFirstName(userAccount.getFirstName());
        currentAccount.setDateOfBirth(userAccount.getDateOfBirth());
        currentAccount.setPhoneNumber(userAccount.getPhoneNumber());
        currentAccount.setIdentifyCard(userAccount.getIdentifyCard());
        currentAccount.setLastName(userAccount.getLastName());
        return userAccountRepository.save(currentAccount).getId();
    }

    public List<UserAccount> getAll() {
        return userAccountRepository.findAll();
    }

    public UserAccount getById(String id) {
        return userAccountRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Account with id %s is not existed", id)));
    }

    public UserAccount getByPhoneNumber(String phoneNumber) {
        return userAccountRepository.findUserAccountByPhoneNumber(phoneNumber).orElseThrow(() -> new IllegalArgumentException(String.format("Account with phone number: %s is not existed", phoneNumber)));
    }

    public UserAccount getByIdentifyCard(String identifyCard) {
        return userAccountRepository.findUserAccountByIdentifyCard(identifyCard).orElseThrow(() -> new IllegalArgumentException(String.format("Account with identify card: %s is not existed", identifyCard)));
    }
}
