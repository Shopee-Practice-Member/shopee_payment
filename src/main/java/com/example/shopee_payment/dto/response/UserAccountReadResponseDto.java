package com.example.shopee_payment.dto.response;

import com.example.shopee_payment.helper.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class UserAccountReadResponseDto implements Serializable {

    private String id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateOfBirth;

    private String identifyCard;
}
