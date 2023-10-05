package com.example.shopee_payment.model;

import com.example.shopee_payment.constant.MoneyTransactionConstant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
public class MoneyTransaction {

    @Id
    @Column()
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column()
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column()
    @UpdateTimestamp
    private LocalDateTime lastUpdatedDate;

    @Column
    @NotNull
    private String walletId;

    @Column
    @NotNull
    private String ownerId;

    @Column
    @NotNull
    private String targetId;

    @Column
    @Enumerated(EnumType.STRING)
    private MoneyTransactionConstant.TargetType targetType;

    @Column
    @Enumerated(EnumType.STRING)
    private MoneyTransactionConstant.TransactionStatus transactionStatus;

    @Column
    private float amount;

    @Column
    private String message;

    @Column
    @Enumerated(EnumType.STRING)
    private MoneyTransactionConstant.BillType billType;


}
