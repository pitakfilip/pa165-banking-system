package cz.muni.pa165.banking.application.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    void createAccount_returnsCreated() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"test123\",\"firstName\":\"Test\",\"lastName\":\"User\",\"userType\":\"REGULAR\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"type\":\"SAVING\",\"maxSpendingLimit\":1000, \"currency\": \"CZK\"}"))
                .andExpect(status().isCreated());
    }

    /*@Test
    void getAccount_accountExists_returnsAccount() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"test123\",\"firstName\":\"Test\",\"lastName\":\"User\",\"userType\":\"REGULAR\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"type\":\"SAVING\",\"maxSpendingLimit\":1000, \"currency\": \"CZK\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("id", 123456L))
                .andExpect(status().isOk());
    }

    @Test
    void schedulePayment_accountExists_returnsCreated() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"test@test.com\",\"password\":\"test123\",\"firstName\":\"Test\",\"lastName\":\"User\",\"userType\":\"REGULAR\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"type\":\"SAVING\",\"maxSpendingLimit\":1000, \"currency\": \"CZK\"}"))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/account/scheduled")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"senderAccountId\":1,\"receiverAccountId\":2,\"amount\":100}"))
                .andExpect(status().isCreated());
    }*/
}