package cz.muni.pa165.banking.application.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.pa165.banking.application.service.ProcessHandlerService;
import cz.muni.pa165.banking.domain.messaging.ProcessRequest;
import org.springframework.stereotype.Service;

@Service
public class ProcessListener {
    
    private final ObjectMapper objectMapper;
    
    private final ProcessHandlerService processHandlerService;

    public ProcessListener(ObjectMapper objectMapper, ProcessHandlerService processHandlerService) {
        this.objectMapper = objectMapper;
        this.processHandlerService = processHandlerService;
    }

    public void onReceived(String message) throws JsonProcessingException {
        System.out.println(message);
        
        ProcessRequest request = objectMapper.readValue(message, ProcessRequest.class);
        processHandlerService.handle(request);
    }
    
}
