package cz.muni.pa165.banking.domain.messaging;

public interface MessageProducer {
    
    void send(ProcessRequest data);    
    
}
