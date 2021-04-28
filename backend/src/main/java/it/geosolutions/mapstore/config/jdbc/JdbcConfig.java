package it.geosolutions.mapstore.config.jdbc;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * TODO - Add @Configuration i @Bean for configuration of JDBC data source in our project
 *
 * TODO - Add external xml configuration for Tomcat server
 *
 */

public interface JdbcConfig {
    JdbcTemplate jdbcTemplateObjectFactory();
}
