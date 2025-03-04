package cz.muni.pa165.banking.application.controller;

import cz.muni.pa165.banking.domain.user.User;
import cz.muni.pa165.banking.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
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
    @WithMockUser(authorities = "SCOPE_test_2")
    void createUser_ReturnsCreated() throws Exception {
        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"123@email.cz\",\"password\":\"passwd123\",\"firstName\":\"Joe\", \"lastName\": \"Mama\", \"userType\": \"REGULAR\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("\"email\":\"123@email.cz\"")))
                .andExpect(content().string(containsString("\"firstName\":\"Joe\"")))
                .andExpect(content().string(containsString("\"lastName\":\"Mama\"")));
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void findUserById_UserFound_ReturnsOk() throws Exception {
        mockMvc.perform(get("/user?userId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("\"email\":\"email@example.org\"")))
                .andExpect(content().string(containsString("\"firstName\":\"Jozko\"")))
                .andExpect(content().string(containsString("\"lastName\":\"Mrkvicka\"")));
    }

    @Test
    @WithMockUser(authorities = "SCOPE_test_2")
    void findUserById_UserNotFound_ReturnsNotFound() throws Exception {
        mockMvc.perform(get("/user?userId=3")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("not found")));
    }
}
