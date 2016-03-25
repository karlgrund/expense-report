package com.karlgrund.expense.tracker.dao;

import com.karlgrund.expense.tracker.dto.User;
import com.karlgrund.expense.tracker.mapper.UserMapper;
import java.util.List;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(UserMapper.class)
public abstract class UserDAO {

    @SqlQuery("SELECT * from users")
    public abstract List<User> findAll();

    @SqlQuery("SELECT * from users where email = :email")
    public abstract User findUserByEmail(@Bind("email") String email);

    @SqlUpdate("INSERT INTO users(first_name, last_name, email, hashed_password, is_admin) "
        + "VALUES( "
        + ":firstName, "
        + ":lastName, "
        + ":email, "
        + ":hashedPassword, "
        + ":isAdmin"
        + ")")
    public abstract void store(@BindBean User user, @Bind("isAdmin") boolean isAdmin);

    /*
        @SqlQuery(""
            + "SELECT message "
            + "FROM purchase_message pm "
            + "INNER JOIN purchase_history ph ON ph.purchase_uuid = pm.purchase_uuid "
            + "WHERE purchase_id BETWEEN :startId AND :endId ORDER BY purchase_id ASC")
     */
    @SqlQuery("Select uuid "
        + "FROM user u "
        + "INNSER JOIN ")
    public abstract List<User> findParticipantsOn(String uuid);
}
