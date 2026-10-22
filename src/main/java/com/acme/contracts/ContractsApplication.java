package com.acme.contracts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class ContractsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ContractsApplication.class, args);
    }
}
