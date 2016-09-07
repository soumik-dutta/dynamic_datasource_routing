package com.omoto.viewaccess;

import org.springframework.util.Assert;

/**
 * Created by omoto on 22/8/16.
 */

public class DbContextHolder {

    private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<DbType>();

    public static void setCustomer(DbType customerType) {
        Assert.notNull(customerType, "customerType cannot be null");
        contextHolder.set(customerType);
    }

    public static DbType getCustomer() {
        return (DbType) contextHolder.get();
    }

    /**
     * call this method as soon as the ThreadLocal job is over
     * because as it will be there in permanent heap and will
     * not to be garbage collected even after the web app is restarted
     * as the thread instance is not owned by webapp .
     *
     * @link http://stackoverflow.com/questions/817856/when-and-how-should-i-use-a-threadlocal-variable
     */
    public static void clearCustomer() {
        contextHolder.remove();
    }
}



