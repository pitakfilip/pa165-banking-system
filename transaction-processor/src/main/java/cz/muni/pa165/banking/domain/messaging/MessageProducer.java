package cz.muni.pa165.banking.domain.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface MessageProducer {
    
    void send(ProcessRequest data) throws JsonProcessingException;    
    
}
