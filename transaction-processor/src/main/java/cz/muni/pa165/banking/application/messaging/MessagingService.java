package cz.muni.pa165.banking.application.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class MessagingService {

    private final Logger logger = LoggerFactory.getLogger(MessagingService.class);
    
    private final ProcessListener processListener;
    
    private final Queue<String> queue = new ConcurrentLinkedQueue<>();

    public MessagingService(ProcessListener processListener) {
        this.processListener = processListener;
    }

    public void addToQueue(String message) {
        queue.add(message);
        triggerRead();
    }

    @Async
    void triggerRead() {
        String message = queue.poll();
        try {
            processListener.onReceived(message);
        } catch (JsonProcessingException e) {
            logger.error("Error while processing message");
        }
    }

}
