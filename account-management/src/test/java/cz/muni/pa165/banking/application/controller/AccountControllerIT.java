package cz.muni.pa165.banking.application.controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    
    @Autowired
    private ScheduledPaymentRepository scheduledPaymentRepository;
    
    @BeforeAll
    public static void initDb(
            @Autowired UserRepository userRepository,
            @Autowired AccountRepository accountRepository,
            @Autowired ScheduledPaymentRepository scheduledPaymentRepository
    ) {
        User user = new User();
        user.setId(1L);
        user.setEmail("email@example.org");
        user.setPassword("pass123");
        user.setFirstName("Jozko");
        user.setLastName("Mrkvicka");
        userRepository.save(user);
    }
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModule(new JavaTimeModule())
            .setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);


    @Test
    void createAccount_returnsCreated() throws Exception {
        mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"type\":\"SAVING\",\"maxSpendingLimit\":1000, \"currency\": \"CZK\"}"))
                .andExpect(status().is(201))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }
}
