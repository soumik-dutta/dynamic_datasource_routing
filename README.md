# Dynamic DataSource Routing using Spring3 - JDBC

The objective of the project is to route datasource at runtime.This is very useful when there are multiple connections to different databases and in different physical locations.The design diagram for this project [design_diagram] 

>The Routing process will initiate from the datasource configuration file.Here we have to configure the different datasources irrespective of the database vender and physical location.All the datasource bean is extending the base Datasource which is **viewAccessDataSource**

Spring-aop.xml
-
```xml
    <!-- Base DataSource  -->
    <bean id="viewAccessDataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource" abstract="true">
	</bean>
	<!-- client specific datasource integration -->
	<!-- Extending the ViewAccessDataSource -->
	<bean id="con1DataSource"  parent="viewAccessDataSource">
		<property name="driverClassName" value="org.postgresql.Driver" />
		<property name="url" value="jdbc:postgresql://your_ip:5432/db"/>
		<property name="username" value="root" />
		<property name="password" value="root" />
	</bean>
	<bean id="con2DataSource"  parent="viewAccessDataSource">
		<property name="driverClassName" value="com.mysql.jdbc.Driver" />
		<property name="url" value="jdbc:mysql://your_ip:3306/db"/>
		<property name="username" value="root" />
		<property name="password" value="root" />
	</bean>
	<!-- Datasource  -->
	<bean id="dataSourceRouting" class="com.omoto.viewaccess.RoutingDataSource">
		<property name="targetDataSources">
			<map key-type="com.omoto.viewaccess.DbType">
				<entry key="CON1" value-ref="con1DataSource"/>
				<entry key="CON2" value-ref="con2DataSource"/>
			</map>
		</property>
	</bean>
```


RoutingDataSource.java
-
>**DataSourceRouting** : This returns the datasource connection object which we are creating it in The ContextHolder.

```java
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
```
DbContextHolder
-
>**DbContextHolder** : Responsible for creating the connection with the database using the configuration.
```java
public class DbContextHolder {
    //ThreadLocal object
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
     * @link http://stackoverflow.com/questions/817856/when-and-how-should-i-use-a-threadlocal-variable
     */
    public static void clearCustomer() {
        contextHolder.remove();
    }
}
```

>**DynamicDatasourceRoutingController** : The controller is injected with the Context 





   [design_diagram]: <https://1drv.ms/i/s!Aojm_aTsSIppj2u7cIuCT37wXD5M>
   [git-repo-url]: <https://github.com/joemccann/dillinger.git>
   [john gruber]: <http://daringfireball.net>
   [@thomasfuchs]: <http://twitter.com/thomasfuchs>
   [df1]: <http://daringfireball.net/projects/markdown/>
   [markdown-it]: <https://github.com/markdown-it/markdown-it>
   [Ace Editor]: <http://ace.ajax.org>
   [node.js]: <http://nodejs.org>
   [Twitter Bootstrap]: <http://twitter.github.com/bootstrap/>
   [keymaster.js]: <https://github.com/madrobby/keymaster>
   [jQuery]: <http://jquery.com>
   [@tjholowaychuk]: <http://twitter.com/tjholowaychuk>
   [express]: <http://expressjs.com>
   [AngularJS]: <http://angularjs.org>
   [Gulp]: <http://gulpjs.com>

   [PlDb]: <https://github.com/joemccann/dillinger/tree/master/plugins/dropbox/README.md>
   [PlGh]:  <https://github.com/joemccann/dillinger/tree/master/plugins/github/README.md>
   [PlGd]: <https://github.com/joemccann/dillinger/tree/master/plugins/googledrive/README.md>
   [PlOd]: <https://github.com/joemccann/dillinger/tree/master/plugins/onedrive/README.md>
