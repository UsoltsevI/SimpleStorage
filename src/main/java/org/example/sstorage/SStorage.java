package org.example.sstorage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class of the application.
 * Used to start the server.
 *
 * @author UsoltsevI
 */
@SpringBootApplication
public class SStorage {
    /**
     * Start the server.
     *
     * @param args arguments to pass to SpringApplication::run method
     */
    public static void main(String[] args) {
        SpringApplication.run(SStorage.class, args);
    }
}