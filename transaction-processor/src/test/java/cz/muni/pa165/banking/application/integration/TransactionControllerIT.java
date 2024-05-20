package cz.muni.pa165.banking.application.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.muni.pa165.banking.account.management.AccountApi;
import cz.muni.pa165.banking.account.management.dto.AccountDto;
import cz.muni.pa165.banking.account.query.CustomerServiceApi;
import cz.muni.pa165.banking.account.query.SystemServiceApi;
import cz.muni.pa165.banking.account.query.dto.TransactionType;
import cz.muni.pa165.banking.application.proxy.rate.ExchangeRatesApi;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.messaging.MessageProducer;
import cz.muni.pa165.banking.domain.money.Money;
import cz.muni.pa165.banking.domain.process.Process;
import cz.muni.pa165.banking.domain.process.ProcessFactory;
import cz.muni.pa165.banking.domain.process.repository.ProcessRepository;
import cz.muni.pa165.banking.domain.process.repository.ProcessTransactionRepository;
import cz.muni.pa165.banking.domain.transaction.Transaction;
import cz.muni.pa165.banking.transaction.processor.dto.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataJpa
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class TransactionControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExchangeRatesApi apiMock;

    @MockBean
    private SecurityFilterChain securityFilterChainMock;

    @MockBean
    private AccountApi accountApi;

    @MockBean
    private CustomerServiceApi publicBalanceApi;

    @MockBean
    private SystemServiceApi privateBalanceApi;

    private static Process createdProcess;
    
    @BeforeAll
    static void initDb(@Autowired ProcessRepository repository, @Autowired ProcessTransactionRepository transactionRepository) {
        Account a1 = new Account("acc1");
        Account a2 = new Account("acc2");
        Money money = new Money(BigDecimal.ONE, Currency.getInstance("CZK"));
        Transaction transaction = new Transaction(a1, a2, cz.muni.pa165.banking.domain.transaction.TransactionType.TRANSFER, money, "transfer 1czk");

        MessageProducer producer = mock(MessageProducer.class);
        createdProcess = new ProcessFactory(transactionRepository, repository).create(transaction, producer);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    public void processTransferBetweenAccounts() throws Exception {
        Account source = new Account("acc_source");
        Account target = new Account("acc_target");

        when(accountApi.findByAccountNumber(source.getAccountNumber())).thenReturn(ResponseEntity.ok(new AccountDto().number("acc_source").currency("CZK")));
        when(accountApi.findByAccountNumber(target.getAccountNumber())).thenReturn(ResponseEntity.ok(new AccountDto().number("acc_target").currency("CZK")));
        when(publicBalanceApi.getBalance(source.getAccountNumber())).thenReturn(ResponseEntity.ok(BigDecimal.valueOf(1000)));


        String requestBody = objectMapper.writeValueAsString(new TransactionDto()
                .source(new cz.muni.pa165.banking.transaction.processor.dto.AccountDto().accountNumber("acc_source"))
                .target(new cz.muni.pa165.banking.transaction.processor.dto.AccountDto().accountNumber("acc_target"))
                .type(TransactionTypeDto.TRANSFER)
                .amount(new MoneyDto().amount(BigDecimal.valueOf(100)).currency("CZK"))
                .detail("TRANSFER TEST"));

        MvcResult response = mockMvc.perform(
                        put("/transaction/v1/process")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();

        String jsonResponse = response.getResponse().getContentAsString();
        ProcessDto process = objectMapper.readValue(jsonResponse, ProcessDto.class);
        
        assertEquals(StatusDto.CREATED, process.getStatus());
        
        verify(privateBalanceApi).addTransactionToBalance(source.getAccountNumber(), BigDecimal.valueOf(-100), process.getIdentifier(), TransactionType.TRANSFER);
        verify(privateBalanceApi).addTransactionToBalance(target.getAccountNumber(), BigDecimal.valueOf(100), process.getIdentifier(), TransactionType.TRANSFER);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    public void getValidProcessStatus() throws Exception {
        MvcResult response = mockMvc.perform(
                        get("/transaction/v1/status")
                                .header("x-process-uuid", createdProcess.getUuid())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        
        String jsonResponse = response.getResponse().getContentAsString();
        ProcessDetailDto detail = objectMapper.readValue(jsonResponse, ProcessDetailDto.class);
        
        assertEquals(createdProcess.getUuid(), detail.getIdentifier());
        assertEquals(StatusDto.CREATED, detail.getStatus());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    public void getNonexistentProcessStatus() throws Exception {
        MvcResult response = mockMvc.perform(
                        get("/transaction/v1/status")
                                .header("x-process-uuid", UUID.randomUUID())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andReturn();
        
        String jsonResponse = response.getResponse().getContentAsString();
        LinkedHashMap JSON = objectMapper.readValue(jsonResponse, LinkedHashMap.class);

        assertTrue(JSON.containsKey("status"));
        assertEquals("NOT_FOUND", JSON.get("status"));
        assertTrue(JSON.containsKey("message"));
        assertEquals("Entity not present in repository", JSON.get("message"));
    }

}
