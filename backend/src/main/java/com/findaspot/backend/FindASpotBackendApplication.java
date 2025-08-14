package com.findaspot.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * FindASpot Backend Application
 * yahan par main application class hai jo Spring Boot ko start karta hai
 */
@SpringBootApplication
public class FindASpotBackendApplication {

    // yahan par application ka main method hai
    public static void main(String[] args) {
        SpringApplication.run(FindASpotBackendApplication.class, args);
    }
}