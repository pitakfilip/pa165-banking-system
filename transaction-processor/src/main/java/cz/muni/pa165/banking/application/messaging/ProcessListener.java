package cz.muni.pa165.banking.application.messaging;

import cz.muni.pa165.banking.application.service.ProcessHandlerService;
import cz.muni.pa165.banking.domain.messaging.ProcessRequest;
import org.springframework.stereotype.Service;

@Service
public class ProcessListener {
    
    private final ProcessHandlerService service;

    public ProcessListener(ProcessHandlerService service) {
        this.service = service;
    }


    // TODO Milestone-2/3, add messaging RabbitMq dependencies
//    @RabbitListener(queues = "${messaging.exchange:process-request}")
    public void onReceived(ProcessRequest message) {
        System.out.println(message);
        service.handle(message);
    }
    
}
