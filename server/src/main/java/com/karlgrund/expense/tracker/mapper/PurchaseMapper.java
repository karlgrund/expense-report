package com.karlgrund.expense.tracker.mapper;

import com.karlgrund.expense.tracker.dao.PartialPaymentsDAO;
import com.karlgrund.expense.tracker.dto.Purchase;
import com.karlgrund.expense.tracker.util.CurrencyId;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class PurchaseMapper implements ResultSetMapper<Purchase> {
    private static PartialPaymentsDAO partialPaymentsDAO;

    public PurchaseMapper() {

    }

    public PurchaseMapper(PartialPaymentsDAO partialPaymentsDAO) {
        this.partialPaymentsDAO = partialPaymentsDAO;
    }

    @Override
    public Purchase map(int index, ResultSet resultSet, StatementContext ctx) throws SQLException {

        Purchase purchase = new Purchase(
            resultSet.getString("uuid"),
            resultSet.getString("event_name"),
            resultSet.getLong("price"),
            CurrencyId.valueOf(resultSet.getString("currency")),
            new ArrayList<>(),
            resultSet.getTimestamp("purchase_date")
        );

        purchase.getPartialPayments().addAll(
            partialPaymentsDAO.getPartialPayments(purchase.getPurchaseUUID())
        );

        return purchase;
    }
}
