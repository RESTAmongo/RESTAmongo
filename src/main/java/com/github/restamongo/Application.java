package com.github.restamongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * RESTAmongo API main SpringBoot Application class.
 */
@SpringBootApplication
public class Application {
    /**
     * Main Application static method, calling Spring run.
     *
     * @param args main args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
