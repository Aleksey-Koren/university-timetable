package com.foxminded.koren.university;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@ComponentScan("com.foxminded.koren.university")
@PropertySource("classpath:application.properties")
public class SpringConfigT {
//
//    @Autowired
//    private Environment env;
//
//    @Bean
//    public String tablesCreationUrl() {
//        return env.getProperty("tables.creation.url");
//    }
//
//    @Bean
//    public DataSource dataSource() throws PropertyVetoException {
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        dataSource.setDriverClass(env.getProperty("db.driverClassName"));
//        dataSource.setJdbcUrl(env.getProperty("db.url"));
//        dataSource.setUser(env.getProperty("db.username"));
//        dataSource.setPassword(env.getProperty("db.password"));
//        dataSource.setInitialPoolSize(5);
//        dataSource.setMaxPoolSize(20);
//        return dataSource;
//    }
//
//    @Bean
//    Properties additionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
//        properties.setProperty("hibernate.format_sql", "true");
//        properties.setProperty("hibernate.use_sql_comments", "true");
//        return properties;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException, PropertyVetoException {
//        LocalContainerEntityManagerFactoryBean entityManagerFactory
//                = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactory.setDataSource(dataSource());
//        entityManagerFactory.setPackagesToScan(new String[] { "com.foxminded.koren.university.entity" });
//
//        JpaVendorAdapter hibernateVendorAdapter = new HibernateJpaVendorAdapter();
//        entityManagerFactory.setJpaVendorAdapter(hibernateVendorAdapter);
//        entityManagerFactory.setJpaProperties(additionalProperties());
//        return entityManagerFactory;
//    }
}