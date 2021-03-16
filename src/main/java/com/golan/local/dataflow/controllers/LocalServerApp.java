package com.golan.local.dataflow.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "com.golan.local.dataflow")
public class LocalServerApp {

    public static void main(String[] args) {
        SpringApplication.run(LocalServerApp.class, args);
    }
}
