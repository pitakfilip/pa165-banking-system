package cz.muni.pa165.banking.application.service;

import cz.muni.pa165.banking.domain.messaging.ProcessRequest;
import cz.muni.pa165.banking.domain.money.CurrencyConverter;
import cz.muni.pa165.banking.domain.money.exchange.ExchangeRateService;
import cz.muni.pa165.banking.domain.process.handler.ProcessHandlerGateway;
import cz.muni.pa165.banking.domain.process.repository.HandlerMBeanRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.domain.remote.AccountService;
import org.springframework.stereotype.Service;

@Service
public class ProcessHandlerService {

    private final ProcessRepository processRepository;
    
    private final AccountService accountService;

    private final ProcessTransactionRepository processTransactionRepository;

    private final ExchangeRateService exchangeRateApi;
    

    public ProcessHandlerService(ProcessRepository processRepository, AccountService accountService, ProcessTransactionRepository processTransactionRepository, ExchangeRateService exchangeRateApi) {
        this.processRepository = processRepository;
        this.accountService = accountService;
        this.processTransactionRepository = processTransactionRepository;
        this.exchangeRateApi = exchangeRateApi;
    }

    public void handle(ProcessRequest request) {
        CurrencyConverter converter =  new CurrencyConverter(exchangeRateApi);
        HandlerMBeanRepository mBeans = new HandlerMBeanRepository(accountService, processTransactionRepository, converter);

        ProcessHandlerGateway gateway = new ProcessHandlerGateway();
        gateway.handle(request.uuid(), request.type(), processRepository, mBeans);
    }
    
}
