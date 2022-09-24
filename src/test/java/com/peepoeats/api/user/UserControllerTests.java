package com.peepoeats.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peepoeats.api.user.DTO.UserRegistrationRequestDTO;
import com.peepoeats.api.user.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private UserRegistrationRequestDTO userRegistrationRequest;
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    private void setup() {
        userRegistrationRequest = UserRegistrationRequestDTO.builder()
                .firstname("Alex")
                .lastname("Ichenskyi")
                .email("aichenskyi@gmail.com")
                .username("aichenskyi")
                .password("password1")
                .role(UserRole.USER)
                .address("Peepo Square")
                .termsaccepted(true)
                .build();
    }

    // returns HTTP status 201 CREATED
    @Test
    public void test_register_returnsCreated() throws Exception {
        mockMvc.perform(post("/user/register")
                        .content(mapper.writeValueAsString(userRegistrationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    // returns HTTP status 400 BAD REQUEST along with throwing MethodArgumentNotValidException
    @Test
    public void test_register_throwsMethodArgumentNotValidException() throws Exception {
        userRegistrationRequest.setPassword("password");

        mockMvc.perform(post("/user/register")
                        .content(mapper.writeValueAsString(userRegistrationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof
                        MethodArgumentNotValidException));
    }

    // returns HTTP status 409 CONFLICT along with throwing UserAlreadyExistsException
    @Test
    public void test_register_throwsUserAlreadyExistsException() throws Exception {
        mockMvc.perform(post("/user/register")
                        .content(mapper.writeValueAsString(userRegistrationRequest))
                        .contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(post("/user/register")
                        .content(mapper.writeValueAsString(userRegistrationRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof
                        UserAlreadyExistsException));
    }

}
