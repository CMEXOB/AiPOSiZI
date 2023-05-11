package com.front;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EntityScan("com.front.entity")
public class FrontApplication {


    public static void main(String[] args) {
        ApplicationContext ctx  = SpringApplication.run(FrontApplication.class, args);
    }

}
