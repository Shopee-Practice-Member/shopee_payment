package com.example.shopee_payment.controller;

import com.example.shopee_payment.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    @GetMapping()
    public ResponseEntity getWalletId(@RequestParam("ownerId") String ownerId) {
        return ResponseEntity.ok(walletService.getWalletIdByOwnerId(ownerId));
    }

    @GetMapping("/find-by-phone-number/{phoneNumber}")
    public ResponseEntity getWalletIdByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(walletService.getWallerIdByPhoneNumber(phoneNumber));
    }
}
