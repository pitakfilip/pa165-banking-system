package cz.muni.pa165.banking.application.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import cz.muni.pa165.banking.account.query.dto.Transaction;
import cz.muni.pa165.banking.domain.transaction.TransactionType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@AutoConfigureMockMvc
public class BalanceControllerIT {
    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModule(new JavaTimeModule())
            .setPropertyNamingStrategy(new PropertyNamingStrategies.SnakeCaseStrategy())
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);


    @Test
    void createBalance_returnsCreated() throws Exception {
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
    void addToBalance_accountExists_returnsOK() throws Exception {
        // Arrange
        mockMvc.perform(post("/balance/new?id=id"));
        // Act
        String id = "id";
        String responseJson = mockMvc.perform(post("/balance/add?id=id&amount=20&processId=5612b08f-27c2-42ca-9f23-0c9aff6ad877&type=WITHDRAW")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void addToBalance_accountNotExists_returnsNOK() throws Exception {
        // Arrange
        mockMvc.perform(post("/balance/new?id=id"));
        // Act
        String id = "id";
        String responseJson = mockMvc.perform(post("/balance/add?id=badid&amount=20&processId=5612b08f-27c2-42ca-9f23-0c9aff6ad877&type=WITHDRAW")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        // Assert
        assertThat(responseJson).contains("was not found.");
    }

    @Test
    void getStatus_personExists_returnsBalanceStatus() throws Exception {
        // Arrange
        mockMvc.perform(post("/balance/new?id=id"));
        // Act
        String id = "id";
        String responseJson = mockMvc.perform(get("/balance/status?id=id")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        Integer response = Integer.valueOf(responseJson);

        // Assert
        assertThat(response).isEqualTo(0);
    }

    @Test
    void getStatus_personNotExists_returnsBadRequest() throws Exception {
        // Arrange
        mockMvc.perform(post("/balance/new?id=id"));
        // Act
        String id = "id";
        String responseJson = mockMvc.perform(get("/balance/status?id=notexistingid")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        // Assert
        assertThat(responseJson).contains("was not found.");
    }

    @Test
    void getTransactions_personExists_returnsTransactions() throws Exception {
        // Arrange
        mockMvc.perform(post("/balance/new?id=id"));
        mockMvc.perform(post("/balance/add?id=id&amount=20&processId=5612b08f-27c2-42ca-9f23-0c9aff6ad877&type=WITHDRAW"));
        cz.muni.pa165.banking.account.query.dto.Transaction transaction = new Transaction();
        transaction.setDate(OffsetDateTime.now());
        transaction.setAmount(BigDecimal.valueOf(20));
        transaction.setProcessId(UUID.fromString("5612b08f-27c2-42ca-9f23-0c9aff6ad877"));
        transaction.setTransactionType(cz.muni.pa165.banking.account.query.dto.TransactionType.WITHDRAW);
        // Act
        String id = "id";
        String responseJson = mockMvc.perform(get("/balance/transactions?id=id&beginning=2020-02-02&end=2025-02-02")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        cz.muni.pa165.banking.account.query.dto.Transaction[] response = OBJECT_MAPPER.readValue(responseJson, cz.muni.pa165.banking.account.query.dto.Transaction[].class);

        // Assert
        assertThat(response[0].getAmount()).isEqualTo(transaction.getAmount());
        assertThat(response[0].getProcessId()).isEqualTo(transaction.getProcessId());
        assertThat(response[0].getTransactionType()).isEqualTo(transaction.getTransactionType());
    }
    @Test
    void getTransactions_personNotExists_returnsError() throws Exception {
        // Act
        String responseJson = mockMvc.perform(get("/balance/transactions?id=d&beginning=2020-02-02&end=2025-02-02")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        // Assert
        assertThat(responseJson).contains("was not found.");
    }

    @Test
    void getAllTransactions_returnsAllTransactions() throws Exception {
        // Arrange
        mockMvc.perform(post("/balance/new?id=id"));
        mockMvc.perform(post("/balance/add?id=id&amount=20&processId=5612b08f-27c2-42ca-9f23-0c9aff6ad877&type=WITHDRAW"));
        mockMvc.perform(post("/balance/new?id=i"));
        mockMvc.perform(post("/balance/add?id=i&amount=40&processId=5612b08f-27c2-42ca-9f23-0c9aff6ad874&type=WITHDRAW"));
        cz.muni.pa165.banking.account.query.dto.Transaction transaction = new Transaction();
        transaction.setDate(OffsetDateTime.now());
        transaction.setAmount(BigDecimal.valueOf(20));
        transaction.setProcessId(UUID.fromString("5612b08f-27c2-42ca-9f23-0c9aff6ad877"));
        transaction.setTransactionType(cz.muni.pa165.banking.account.query.dto.TransactionType.WITHDRAW);
        cz.muni.pa165.banking.account.query.dto.Transaction transaction2 = new Transaction();
        transaction2.setDate(OffsetDateTime.now());
        transaction2.setAmount(BigDecimal.valueOf(40));
        transaction2.setProcessId(UUID.fromString("5612b08f-27c2-42ca-9f23-0c9aff6ad874"));
        transaction2.setTransactionType(cz.muni.pa165.banking.account.query.dto.TransactionType.WITHDRAW);
        // Act
        String id = "id";
        String responseJson = mockMvc.perform(get("/balance/alltransactions?beginning=2020-02-02&end=2025-02-02")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        cz.muni.pa165.banking.account.query.dto.Transaction[] response = OBJECT_MAPPER.readValue(responseJson, cz.muni.pa165.banking.account.query.dto.Transaction[].class);

        // Assert
        assertThat(response[1].getAmount()).isEqualTo(transaction.getAmount());
        assertThat(response[1].getProcessId()).isEqualTo(transaction.getProcessId());
        assertThat(response[1].getTransactionType()).isEqualTo(transaction.getTransactionType());
        assertThat(response[0].getAmount()).isEqualTo(transaction2.getAmount());
        assertThat(response[0].getProcessId()).isEqualTo(transaction2.getProcessId());
        assertThat(response[0].getTransactionType()).isEqualTo(transaction2.getTransactionType());
    }

    @Test
    void getReport_personExists_returnsReport() throws Exception {
        // Arrange
        mockMvc.perform(post("/balance/new?id=id"));
        mockMvc.perform(post("/balance/add?id=id&amount=20&processId=5612b08f-27c2-42ca-9f23-0c9aff6ad877&type=WITHDRAW"));
        cz.muni.pa165.banking.account.query.dto.Transaction transaction = new Transaction();
        transaction.setDate(OffsetDateTime.now());
        transaction.setAmount(BigDecimal.valueOf(20));
        transaction.setProcessId(UUID.fromString("5612b08f-27c2-42ca-9f23-0c9aff6ad877"));
        transaction.setTransactionType(cz.muni.pa165.banking.account.query.dto.TransactionType.WITHDRAW);
        // Act
        String id = "id";
        String responseJson = mockMvc.perform(get("/balance/account/report?id=id&beginning=2020-02-02&end=2024-05-05")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        cz.muni.pa165.banking.account.query.dto.TransactionsReport response = OBJECT_MAPPER.readValue(responseJson, cz.muni.pa165.banking.account.query.dto.TransactionsReport.class);

        // Assert
        assertThat(response.getWithdrawalAmount().getAmountIn()).isEqualTo(BigDecimal.valueOf(20));
        assertThat(response.getTotalAmount().getTimesIn()).isEqualTo(BigDecimal.ONE);
    }

    @Test
    void getReport_personNotExists_returnsError() throws Exception {
        // Act
        String responseJson = mockMvc.perform(get("/balance/account/report?id=notanaccount&beginning=2020-02-02&end=2024-05-05")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        // Assert
        assertThat(responseJson).contains("was not found.");
    }
}
