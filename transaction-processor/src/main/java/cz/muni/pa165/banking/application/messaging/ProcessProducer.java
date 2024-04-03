package cz.muni.pa165.banking.application.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.messaging.ProcessRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    public void send(ProcessRequest data) throws JsonProcessingException {
        String dataAsJsonString = mapper.writeValueAsString(data);
//        template.convertAndSend(EXCHANGE_NAME, "", data); 
    }
    
}
