package com.karlgrund.expense.tracker.dao;

import com.karlgrund.expense.tracker.dto.PartialPayment;
import com.karlgrund.expense.tracker.mapper.PartialPaymentMapper;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(PartialPaymentMapper.class)
public abstract class PartialPaymentsDAO {

    @SqlQuery("Select * FROM partial_payments WHERE purchase_uuid = :purchaseUUID")
    public abstract List<PartialPayment> getPartialPayments(@Bind("purchaseUUID") String purchaseUUID);

    @SqlQuery(
        "Select * FROM partial_payments "
            + "WHERE email = :userEmail"
    )
    public abstract List<PartialPayment> getPartialPaymentsForUser(
        @Bind("userEmail") String userEmail
    );

    @SqlUpdate("INSERT INTO partial_payments(purchase_uuid, amount, email) VALUES("
        + ":purchaseUUID, "
        + ":amount, "
        + ":email)")
    public abstract void store(@BindBean PartialPayment partialPayment);

    @SqlUpdate("DELETE FROM partial_payments where purchase_uuid = :purchaseUUID")
    public abstract void remove(@Bind("purchaseUUID") String purchaseUUID);
}
