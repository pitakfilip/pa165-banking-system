package cz.muni.pa165.banking.application.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.messaging.ProcessRequest;
import cz.muni.pa165.banking.exception.ServerError;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProcessProducer implements MessageProducer {
    
    private final MessagingService messagingService;
    
    private final ObjectMapper mapper;
    
    public ProcessProducer(MessagingService messagingService, ObjectMapper mapper) {
        this.messagingService = messagingService;
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
        messagingService.addToQueue(dataAsJsonString);
    }
    
}
