package it.geosolutions.mapstore.config.jdbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class JdbcConfigImpl implements JdbcConfig {

    private DriverManagerDataSource dataSource;

    public JdbcConfigImpl() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://213.191.153.249:5432/PRIMOSTEN");
        dataSource.setUsername("geostore");
        dataSource.setPassword("geostore");

        this.dataSource = dataSource;
    }

    @Bean(name = "jdbcTemplateObjectFactory")
    @Override
    public JdbcTemplate jdbcTemplateObjectFactory() {
        return new JdbcTemplate(dataSource);
    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
