package com.beehive;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ServerStartedMessage implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\nServer start succesfully!!!!");
    }
}
