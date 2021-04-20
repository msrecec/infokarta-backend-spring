package it.geosolutions.mapstore.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * TODO - Add @Configuration i @Bean for configuration of JDBC data source in our project
 *
 * TODO - Add external xml configuration for Tomcat server
 *
 */

public interface JDBCConfig {
    static DataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://213.191.153.249:5432/PRIMOSTEN");
        dataSource.setUsername("geostore");
        dataSource.setPassword("geostore");

        return dataSource;
    }
    public void setDataSource(DataSource ds);
}
