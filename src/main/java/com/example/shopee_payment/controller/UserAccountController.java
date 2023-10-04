package com.example.shopee_payment.controller;

import com.example.shopee_payment.dto.request.UserAccountCreateRequestDto;
import com.example.shopee_payment.dto.response.UserAccountReadResponseDto;
import com.example.shopee_payment.mapper.UserAccountMapper;
import com.example.shopee_payment.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/account")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @PostMapping("")
    public ResponseEntity createAccount(@RequestBody UserAccountCreateRequestDto createRequestDto) {
        var result = userAccountService.createUserAccount(UserAccountMapper.dtoToEntity(createRequestDto));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all")
    public ResponseEntity getAll() {
        var result = userAccountService.getAll().stream().map(UserAccountMapper::entityToReadResponseDto).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity getById(@PathVariable("id") String id) {
        var result = UserAccountMapper.entityToReadResponseDto(userAccountService.getById(id));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/find")
    public ResponseEntity getByPhoneNumber(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("identifyCard") String identifyCard) {
        UserAccountReadResponseDto result;
        if(phoneNumber == null) {
            result = UserAccountMapper.entityToReadResponseDto(userAccountService.getByIdentifyCard(identifyCard));
        }else {
            result = UserAccountMapper.entityToReadResponseDto(userAccountService.getByPhoneNumber(phoneNumber));
        }
        return ResponseEntity.ok(result);
    }


}
