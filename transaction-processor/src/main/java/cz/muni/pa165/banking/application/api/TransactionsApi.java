package cz.muni.pa165.banking.application.api;

import cz.muni.pa165.banking.application.facade.TransactionFacade;
import cz.muni.pa165.banking.dto.DtoTransactionRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
// controller would be extending an interface generated from OpenApi, TODO create API definition and generate
public class TransactionsApi {
    
    private final TransactionFacade facade;

    public TransactionsApi(TransactionFacade facade) {
        this.facade = facade;
    }

    @PutMapping("/new")
    public ResponseEntity createNew(@RequestBody DtoTransactionRequest request) {
        facade.createNewProcess(request);
        
        return new ResponseEntity(HttpStatusCode.valueOf(200));
    }
    
    
}
