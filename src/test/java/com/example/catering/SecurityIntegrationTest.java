package com.example.catering;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenAccessingOpenEndpoint_thenNoAuthenticationIsRequired() throws Exception {
        mockMvc.perform(get("/index"))  // Assuming '/index' is an open endpoint in your application
                .andExpect(status().isOk());
    }

    @Test
    public void whenAccessingSecureEndpointWithoutAuthentication_thenRedirectToLogin() throws Exception {
        mockMvc.perform(get("/main"))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"));
    }

    @Test
    public void whenAccessingSecureEndpointWithAuthentication_thenOk() throws Exception {
        mockMvc.perform(get("/main")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("test@test.com:test".getBytes())))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/login"))
                .andReturn();

        mockMvc.perform(get("/login")
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString("test@test.com:test".getBytes())))
                .andExpect(status().isOk());
    }

}
