package com.example.shopee_payment.mapper;

import com.example.shopee_payment.dto.request.UserAccountCreateRequestDto;
import com.example.shopee_payment.dto.response.UserAccountReadResponseDto;
import com.example.shopee_payment.model.UserAccount;

public class UserAccountMapper {

    public static UserAccount dtoToEntity(UserAccountCreateRequestDto dto) {
        UserAccount userAccount = new UserAccount();
        userAccount.setFirstName(dto.getFirstName());
        userAccount.setLastName(dto.getLastName());
        userAccount.setPhoneNumber(dto.getPhoneNumber());
        userAccount.setIdentifyCard(dto.getIdentifyCard());
        userAccount.setDateOfBirth(dto.getDateOfBirth());
        return userAccount;
    }

    public static UserAccountReadResponseDto entityToReadResponseDto(UserAccount account) {
        return UserAccountReadResponseDto.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .identifyCard(account.getIdentifyCard())
                .phoneNumber(account.getPhoneNumber())
                .build();
    }
}
