package cz.muni.pa165.banking.application.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.application.facade.BalanceFacade;
import cz.muni.pa165.banking.application.mapper.BalanceMapperImpl;
import cz.muni.pa165.banking.application.service.BalanceServiceImpl;
import cz.muni.pa165.banking.domain.balance.Balance;
import cz.muni.pa165.banking.domain.balance.repository.BalancesRepository;
import cz.muni.pa165.banking.domain.transaction.repository.TransactionRepository;
import cz.muni.pa165.banking.domain.balance.service.BalanceService;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import cz.muni.pa165.banking.exception.CustomExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BalanceControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BalancesRepository balancesRepository;
    
    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private BalanceService service;

    @Autowired
    private BalanceFacade facade;

    @Autowired
    private CustomExceptionHandler exceptionHandler;
    
    
    // disable connecting to database
    @TestConfiguration
    @SpringBootApplication(exclude = {
            DataSourceAutoConfiguration.class,
            DataSourceTransactionManagerAutoConfiguration.class,
            HibernateJpaAutoConfiguration.class
    })
    static class IntegrationTestConfig {
        
        @Bean 
        public BalanceService service(BalancesRepository bRepo, TransactionRepository tRepo) {
            return new BalanceServiceImpl(bRepo, tRepo);
        }
        
        @Bean
        public BalanceFacade facade(BalanceService service) {
            return new BalanceFacade(service, new BalanceMapperImpl());
        }

        @Bean
        public CustomExceptionHandler restApiExceptionHandler(BalanceService service) {
            return new CustomExceptionHandler();
        }
        
    }
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModule(new JavaTimeModule())
            .setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);


    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void createBalance_returnsCreated_IT() throws Exception {
        // Arrange

        // Act
        String id = "id";
        String responseJson = mockMvc.perform(post("/balance/new?id=id")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void addToBalance_accountExists_returnsOK_IT() throws Exception {
        // Arrange
        String id = "id";
        Balance mockBalance = new Balance(id);
        when(balancesRepository.findById(id)).thenReturn(Optional.of(mockBalance));
        // Act
        String responseJson = mockMvc.perform(post("/balance/add?id=id&amount=20&processId=5612b08f-27c2-42ca-9f23-0c9aff6ad877&type=WITHDRAW")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void addToBalance_accountNotExists_returnsNOK_IT() throws Exception {
        // Arrange
        String id = "badid";
        when(balancesRepository.findById(id)).thenReturn(Optional.empty());
        // Act

        String responseJson = mockMvc.perform(post("/balance/add?id=badid&amount=20&processId=5612b08f-27c2-42ca-9f23-0c9aff6ad877&type=WITHDRAW")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        // Assert
        assertThat(responseJson).contains("was not found.");
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void getStatus_personExists_returnsBalanceStatus_IT() throws Exception {
        // Arrange
        String id = "id";
        Balance mockBalance = new Balance(id);
        when(balancesRepository.findById(id)).thenReturn(Optional.of(mockBalance));
        when(transactionRepository.findByBalance(mockBalance)).thenReturn(mockBalance.getTransactions());
        // Act
        String responseJson = mockMvc.perform(get("/balance/status?id=id")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Double response = Double.valueOf(responseJson);

        // Assert
        assertThat(response).isEqualTo(0);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void getStatus_personNotExists_returnsBadRequest_IT() throws Exception {
        // Arrange
        mockMvc.perform(post("/balance/new?id=id"));
        // Act
        String id = "id";
        String responseJson = mockMvc.perform(get("/balance/status?id=notexistingid")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        // Assert
        assertThat(responseJson).contains("was not found.");
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void getTransactions_personExists_returnsTransactions_IT() throws Exception {
        // Arrange
        String id = "id";
        Balance mockBalance = new Balance(id);
        when(balancesRepository.findById(id)).thenReturn(Optional.of(mockBalance));

        cz.muni.pa165.banking.account.query.dto.Transaction transaction = new Transaction();
        transaction.setDate(OffsetDateTime.now());
        transaction.setAmount(BigDecimal.valueOf(20.00));
        transaction.setProcessId(UUID.fromString("5612b08f-27c2-42ca-9f23-0c9aff6ad877"));
        transaction.setTransactionType(cz.muni.pa165.banking.account.query.dto.TransactionType.WITHDRAW);

        mockBalance.addTransaction(BigDecimal.valueOf(20), TransactionType.WITHDRAW,
                UUID.fromString("5612b08f-27c2-42ca-9f23-0c9aff6ad877"));
        when(balancesRepository.findById(id)).thenReturn(Optional.of(mockBalance));
        when(transactionRepository.findByBalance(mockBalance)).thenReturn(mockBalance.getTransactions());

        // Act
        String responseJson = mockMvc.perform(get("/balance/transactions?id=id&beginning=2020-02-02&end=2035-02-02")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        cz.muni.pa165.banking.account.query.dto.Transaction[] response =
                OBJECT_MAPPER.readValue(responseJson, cz.muni.pa165.banking.account.query.dto.Transaction[].class);

        // Assert
        assertThat(response[0].getAmount().byteValueExact()).isEqualTo(transaction.getAmount().byteValueExact());
        assertThat(response[0].getProcessId()).isEqualTo(transaction.getProcessId());
        assertThat(response[0].getTransactionType()).isEqualTo(transaction.getTransactionType());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void getTransactions_personNotExists_returnsError_IT() throws Exception {
        // Act
        String responseJson = mockMvc.perform(get("/balance/transactions?id=d&beginning=2020-02-02&end=2035-02-02")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        // Assert
        assertThat(responseJson).contains("was not found.");
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void getAllTransactions_returnsAllTransactions_IT() throws Exception {
        // Arrange

        String id1 = "idddd";
        String id2 = "idd";
        Balance mockBalance1 = new Balance(id1);
        Balance mockBalance2  = new Balance(id2);

        cz.muni.pa165.banking.account.query.dto.Transaction transaction = new Transaction();
        transaction.setDate(OffsetDateTime.now());
        transaction.setAmount(BigDecimal.valueOf(20));
        transaction.setProcessId(UUID.fromString("5612b08f-27c2-42ca-9f23-0c9aff6ad847"));
        transaction.setTransactionType(cz.muni.pa165.banking.account.query.dto.TransactionType.WITHDRAW);
        cz.muni.pa165.banking.account.query.dto.Transaction transaction2 = new Transaction();
        transaction2.setDate(OffsetDateTime.now());
        transaction2.setAmount(BigDecimal.valueOf(40));
        transaction2.setProcessId(UUID.fromString("5612b08f-27c2-42ca-9f23-0c9aff64d874"));
        transaction2.setTransactionType(cz.muni.pa165.banking.account.query.dto.TransactionType.WITHDRAW);

        mockBalance1.addTransaction(BigDecimal.valueOf(20), TransactionType.WITHDRAW,
                UUID.fromString("5612b08f-27c2-42ca-9f23-0c9aff6ad847"));
        when(balancesRepository.findById(id1)).thenReturn(Optional.of(mockBalance1));
        mockBalance2.addTransaction(BigDecimal.valueOf(40), TransactionType.WITHDRAW,
                UUID.fromString("5612b08f-27c2-42ca-9f23-0c9aff64d874"));
        when(balancesRepository.findById(id2)).thenReturn(Optional.of(mockBalance2));
        when(transactionRepository.findByBalance(mockBalance1)).thenReturn(mockBalance1.getTransactions());
        when(transactionRepository.findByBalance(mockBalance2)).thenReturn(mockBalance2.getTransactions());
        when(balancesRepository.getAllIds()).thenReturn(List.of(id1, id2));
        // Act
        String id = "id";
        String responseJson = mockMvc.perform(get("/balance/alltransactions?beginning=2020-02-02&end=2035-02-02")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        List<cz.muni.pa165.banking.account.query.dto.Transaction> response = Arrays.stream(OBJECT_MAPPER.readValue(responseJson, Transaction[].class)).toList();
        mockMvc.perform(delete("/balance?id=idddd"));
        mockMvc.perform(delete("/balance?id=idd"));

        // Assert
        assertThat(response.get(1).getAmount()).isEqualTo(transaction2.getAmount());
        assertThat(response.get(1).getTransactionType()).isEqualTo(transaction2.getTransactionType());
        assertThat(response.get(0).getAmount().byteValueExact()).isEqualTo(transaction.getAmount().byteValueExact());
        assertThat(response.get(0).getTransactionType()).isEqualTo(transaction.getTransactionType());
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void getReport_personExists_returnsReport_IT() throws Exception {
        // Arrange
        String id = "iddd";
        Balance mockBalance = new Balance(id);

        cz.muni.pa165.banking.account.query.dto.Transaction transaction = new Transaction();
        transaction.setDate(OffsetDateTime.now());
        transaction.setAmount(BigDecimal.valueOf(20));
        transaction.setProcessId(UUID.fromString("5612b08f-27c2-42ca-9f26-0c9aff6ad877"));
        transaction.setTransactionType(cz.muni.pa165.banking.account.query.dto.TransactionType.WITHDRAW);

        mockBalance.addTransaction(BigDecimal.valueOf(20), TransactionType.WITHDRAW,
                UUID.fromString("5612b08f-27c2-42ca-9f26-0c9aff6ad877"));
        when(balancesRepository.findById(id)).thenReturn(Optional.of(mockBalance));
        when(transactionRepository.findByBalance(mockBalance)).thenReturn(mockBalance.getTransactions());
        // Act
        String responseJson = mockMvc.perform(get("/balance/account/report?id=iddd&beginning=2020-02-02&end=2035-05-05")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        cz.muni.pa165.banking.account.query.dto.TransactionsReport response = OBJECT_MAPPER.readValue(responseJson, cz.muni.pa165.banking.account.query.dto.TransactionsReport.class);
        mockMvc.perform(delete("/balance?id=iddd"));

        // Assert
        assertThat(response.getWithdrawalAmount().getAmountIn().byteValueExact()).isEqualTo(BigDecimal.valueOf(20).byteValueExact());
        assertThat(response.getTotalAmount().getTimesIn()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void getReport_personNotExists_returnsError_IT() throws Exception {
        // Act
        String responseJson = mockMvc.perform(get("/balance/account/report?id=notanaccount&beginning=2020-02-02&end=2035-05-05")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(404))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        // Assert
        assertThat(responseJson).contains("was not found.");
    }
}
