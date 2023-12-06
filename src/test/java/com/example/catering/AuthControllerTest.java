package com.example.catering;

import com.example.catering.controller.AuthController;
import com.example.catering.dto.UserDto;
import com.example.catering.entity.User;
import com.example.catering.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void registrationWithExistingEmailShouldFail() throws Exception {
        // Arrange
        UserDto existingUserDto = new UserDto();
        existingUserDto.setEmail("existing@example.com");
        Mockito.when(userService.findUserByEmail(existingUserDto.getEmail())).thenReturn(new User());

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/register/save")
                .param("email", existingUserDto.getEmail())
                .param("otherFormFields", "otherValues"));

        // Assert
        result.andExpect(status().isForbidden())
                .andExpect(content().string(""))
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                .andExpect(header().string("X-XSS-Protection", "0"))
                .andExpect(header().string("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate"))
                .andExpect(header().string("Pragma", "no-cache"))
                .andExpect(header().string("Expires", "0"))
                .andExpect(header().string("X-Frame-Options", "DENY"));
    }

    @Test
    public void registrationWithNonExistingEmailShouldSucceed() throws Exception {
        // Arrange
        UserDto newUserDto = new UserDto();
        newUserDto.setEmail("new@example.com");
        Mockito.when(userService.findUserByEmail(newUserDto.getEmail())).thenReturn(null);

        // Act
        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/register/save")
                .param("email", newUserDto.getEmail())
                .param("otherFormFields", "otherValues"));

        // Assert
        result.andExpect(status().isForbidden())
                .andExpect(content().string(""))
                .andExpect(header().string("X-Content-Type-Options", "nosniff"))
                .andExpect(header().string("X-XSS-Protection", "0"))
                .andExpect(header().string("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate"))
                .andExpect(header().string("Pragma", "no-cache"))
                .andExpect(header().string("Expires", "0"))
                .andExpect(header().string("X-Frame-Options", "DENY"));
    }
}
