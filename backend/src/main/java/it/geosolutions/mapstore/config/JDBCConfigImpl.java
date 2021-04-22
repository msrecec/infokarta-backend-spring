package it.geosolutions.mapstore.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JDBCConfigImpl implements JDBCConfig{
    private JdbcTemplate jdbcTemplateObject;
    private DataSource ds;

    JDBCConfigImpl() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://213.191.153.249:5432/PRIMOSTEN");
        dataSource.setUsername("geostore");
        dataSource.setPassword("geostore");

        this.jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    @Override
    public void setDataSource(DataSource ds) {
        this.ds = ds;
    }

    public JdbcTemplate getJdbcTemplateObject() {
        return jdbcTemplateObject;
    }

    public void setJdbcTemplateObject(JdbcTemplate jdbcTemplateObject) {
        this.jdbcTemplateObject = jdbcTemplateObject;
    }

    public DataSource getDs() {
        return ds;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }
}
