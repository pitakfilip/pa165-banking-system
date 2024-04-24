package cz.muni.pa165.banking.application.controller;


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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void initDb(
            @Autowired UserRepository userRepository
    ) {
        User user = new User();
        user.setId(1L);
        user.setEmail("email@example.org");
        user.setPassword("pass123");
        user.setFirstName("Jozko");
        user.setLastName("Mrkvicka");
        userRepository.save(user);
    }

    @Test
    void createUser_ReturnsCreated() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"123@email.cz\",\"password\":\"passwd123\",\"firstName\":\"Joe\", \"lastName\": \"Mama\", \"userType\": \"REGULAR\"}"))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void findUserById_UserFound_ReturnsOk() throws Exception {
        mockMvc.perform(get("/user?userId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void findUserById_UserNotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/user?userId=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }
}
