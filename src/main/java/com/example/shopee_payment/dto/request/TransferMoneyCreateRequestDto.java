package com.example.shopee_payment.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
@Getter
@Setter
public class TransferMoneyCreateRequestDto {

    @NonNull
    private String fromWalletOwnerId;

    @NonNull
    private String toWalletOwnerId;

    @NonNull
    @Min(1000)
    private float amount;

    private String message;
}
