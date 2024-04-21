package cz.muni.pa165.banking.application.controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.AccountType;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import cz.muni.pa165.banking.domain.scheduled.repository.ScheduledPaymentRepository;
import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Currency;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AccountRepository accountRepository;

    
    @BeforeAll
    public static void initDb(
            @Autowired UserRepository userRepository,
            @Autowired AccountRepository accountRepository
    ) {
        User user = new User();
        user.setId(1L);
        user.setEmail("email@example.org");
        user.setPassword("pass123");
        user.setFirstName("Jozko");
        user.setLastName("Mrkvicka");
        userRepository.save(user);

        User user2 = new User();
        user2.setId(2L);
        user2.setEmail("123@eemail.cz");
        user2.setPassword("pass123");
        user2.setFirstName("Jozko");
        user2.setLastName("Mrkvicka");
        userRepository.save(user2);

        Account account = new Account();
        account.setId(1L);
        account.setUserId(1L);
        account.setAccountNumber("1");
        account.setType(AccountType.CREDIT);
        account.setCurrency(Currency.getInstance("CZK"));
        accountRepository.save(account);

        Account account2 = new Account();
        account2.setId(2L);
        account2.setUserId(2L);
        account2.setAccountNumber("2");
        account2.setType(AccountType.CREDIT);
        account2.setCurrency(Currency.getInstance("CZK"));
        accountRepository.save(account2);
    }

    @Test
    void createAccount_userExists_returnsCreated() throws Exception {
        mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"type\":\"SAVING\",\"maxSpendingLimit\":1000, \"currency\": \"CZK\"}"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void createAccount_userNotExist_returnsNotFound() throws Exception {
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":3,\"type\":\"SAVING\",\"maxSpendingLimit\":1000, \"currency\": \"CZK\"}"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void findAccountById_accountFound_returnsOk() throws Exception {
        mockMvc.perform(get("/account?accountId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void findAccountById_accountNotFound_returnsNotFound() throws Exception {
        mockMvc.perform(get("/account?accountId=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void getScheduledPayments_accountFound_returnsOk() throws Exception {
        mockMvc.perform(get("/account/scheduled?accountNumber=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void getScheduledPayments_accountNotFound_returnsNotFound() throws Exception {
        mockMvc.perform(get("/account/scheduled?accountNumber=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void schedulePayment_accountFound_returnsCreated() throws Exception {
        mockMvc.perform(post("/account/scheduled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"senderAccountId\":1, \"receiverAccountId\":2, \"amount\":1}"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void schedulePayment_accountNotFound_returnsNotFound() throws Exception {
        mockMvc.perform(post("/account/scheduled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"senderAccountId\":1, \"receiverAccountId\":3, \"amount\":1}"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }
}
