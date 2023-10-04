package com.example.shopee_payment.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaidOrderMessageRequestDto {

    private String id;

    private float price;

    private String nameService;

    private String message;

    private String transactionStatus;
}
