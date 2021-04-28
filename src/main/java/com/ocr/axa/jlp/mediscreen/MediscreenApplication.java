package com.ocr.axa.jlp.mediscreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MediscreenApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediscreenApplication.class, args);
    }

}


