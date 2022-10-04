package com.peepoeats.api.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.peepoeats.api.user.DTO.UserLoginRequestDTO;
import com.peepoeats.api.user.DTO.UserRegistrationRequestDTO;
import com.peepoeats.api.user.exception.UserBadCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserControllerLoginTests {

    @Autowired
    private MockMvc mockMvc;

    private UserLoginRequestDTO userLoginRequest;
    private final ObjectMapper mapper = new ObjectMapper();

    private static boolean setUpIsDone = false;

    @BeforeEach
    private void setupOnce() throws Exception {
        if (setUpIsDone)
            return;

        // register a user
        UserRegistrationRequestDTO userRegistrationRequest = UserRegistrationRequestDTO.builder()
                .firstname("Alex")
                .lastname("Ichenskyi")
                .email("aichenskyi@gmail.com")
                .username("aichenskyi")
                .password("password1")
                .role(UserRole.USER)
                .address("Peepo Square")
                .termsaccepted(true)
                .build();

        mockMvc.perform(post("/user/register")
                .content(mapper.writeValueAsString(userRegistrationRequest))
                .contentType(MediaType.APPLICATION_JSON));

        setUpIsDone = true;
    }

    @BeforeEach
    private void setup() {
        userLoginRequest = UserLoginRequestDTO.builder()
                .email("aichenskyi@gmail.com")
                .password("password1")
                .build();
    }

    // returns HTTP status 202 ACCEPTED
    @Test
    public void test_login_returnsAccepted() throws Exception {
        mockMvc.perform(post("/user/login")
                        .content(mapper.writeValueAsString(userLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    // returns HTTP status 400 BAD REQUEST along with throwing UserBadCredentialsException
    @Test
    public void test_login_throwsUserBadCredentialsException() throws Exception {
        userLoginRequest.setPassword("password3");

        mockMvc.perform(post("/user/login")
                        .content(mapper.writeValueAsString(userLoginRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof
                        UserBadCredentialsException));
    }

}
