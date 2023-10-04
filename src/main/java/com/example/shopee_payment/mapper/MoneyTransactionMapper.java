package com.example.shopee_payment.mapper;

import com.example.shopee_payment.dto.response.MoneyTransactionReadResponseDto;
import com.example.shopee_payment.model.MoneyTransaction;

public class MoneyTransactionMapper {

  public static MoneyTransactionReadResponseDto entityToReadResponseDto(MoneyTransaction moneyTransaction) {
      return MoneyTransactionReadResponseDto.builder()
              .id(moneyTransaction.getId())
              .createdDate(moneyTransaction.getCreatedDate())
              .ownerId(moneyTransaction.getOwnerId())
              .targetId(moneyTransaction.getTargetId())
              .targetType(moneyTransaction.getTargetType().label)
              .transactionStatus(moneyTransaction.getTransactionStatus().label)
              .amount(moneyTransaction.getAmount())
              .message(moneyTransaction.getMessage())
              .billType(moneyTransaction.getBillType().label)
              .build();
  }
}
