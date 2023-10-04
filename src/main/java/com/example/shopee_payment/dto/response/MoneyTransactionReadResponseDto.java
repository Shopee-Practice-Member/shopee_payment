package com.example.shopee_payment.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MoneyTransactionReadResponseDto implements Serializable {

    private String id;

    private LocalDateTime createdDate;

    private String ownerId;

    private String targetId;

    private String targetType;

    private String transactionStatus;

    private float amount;

    private String message;

    private String billType;
}
