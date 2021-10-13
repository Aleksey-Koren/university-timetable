package com.foxminded.koren.university;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@SpringBootApplication
@PropertySource("classpath:application.properties")
public class Application {

    private Environment env;

    @Autowired
    public Application(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public Logger rootLogger() {
        return LoggerFactory.getLogger("root");
    }

    @Bean
    @Profile("test")
    public String tablesCreationUrl() {
        return env.getProperty("tables.creation.url");
    }

}
