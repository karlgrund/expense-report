package com.karlgrund.expense.tracker;

import com.karlgrund.expense.tracker.configuration.ExpenseTrackerConfiguration;
import com.karlgrund.expense.tracker.dao.PartialPaymentsDAO;
import com.karlgrund.expense.tracker.dao.PurchaseDAO;
import com.karlgrund.expense.tracker.dao.TripDAO;
import com.karlgrund.expense.tracker.dao.TripParticipationDAO;
import com.karlgrund.expense.tracker.dao.UserDAO;
import com.karlgrund.expense.tracker.dto.User;
import com.karlgrund.expense.tracker.filter.BasicAuthentication;
import com.karlgrund.expense.tracker.manager.PurchaseManager;
import com.karlgrund.expense.tracker.manager.TripManager;
import com.karlgrund.expense.tracker.mapper.PurchaseMapper;
import com.karlgrund.expense.tracker.mapper.TripMapper;
import com.karlgrund.expense.tracker.resources.AdministratorResource;
import com.karlgrund.expense.tracker.resources.PurchaseResource;
import com.karlgrund.expense.tracker.resources.ReportResource;
import com.karlgrund.expense.tracker.resources.TripResource;
import com.karlgrund.expense.tracker.resources.UserResource;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.java8.Java8Bundle;
import io.dropwizard.java8.auth.AuthDynamicFeature;
import io.dropwizard.java8.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.skife.jdbi.v2.DBI;

public class ExpenseTrackerApplication extends Application<ExpenseTrackerConfiguration> {

    public static void main(String[] args) throws Exception {
        new ExpenseTrackerApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<ExpenseTrackerConfiguration> bootstrap) {
        super.initialize(bootstrap);
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new Java8Bundle());

    }

    @Override
    public void run(ExpenseTrackerConfiguration expenseTrackerConfiguration, Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, expenseTrackerConfiguration.getDataSourceFactory(), "postgresql");

        final UserDAO userDAO = jdbi.onDemand(UserDAO.class);
        final PurchaseDAO purchaseDAO = jdbi.onDemand(PurchaseDAO.class);
        final PartialPaymentsDAO partialPaymentsDAO = jdbi.onDemand(PartialPaymentsDAO.class);
        final TripParticipationDAO tripParticipationDAO = jdbi.onDemand(TripParticipationDAO.class);
        final TripDAO tripDAO = jdbi.onDemand(TripDAO.class);

        final TripManager tripManager = new TripManager(tripDAO, tripParticipationDAO, userDAO);
        final PurchaseManager purchaseManager = new PurchaseManager(purchaseDAO, partialPaymentsDAO);

        environment.jersey().register(new AuthDynamicFeature(
            new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new BasicAuthentication(userDAO))
                .setRealm("authentication")
                .buildAuthFilter()
        ));

        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        environment.jersey().register(new AdministratorResource(userDAO));
        environment.jersey().register(new ReportResource(purchaseManager, tripManager));
        environment.jersey().register(new TripResource(tripManager));
        environment.jersey().register(new UserResource(userDAO));
        environment.jersey().register(new PurchaseResource(purchaseManager, tripManager, userDAO));

        jdbi.registerMapper(new PurchaseMapper(partialPaymentsDAO));
        jdbi.registerMapper(new TripMapper(tripParticipationDAO));
    }

}
