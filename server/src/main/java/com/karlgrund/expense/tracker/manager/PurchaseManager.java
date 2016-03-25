package com.karlgrund.expense.tracker.manager;

import com.karlgrund.expense.tracker.dao.PartialPaymentsDAO;
import com.karlgrund.expense.tracker.dao.PurchaseDAO;
import com.karlgrund.expense.tracker.dto.PartialPayment;
import com.karlgrund.expense.tracker.dto.Purchase;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.BadRequestException;

public class PurchaseManager {
    PurchaseDAO purchaseDAO;
    PartialPaymentsDAO partialPaymentsDAO;

    public PurchaseManager(
        PurchaseDAO purchaseDAO,
        PartialPaymentsDAO partialPaymentsDAO
    ) {
        this.purchaseDAO = purchaseDAO;
        this.partialPaymentsDAO = partialPaymentsDAO;
    }

    public boolean purchaseExists(Purchase purchase) {
        return purchaseDAO.purchaseExists(purchase) != null;
    }

    public void storePurchase(Purchase purchase) {
        purchaseDAO.store(purchase);
    }

    public Purchase getPurchase(String purchaseUUID) {
        Purchase purchase = purchaseDAO.getPurchase(purchaseUUID);
        if (purchase == null) {
            throw new BadRequestException("Purchase does not exist");
        }
        return purchase;
    }

    public List<Purchase> getPurchases(String eventName, Optional<String> userEmail) {
        if (userEmail.isPresent()) {
            return purchaseDAO.getPurchase(eventName, userEmail.get());
        }

        return purchaseDAO.getPurchasesFor(eventName);
    }

    public void storePartialPayment(PartialPayment partialPayment) {
        partialPaymentsDAO.store(partialPayment);
    }

    public void updatePurchase(Purchase storedPurchase) {
        partialPaymentsDAO.remove(storedPurchase.getPurchaseUUID());
        purchaseDAO.remove(storedPurchase.getPurchaseUUID());

        storedPurchase.getPartialPayments().stream().forEach(this::storePartialPayment);
        storePurchase(storedPurchase);
    }
}
