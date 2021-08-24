package com.foxminded.koren.university;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan("com.foxminded.koren.university")
@PropertySource("classpath:application.properties")
public class SpringConfigT /*implements WebMvcConfigurer*/ {
    
    @Autowired
    private Environment env;

    @Bean
    public String tablesCreationUrl() {
        return env.getProperty("tables.creation.url");
    }
}