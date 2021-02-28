package it.geosolutions.mapstore.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class JDBCConfig {
    public DataSource postgresqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/geostore");
        dataSource.setUsername("geostore");
        dataSource.setPassword("geostore");

        return dataSource;
    }
}
