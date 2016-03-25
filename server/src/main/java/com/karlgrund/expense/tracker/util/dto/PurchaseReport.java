package com.karlgrund.expense.tracker.util.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.karlgrund.expense.tracker.dto.PartialPayment;
import com.karlgrund.expense.tracker.dto.Purchase;
import com.karlgrund.expense.tracker.dto.Event;
import com.karlgrund.expense.tracker.util.CurrencyConverter;
import com.karlgrund.expense.tracker.util.CurrencyId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseReport {
    @JsonIgnore
    private List<Purchase> purchases;
    private List<String> participantsInEvent;
    private CurrencyConverter currencyConverter;
    private Map<String, Double> participantExpenses;

    @JsonProperty("total_amount")
    private Double totalAmount;

    public PurchaseReport(
        List<Purchase> purchases,
        Event event,
        CurrencyConverter currencyConverter
    ) {
        this.purchases = purchases;
        this.participantsInEvent = event.getParticipantEmails();
        this.currencyConverter = currencyConverter;
        this.totalAmount = purchases
            .stream()
            .mapToDouble(purchase -> currencyConverter.convertToSEK(
                purchase.getPurchaseDate(),
                purchase.getPrice(),
                purchase.getCurrencyId()
            ))
            .sum();
        participantExpenses = new HashMap<>();

        calculateExpensesPerUser();
    }

    private void calculateExpensesPerUser() {
        purchases
            .stream()
            .forEach(purchase -> purchase
                .getPartialPayments()
                .stream()
                .forEach(partialPayment -> addExpense(partialPayment,
                    purchase.getPurchaseDate(),
                    purchase.getCurrencyId()
                ))
            );
        participantsInEvent.stream().forEach(participant -> {
            if (!participantExpenses.containsKey(participant)) {
                participantExpenses.put(participant, 0.0);
            }
        });
    }

    private void addExpense(PartialPayment partialPayment, Date purchaseDate, CurrencyId currencyId) {
        double expense = convertToSEK(partialPayment, purchaseDate, currencyId);
        if (participantExpenses.containsKey(partialPayment.getEmail())) {
            expense += participantExpenses.get(partialPayment.getEmail());
        }
        participantExpenses.put(partialPayment.getEmail(), expense);
    }

    private Double convertToSEK(PartialPayment partialPayment, Date purchaseDate, CurrencyId currencyId) {
        return currencyConverter.convertToSEK(purchaseDate, partialPayment.getAmount(), currencyId);
    }

    public Map<String, Double> getParticipantExpenses() {
        return participantExpenses;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

}
