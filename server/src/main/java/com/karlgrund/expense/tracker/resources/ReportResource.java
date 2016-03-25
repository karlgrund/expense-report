package com.karlgrund.expense.tracker.resources;

import com.karlgrund.expense.tracker.dto.Purchase;
import com.karlgrund.expense.tracker.dto.Report;
import com.karlgrund.expense.tracker.dto.Trip;
import com.karlgrund.expense.tracker.manager.PurchaseManager;
import com.karlgrund.expense.tracker.manager.TripManager;
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
    private TripManager tripManager;

    public ReportResource(PurchaseManager purchaseManager, TripManager tripManager) {
        this.purchaseManager = purchaseManager;
        this.tripManager = tripManager;
    }

    @GET
    public Report generateReport(
        @QueryParam("tripName") String tripName
    ) {

        Trip trip = tripManager.findTrip(tripName);
        List<Purchase> purchases = purchaseManager.getPurchases(
            tripName,
            Optional.empty()
        );
        if (purchases.isEmpty()) {
            throw new BadRequestException("No purchases made on the trip");
        }
        PurchaseReport purchaseReport = new PurchaseReport(
            purchases,
            trip,
            new CurrencyConverter()
        );
        Report report = new Report(trip);

        report.addExpenses(purchaseReport);

        return report;
    }
}
