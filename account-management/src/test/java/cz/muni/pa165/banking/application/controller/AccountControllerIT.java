package cz.muni.pa165.banking.application.controller;


import cz.muni.pa165.banking.account.query.SystemServiceApi;
import cz.muni.pa165.banking.domain.account.Account;
import cz.muni.pa165.banking.domain.account.AccountType;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Currency;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AccountControllerIT {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SystemServiceApi balanceApi;


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
        account.setAccountNumber("abc");
        account.setType(AccountType.CREDIT);
        account.setCurrency(Currency.getInstance("CZK"));
        accountRepository.save(account);

        Account account2 = new Account();
        account2.setId(2L);
        account2.setUserId(2L);
        account2.setAccountNumber("abcd");
        account2.setType(AccountType.CREDIT);
        account2.setCurrency(Currency.getInstance("CZK"));
        accountRepository.save(account2);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void createAccount_UserExists_ReturnsCreated() throws Exception {
        when(balanceApi.createBalance(anyString())).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"type\":\"SAVING\",\"maxSpendingLimit\":1000, \"currency\": \"CZK\"}"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

        verify(balanceApi).createBalance(anyString());
    }


    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void createAccount_UserNotExist_ReturnsNotFound() throws Exception {
        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":3,\"type\":\"SAVING\",\"maxSpendingLimit\":1000, \"currency\": \"CZK\"}"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void findAccountById_AccountFound_ReturnsOk() throws Exception {
        mockMvc.perform(get("/account?accountId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void findAccountById_AccountNotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/account?accountId=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }


    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void findAccountByAccountNumber_AccountFound_ReturnsOk() throws Exception {
        mockMvc.perform(get("/account/number?accountNumber=abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void findAccountByAccountNumber_AccountNotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/account/number?accountNumber=abcde")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void getScheduledPayments_AccountFound_ReturnsOk() throws Exception {
        mockMvc.perform(get("/account/scheduled?accountNumber=abc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void getScheduledPayments_AccountNotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/account/scheduled?accountNumber=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void schedulePayment_AccountFound_ReturnsCreated() throws Exception {
        mockMvc.perform(post("/account/scheduled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"senderAccount\":\"abc\", \"receiverAccount\":\"abcd\", \"amount\":1, \"type\":\"WEEKLY\", \"day\":1}"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void schedulePayment_AccountNotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(post("/account/scheduled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"senderAccount\":\"abc\", \"receiverAccount\":\"abcde\", \"amount\":1, \"type\":\"WEEKLY\", \"day\":1}"))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }
}
