package cz.muni.pa165.banking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyServiceClient{

    private static final Logger log = LoggerFactory.getLogger(MyServiceClient.class);

    public static void main(String[] args) {
        SpringApplication.run(MyServiceClient.class, args);
    }

}