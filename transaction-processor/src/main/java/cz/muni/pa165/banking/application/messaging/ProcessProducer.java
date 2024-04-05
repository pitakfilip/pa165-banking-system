package cz.muni.pa165.banking.application.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.messaging.ProcessRequest;
import cz.muni.pa165.banking.exception.ServerError;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessProducer implements MessageProducer {
    
    // TODO Milestone-2/3, add messaging RabbitMq dependencies
//    private final RabbitTemplate template;
    
    private final ObjectMapper mapper;
    
    @Value("${messaging.exchange:process-request}")
    private String EXCHANGE_NAME;

    public ProcessProducer(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    
    @Override
    public void send(ProcessRequest data) {
        String dataAsJsonString;
        try {
            dataAsJsonString = mapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new ServerError(
                    "Unable to map ProcessRequest to String",
                    Map.of(
                            "causeLocation", "cz.muni.pa165.banking.application.messaging.ProcessProducer.send",
                            "invalidObject", data.toString()
                    )
            );
        }
//        template.convertAndSend(EXCHANGE_NAME, "", dataAsJsonString); 
    }
    
}
