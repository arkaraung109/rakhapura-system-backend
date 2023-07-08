package com.pearlyadana.rakhapuraapp.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@PropertySource("classpath:database.properties")
@Configuration
public class JDBCDataSourceConfig {

    private @Value("${spring.datasource.url}") String dbUrl;
    private @Value("${spring.datasource.username}") String dbUsername;
    private @Value("${spring.datasource.password}") String dbPassword;
    private @Value("${spring.datasource.driver-class-name}") String dbDriverclass;

    @Bean(name = "datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        dataSource.setDriverClassName(dbDriverclass);
        Properties prop=new Properties();
        prop.setProperty("useUnicode","true");
        prop.setProperty("characterEncoding","utf8");
        dataSource.setConnectionProperties(prop);
        return dataSource;
    }

    //step 2
    @Bean(name = "namedJdbcTemplate")
    public NamedParameterJdbcTemplate namedJdbcTemplate(@Qualifier("datasource") DataSource ds) {
        return new NamedParameterJdbcTemplate(ds);
    }

    //step 2
    @Bean(name = "jdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("datasource") DataSource ds) {
        return new JdbcTemplate(ds);
    }

}
