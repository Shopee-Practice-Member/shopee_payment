package com.example.shopee_payment.service;

import com.example.shopee_payment.model.Wallet;
import com.example.shopee_payment.repository.WalletRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    public Boolean deposit(String walletId, float money) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new IllegalArgumentException(String.format("Wallet with id $s was not existed", walletId)));
        wallet.deposit(money);
        walletRepository.save(wallet);
        return true;
    }

    public Boolean withDraw(String walletId, float money) {
        Wallet wallet = walletRepository.findById(walletId).orElseThrow(() -> new IllegalArgumentException(String.format("Wallet with id $s was not existed", walletId)));
        wallet.withDraw(money);
        walletRepository.save(wallet);
        return true;
    }

    public Boolean isWalletExisted(String walletId) {
        return walletRepository.existsById(walletId);
    }

    public Wallet saveWallet(String ownerId) {
        Wallet wallet = new Wallet();
        wallet.setOwnerId(ownerId);
        wallet.deposit(0);
        return walletRepository.save(wallet);
    }

    public String getWalletIdByOwnerId(String ownerId) {
        Wallet wallet = walletRepository.findByOwnerId(ownerId).orElseThrow(() -> new IllegalArgumentException(String.format("Wallet with owner id $s was not existed", ownerId)));
        return wallet.getId();
    }

    public String getWallerIdByPhoneNumber(String phoneNumber) {
        Wallet wallet = walletRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> new IllegalArgumentException(String.format("Wallet with phoneNumber $s was not existed", phoneNumber)));
        return wallet.getId();
    }

    public Wallet getWalletById(String id) {
        return walletRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Wallet with id $s was not existed", id)));
    }

    public Optional<Wallet> getWalletByOwnerId(String ownerId) {
        return walletRepository.findByOwnerId(ownerId);
    }
}
