# Dynamic DataSource Routing using Spring3 - JDBC

The objective of the project is to route datasource at runtime.This is very useful when there are multiple connections to different databases and in different physical locations.

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
