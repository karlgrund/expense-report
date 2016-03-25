package com.karlgrund.expense.tracker.resources;

import com.karlgrund.expense.tracker.dto.Purchase;
import com.karlgrund.expense.tracker.dto.Report;
import com.karlgrund.expense.tracker.dto.Event;
import com.karlgrund.expense.tracker.manager.PurchaseManager;
import com.karlgrund.expense.tracker.manager.EventManager;
import com.karlgrund.expense.tracker.util.CurrencyConverter;
import com.karlgrund.expense.tracker.util.dto.PurchaseReport;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("report")
public class ReportResource {

    private PurchaseManager purchaseManager;
    private EventManager eventManager;

    public ReportResource(PurchaseManager purchaseManager, EventManager eventManager) {
        this.purchaseManager = purchaseManager;
        this.eventManager = eventManager;
    }

    @GET
    public Report generateReport(
        @QueryParam("eventName") String eventName
    ) {

        Event event = eventManager.findEvent(eventName);
        List<Purchase> purchases = purchaseManager.getPurchases(
            eventName,
            Optional.empty()
        );
        if (purchases.isEmpty()) {
            throw new BadRequestException("No purchases made on the event");
        }
        PurchaseReport purchaseReport = new PurchaseReport(
            purchases,
            event,
            new CurrencyConverter()
        );
        Report report = new Report(event);

        report.addExpenses(purchaseReport);

        return report;
    }
}
