package cz.muni.pa165.banking.application.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.messaging.ProcessRequest;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.exception.ServerError;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ProcessProducer implements MessageProducer {
    
    private final MessagingService messagingService;
    
    private final ObjectMapper mapper;
    
    public ProcessProducer(MessagingService messagingService, ObjectMapper mapper) {
        this.messagingService = messagingService;
        this.mapper = mapper;
    }

    @PostConstruct
    void testing() {
        ProcessRequest data = new ProcessRequest(UUID.randomUUID(), TransactionType.DEPOSIT);
        send(data);
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
