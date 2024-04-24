package cz.muni.pa165.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProcessorApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(ProcessorApplication.class);
    }

}