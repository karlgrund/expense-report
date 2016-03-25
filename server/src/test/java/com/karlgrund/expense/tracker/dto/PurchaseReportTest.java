package com.karlgrund.expense.tracker.dto;

import com.karlgrund.expense.tracker.util.CurrencyConverter;
import com.karlgrund.expense.tracker.util.dto.PurchaseReport;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;

public class PurchaseReportTest {
    private static final String EVENT_NAME = "Event Name";
    private static final String EMAIL1 = "test@gmail.com";
    private static final String EMAIL2 = "prov@gmail.com";
    private PurchaseReport purchaseReport;
    private DecimalFormat df = new DecimalFormat("#.00");

    @Before
    public void setUp() {
        Event event = new Event(EVENT_NAME, Arrays.asList(EMAIL1, EMAIL2));
        PartialPayment partialPayment1 = new PartialPayment(
            EMAIL1,
            25L
        );
        PartialPayment partialPayment2 = new PartialPayment(
            EMAIL2,
            75L
        );

        Purchase purchase = new Purchase(
            100L,
            EVENT_NAME,
            "SEK",
            Arrays.asList(partialPayment1, partialPayment2),
            new Date()
        );

        Purchase purchase2 = new Purchase(
            100L,
            EVENT_NAME,
            "EUR",
            Arrays.asList(partialPayment1, partialPayment2),
            new Date()
        );

        purchaseReport = new PurchaseReport(
            Arrays.asList(purchase, purchase2),
            event,
            new CurrencyConverter()
        );
    }

    @Test
    public void shouldGetCurrency() {
        System.out.println(df.format(purchaseReport.getTotalAmount()));
        purchaseReport.getParticipantExpenses().keySet().stream().forEach(
            s -> System.out.println(
                "Email: " + s + " Amount: " + df.format(purchaseReport.getParticipantExpenses().get(s)) + " SEK")
        );
    }

}
