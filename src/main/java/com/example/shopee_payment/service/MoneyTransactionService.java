package com.example.shopee_payment.service;

import com.example.shopee_payment.dto.request.OrderMessageRequestDto;
import com.example.shopee_payment.dto.request.TransferMoneyCreateRequestDto;
import com.example.shopee_payment.model.MoneyTransaction;
import com.example.shopee_payment.model.Wallet;
import com.example.shopee_payment.repository.MoneyTransactionRepository;
import com.example.shopee_payment.variable.MoneyTransactionVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MoneyTransactionService {

    @Autowired
    private MoneyTransactionRepository moneyTransactionRepository;

    @Autowired
    private WalletService walletService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String payForBill(OrderMessageRequestDto order) {
        var wallet = walletService.getWalletById(order.getWalletId());

        MoneyTransaction moneyTransaction = new MoneyTransaction();
        moneyTransaction.setBillType(MoneyTransactionVariable.BillType.WITHDRAW);
        moneyTransaction.setAmount(order.getPrice());
        moneyTransaction.setTransactionStatus(MoneyTransactionVariable.TransactionStatus.IN_PROGRESS);
        moneyTransaction.setMessage(order.getMessage());
        moneyTransaction.setTargetId(order.getId());
        moneyTransaction.setTargetType(MoneyTransactionVariable.TargetType.PAY_FOR_BILL);
        moneyTransaction.setWalletId(order.getWalletId());
        moneyTransaction.setOwnerId(wallet.getOwnerId());
        var savedMoneyTransaction = moneyTransactionRepository.saveAndFlush(moneyTransaction);

        try {
            walletService.withDraw(order.getWalletId(), order.getPrice());
            savedMoneyTransaction.setTransactionStatus(MoneyTransactionVariable.TransactionStatus.PAID);
        } catch (Exception e) {
            savedMoneyTransaction.setTransactionStatus(MoneyTransactionVariable.TransactionStatus.FAILED);
        }

        var savedTransaction = moneyTransactionRepository.save(savedMoneyTransaction);
        return savedTransaction.getId();
    }

    public MoneyTransaction getTransactionById(String id) {
        return moneyTransactionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Transaction with id %s is not existed", id)));
    }


    public List<MoneyTransaction> getMoneyTransactionByAccountId(String accountId) {
        Wallet wallet = walletService.getWalletByOwnerId(accountId)
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format(" Wallet with owner id %s is not existed", accountId)));
        List<MoneyTransaction> moneyTransactions = moneyTransactionRepository.findByWalletId(wallet.getId());
        return moneyTransactions;
    }

    @Transactional
    public Boolean transferMoney(TransferMoneyCreateRequestDto transferMoneyCreateRequestDto) {
        Wallet fromWallet = walletService.getWalletByOwnerId(transferMoneyCreateRequestDto.getFromWalletOwnerId())
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format(" Wallet with owner id %s is not existed", transferMoneyCreateRequestDto.getFromWalletOwnerId())));
        Wallet toWallet = walletService.getWalletByOwnerId(transferMoneyCreateRequestDto.getToWalletOwnerId())
                .orElseThrow(() ->
                        new IllegalArgumentException(String.format(" Wallet with owner id %s is not existed", transferMoneyCreateRequestDto.getToWalletOwnerId())));

        withDrawByTransferingMoney(transferMoneyCreateRequestDto, fromWallet);
        depositByReceivingMoney(transferMoneyCreateRequestDto, toWallet);

        return true;
    }

    private Boolean withDrawByTransferingMoney(TransferMoneyCreateRequestDto transferMoneyCreateRequestDto, Wallet wallet) {
        MoneyTransaction moneyTransaction = new MoneyTransaction();
        moneyTransaction.setBillType(MoneyTransactionVariable.BillType.WITHDRAW);
        moneyTransaction.setAmount(transferMoneyCreateRequestDto.getAmount());
        moneyTransaction.setTransactionStatus(MoneyTransactionVariable.TransactionStatus.IN_PROGRESS);
        moneyTransaction.setMessage(transferMoneyCreateRequestDto.getMessage());
        moneyTransaction.setTargetType(MoneyTransactionVariable.TargetType.WALLET);
        moneyTransaction.setTargetId(transferMoneyCreateRequestDto.getToWalletOwnerId());

        moneyTransaction.setOwnerId(wallet.getOwnerId());
        moneyTransaction.setWalletId(wallet.getId());
        var savedMoneyTransaction = moneyTransactionRepository.saveAndFlush(moneyTransaction);

        try {
            walletService.withDraw(wallet.getId(), transferMoneyCreateRequestDto.getAmount());
            savedMoneyTransaction.setTransactionStatus(MoneyTransactionVariable.TransactionStatus.PAID);
            moneyTransactionRepository.save(savedMoneyTransaction);
        } catch (Exception e) {
            savedMoneyTransaction.setTransactionStatus(MoneyTransactionVariable.TransactionStatus.FAILED);
            moneyTransactionRepository.save(savedMoneyTransaction);
            return false;
        }
        return true;
    }

    private Boolean depositByReceivingMoney(TransferMoneyCreateRequestDto transferMoneyCreateRequestDto, Wallet wallet) {

        MoneyTransaction moneyTransaction = new MoneyTransaction();
        moneyTransaction.setBillType(MoneyTransactionVariable.BillType.DEPOSIT);
        moneyTransaction.setAmount(transferMoneyCreateRequestDto.getAmount());
        moneyTransaction.setTransactionStatus(MoneyTransactionVariable.TransactionStatus.IN_PROGRESS);
        moneyTransaction.setMessage(transferMoneyCreateRequestDto.getMessage());
        moneyTransaction.setTargetType(MoneyTransactionVariable.TargetType.WALLET);
        moneyTransaction.setTargetId(transferMoneyCreateRequestDto.getFromWalletOwnerId());

        moneyTransaction.setOwnerId(wallet.getOwnerId());
        moneyTransaction.setWalletId(wallet.getId());
        var savedMoneyTransaction = moneyTransactionRepository.saveAndFlush(moneyTransaction);

        try {
            walletService.deposit(wallet.getId(), transferMoneyCreateRequestDto.getAmount());
            savedMoneyTransaction.setTransactionStatus(MoneyTransactionVariable.TransactionStatus.PAID);
            moneyTransactionRepository.save(savedMoneyTransaction);

        } catch (Exception e) {
            savedMoneyTransaction.setTransactionStatus(MoneyTransactionVariable.TransactionStatus.FAILED);
            moneyTransactionRepository.save(savedMoneyTransaction);
            return false;
        }
        return true;
    }
}
