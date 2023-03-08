package com.github.massimopavoni.restamongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * RESTAmongo API main SpringBoot Application class.
 */
@SpringBootApplication
@ConfigurationPropertiesScan
public class Application {
    /**
     * Main Application static method, calling Spring run.
     *
     * @param args main args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Register properties sources configurer.
     *
     * @return properties configurer object
     *
     * @throws FileNotFoundException if no application properties was found within the RESTAmongo config directory
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() throws FileNotFoundException {
        PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
        File applicationProperties =
                new File(String.format("%s/.config/RESTAmongo/application.properties", System.getProperty("user.home")));
        if (!applicationProperties.exists())
            throw new FileNotFoundException("Make sure to create the application properties file in the correct directory!");
        properties.setLocation(new FileSystemResource(applicationProperties));
        properties.setIgnoreResourceNotFound(false);
        return properties;
    }
}
