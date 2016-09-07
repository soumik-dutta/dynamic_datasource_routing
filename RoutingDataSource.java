package com.omoto.viewaccess;

import com.omoto.constants.Constants;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Routing datasource accouring to the call
 * The caller will have to mention the client name
 * and the datasource will be set according to the
 * conf. file mentioned.
 * Created by soumik on 22/8/16.
 */
public class RoutingDataSource extends AbstractRoutingDataSource implements Constants {


    //get the customer
    @Override
    protected Object determineCurrentLookupKey() {
        return DbContextHolder.getCustomer();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

}
