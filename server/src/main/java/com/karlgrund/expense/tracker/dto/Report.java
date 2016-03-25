package com.karlgrund.expense.tracker.dto;

import com.karlgrund.expense.tracker.util.dto.PurchaseReport;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Report {
    private Map<String, String> expenses = new HashMap<>();
    private DecimalFormat df = new DecimalFormat("#.00");
    private Trip trip;

    public Report(Trip trip) {
        this.trip = trip;
    }

    public Trip getTrip() {
        return trip;
    }

    public Map<String, String> getExpenses() {
        return expenses;
    }

    public void addExpenses(PurchaseReport purchaseReport) {
        purchaseReport.getParticipantExpenses()
            .keySet()
            .stream()
            .forEach(key -> add(key, purchaseReport.getParticipantExpenses().get(key)));
    }

    private void add(String key, double amount) {
        expenses.put(key, df.format(amount));
    }
}
