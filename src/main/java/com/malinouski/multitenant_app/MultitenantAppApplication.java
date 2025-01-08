package com.malinouski.multitenant_app;

import com.malinouski.multitenant_app.util.BitcoinPriceChecker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MultitenantAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultitenantAppApplication.class, args);
        System.out.println("Application successfully started!");
    }

    // Used only for test purpose of a destination service
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            BitcoinPriceChecker checker = ctx.getBean(BitcoinPriceChecker.class);
            checker.checkBtcPrice();
        };
    }

}
