package com.karlgrund.expense.tracker.resources;

import com.karlgrund.expense.tracker.dao.UserDAO;
import com.karlgrund.expense.tracker.dto.PartialPayment;
import com.karlgrund.expense.tracker.dto.Purchase;
import com.karlgrund.expense.tracker.manager.PurchaseManager;
import com.karlgrund.expense.tracker.manager.TripManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("purchase")
public class PurchaseResource {
    private PurchaseManager purchaseManager;
    private TripManager tripManager;
    private UserDAO userDAO;

    public PurchaseResource(
        PurchaseManager purchaseManager,
        TripManager tripManager,
        UserDAO userDAO
    ) {
        this.purchaseManager = purchaseManager;
        this.tripManager = tripManager;
        this.userDAO = userDAO;
    }

    @POST
    public Purchase createPurchase(
        @Valid Purchase purchase
    ) {
        purchaseExists(purchase);
        validatePurchase(purchase);

        purchase.getPartialPayments()
            .stream()
            .forEach(purchaseManager::storePartialPayment);

        purchaseManager.storePurchase(purchase);

        return purchase;
    }

    @PUT
    @Path("{purchaseUUID}")
    public Purchase updatePurchase(
        @PathParam("purchaseUUID") String purchaseUUID,
        @Valid Purchase purchase
    ) {
        Purchase storedPurchase = purchaseManager.getPurchase(purchaseUUID);

        validatePurchase(purchase);

        purchase.getPartialPayments()
            .stream()
            .forEach(partialPayment -> partialPayment.setPurchaseUUID(purchaseUUID));

        storedPurchase.update(purchase);
        purchaseManager.updatePurchase(storedPurchase);

        return storedPurchase;
    }

    @GET
    public List<Purchase> getPurchases(
        @QueryParam("tripId") String tripName,
        @QueryParam("userEmail") String userEmail
    ) {
        tripManager.validateTrip(tripName);

        return purchaseManager.getPurchases(tripName, Optional.ofNullable(userEmail));
    }

    private void purchaseExists(Purchase purchase) {
        if (purchaseManager.purchaseExists(purchase)) {
            throw new BadRequestException("Purchase already exists");
        }
    }

    private void validatePurchase(Purchase purchase) {
        // See if trip exists
        tripManager.validateTrip(purchase.getTripName());

        // Everything is paid for
        Long totalAmount = purchase.getPartialPayments()
            .stream()
            .mapToLong(PartialPayment::getAmount)
            .sum();

        if (!totalAmount.equals(purchase.getPrice())) {
            throw new BadRequestException("Partial payment does not add up");
        }

        // All users exist
        if (!purchase.getPartialPayments()
            .stream()
            .allMatch(
                partialPayments -> Objects.nonNull(userDAO.findUserByEmail(partialPayments.getEmail())))) {
            throw new BadRequestException("All users does not exist");
        }
    }
}
