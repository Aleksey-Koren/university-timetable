package com.foxminded.koren.university.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@ComponentScan("com.foxminded.koren.university")
@PropertySource("classpath:application.properties")
public class SpringConfig {

    private Environment env;

    @Autowired
    public SpringConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() throws NamingException {
        InitialContext context = new InitialContext();
        DataSource result = (DataSource) context.lookup("java:/comp/env/jdbc/datasource");
        return result;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() throws PropertyVetoException, NamingException {
        return new JdbcTemplate(dataSource());
    }
    
    @Bean
    public Logger rootLogger() {
        return LoggerFactory.getLogger("root");
    }

    @Bean
    public String tablesCreationUrl() {
        return env.getProperty("tables.creation.url");
    }
}