package com.example.shopee_payment.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderMessageRequestDto {

    private String id;

    private float price;

    private String nameService;

    @NotNull
    private String message;

    private String walletId;
}
