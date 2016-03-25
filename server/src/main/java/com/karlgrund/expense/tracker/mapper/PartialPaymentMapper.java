package com.karlgrund.expense.tracker.mapper;

import com.karlgrund.expense.tracker.dto.PartialPayment;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

public class PartialPaymentMapper implements ResultSetMapper<PartialPayment> {
    @Override
    public PartialPayment map(int index, ResultSet resultSet, StatementContext ctx) throws SQLException {
        return new PartialPayment(
            resultSet.getString("purchase_uuid"),
            resultSet.getString("email"),
            resultSet.getLong("amount")
        );
    }
}
