package com.example.shopee_payment.controller;

import com.example.shopee_payment.dto.request.TransferMoneyCreateRequestDto;
import com.example.shopee_payment.mapper.MoneyTransactionMapper;
import com.example.shopee_payment.service.MoneyTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class MoneyTransactionController {

    @Autowired
    private MoneyTransactionService moneyTransactionService;

    @PostMapping("/transfer")
    public ResponseEntity transferMoney(@RequestBody TransferMoneyCreateRequestDto transferMoneyCreateRequestDto) {
        var result = moneyTransactionService.transferMoney(transferMoneyCreateRequestDto);
        return ResponseEntity.ok(result ? "Transfer successful" : "Transfer Failed");
    }

    @GetMapping("/find-by-user/{id}")
    public ResponseEntity getTransactionsOfUser(@PathVariable("id") String accountId) {
        var result = moneyTransactionService.getMoneyTransactionByAccountId(accountId).stream().map(MoneyTransactionMapper::entityToReadResponseDto).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
