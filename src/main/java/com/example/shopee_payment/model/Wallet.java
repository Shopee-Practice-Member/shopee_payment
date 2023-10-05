package com.example.shopee_payment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table
public class Wallet {

    @Id
    @Column()
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column
    @NonNull
    @Min(0)
    private float balance = 0;

    @Column(updatable = false)
    private String ownerId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Float getBalance() {
        return balance;
    }

    public void deposit(float money) {
        this.balance += money;
    }

    public void withDraw(float money) {
        if(this.balance > 0 && this.balance > money){
            this.balance -= money;
        }else {
            throw new IllegalArgumentException("The balance of wallet is not enough to make transaction");
        }
    }


}
