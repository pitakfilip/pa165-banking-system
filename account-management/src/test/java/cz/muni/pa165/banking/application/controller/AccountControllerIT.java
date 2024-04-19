package cz.muni.pa165.banking.application.controller;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.muni.pa165.banking.application.facade.AccountFacade;
import cz.muni.pa165.banking.application.facade.UserFacade;
import cz.muni.pa165.banking.application.mapper.DtoMapperImpl;
import cz.muni.pa165.banking.application.service.AccountService;
import cz.muni.pa165.banking.application.service.UserService;
import cz.muni.pa165.banking.domain.account.repository.AccountRepository;
import cz.muni.pa165.banking.domain.scheduled.repository.ScheduledPaymentRepository;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
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
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ScheduledPaymentRepository scheduledPaymentRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountFacade accountFacade;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFacade userFacade;

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
        public AccountService accountService(AccountRepository aRepo, ScheduledPaymentRepository sRepo) {
            return new AccountService(aRepo, sRepo);
        }

        @Bean
        public AccountFacade accountFacade(AccountService service) {
            return new AccountFacade(service, new DtoMapperImpl());
        }

        @Bean
        public UserService userService(UserRepository uRepo) {
            return new UserService(uRepo);
        }

        @Bean
        public UserFacade userFacade(UserService service) {
            return new UserFacade(service, new DtoMapperImpl());
        }

        @Bean
        public CustomExceptionHandler restApiExceptionHandler(AccountService service) {
            return new CustomExceptionHandler();
        }

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
