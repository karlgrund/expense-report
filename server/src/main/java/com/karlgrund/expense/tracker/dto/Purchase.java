package com.karlgrund.expense.tracker.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.karlgrund.expense.tracker.util.CurrencyId;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotNull;

public class Purchase {
    private String purchaseUUID;
    @NotNull
    private Long price;
    @NotNull
    private String tripName;
    @NotNull
    private CurrencyId currencyId;
    private List<PartialPayment> partialPayments;
    private Date purchaseDate;

    @JsonCreator
    public Purchase(
        @JsonProperty("price") Long price,
        @JsonProperty("trip_name") String tripName,
        @JsonProperty("currency") String currencyId,
        @JsonProperty("partialpayment") List<PartialPayment> partialPayments,
        @JsonProperty("created") Date created
    ) {
        this(
            UUID.randomUUID().toString(),
            tripName,
            price,
            CurrencyId.valueOf(currencyId),
            partialPayments,
            created != null ? created : new Date()
        );
    }

    public Purchase(
        String purchaseUUID,
        String tripName,
        Long price,
        CurrencyId currencyId,
        List<PartialPayment> partialPayments,
        Date purchaseDate
    ) {
        this.purchaseUUID = purchaseUUID;
        this.tripName = tripName;
        this.price = price;
        this.currencyId = currencyId;
        this.partialPayments = partialPayments;
        this.purchaseDate = purchaseDate;
        updatePartialPayments();
    }

    public String getPurchaseUUID() {
        return purchaseUUID;
    }

    public String getTripName() {
        return tripName;
    }

    public List<PartialPayment> getPartialPayments() {
        return partialPayments;
    }

    public Long getPrice() {
        return price;
    }

    public CurrencyId getCurrencyId() {
        return currencyId;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void update(Purchase purchase) {
        this.tripName = purchase.getTripName();
        this.price = purchase.getPrice();
        this.currencyId = purchase.getCurrencyId();
        this.partialPayments = purchase.getPartialPayments();
        this.purchaseDate = purchase.getPurchaseDate();
    }

    private void updatePartialPayments() {
        partialPayments.stream().forEach(partialPayment -> partialPayment.setPurchaseUUID(this.purchaseUUID));
    }
}
