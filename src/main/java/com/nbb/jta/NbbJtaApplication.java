package com.nbb.jta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class NbbJtaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NbbJtaApplication.class, args);
    }

}
