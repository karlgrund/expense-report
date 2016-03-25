package com.karlgrund.expense.tracker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Email;

public class PartialPayment {
    private String purchaseUUID;
    @Email
    private String email;
    private Long amount;

    @JsonCreator
    public PartialPayment(
        @JsonProperty("email") String email,
        @JsonProperty("amount") Long amount
    ) {
        this.email = email;
        this.amount = amount;
    }

    public PartialPayment(String purchaseUUID, String email, Long amount) {
        this.purchaseUUID = purchaseUUID;
        this.email = email;
        this.amount = amount;
    }

    public String getPurchaseUUID() {
        return purchaseUUID;
    }

    public void setPurchaseUUID(String purchaseUUID) {
        this.purchaseUUID = purchaseUUID;
    }

    public String getEmail() {
        return email;
    }

    public Long getAmount() {
        return amount;
    }
}
