package it.geosolutions.mapstore.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class JDBCConfig {
    public DataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://213.191.153.249:5432/PRIMOSTEN");
        dataSource.setUsername("geostore");
        dataSource.setPassword("geostore");

        return dataSource;
    }
}
