package com.example.catering;

import com.example.catering.controller.AuthController;
import com.example.catering.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

@WebMvcTest(AuthController.class)
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private static final String TEST_USERNAME = "testUser";

    @BeforeEach
    public void setup() {
        authenticateTestUser(TEST_USERNAME, "test", "ROLE_ADMIN");
    }

    private void authenticateTestUser(String username, String password, String role) {
        UserDetails testUser = new User(username, password, Collections.singleton(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities())
        );
    }

    @Test
    public void testAuthenticatedUserCanAccessMainPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/main")
                        .with(request -> {
                            request.setRemoteUser(TEST_USERNAME);
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("main"));
    }

    @Test
    public void testAdminCanAccessUsersList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/usersList")
                        .header("Authorization", "Basic " + base64Encode("actualUsername:actualPassword")))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("usersList"));
    }


    @Test
    public void testNonAdminCannotAccessUsersList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/usersList")
                        .with(request -> {
                            request.setRemoteUser(TEST_USERNAME);
                            return request;
                        }))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    private String base64Encode(String value) {
        return java.util.Base64.getEncoder().encodeToString(value.getBytes());
    }
}
