package com.foresys.app1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.foresys")
public class App1Application {

    public static void main(String[] args) {
        SpringApplication.run(App1Application.class, args);
    }

}
