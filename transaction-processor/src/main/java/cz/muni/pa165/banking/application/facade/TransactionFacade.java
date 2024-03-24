package cz.muni.pa165.banking.application.facade;

import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.dto.DtoTransactionRequest;
import org.springframework.stereotype.Service;

@Service
public class TransactionFacade {
    
    public void createNewProcess(DtoTransactionRequest request) {
        // Account source = Mapper.map(request.source);
        // Account target = Mapper.map(request.target);
        TransactionType type = TransactionType.valueOf(request.getType().name());
        
        // call service to create process, validate stuff etc. etc.
        
        // check if all validations passed (accounts exist, enough balance on source account, ...)
        
        // call service to start 'sending money' to target account, would contain:
        //      - fetch source account info about currency
        //      - fetch target account info about currency
        //      - if needed, convert money via CurrencyConverter
        //      - process 
        
        // call messaging to push new value finalized process into query module (updates balance of both accounts, saves changes)
    }
    
}
