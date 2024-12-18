package com.malinouski.multitenant_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultitenantAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultitenantAppApplication.class, args);
        System.out.println("Application successfully started!");
    }

}
