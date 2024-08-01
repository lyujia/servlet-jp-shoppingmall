package com.nhnacademy.nhnpage;

import com.nhnacademy.nhnpage.controller.LoginController;
import com.nhnacademy.nhnpage.domain.Admin;
import com.nhnacademy.nhnpage.domain.Person;
import com.nhnacademy.nhnpage.domain.User;
import com.nhnacademy.nhnpage.exception.IdPassNotCorrectException;
import com.nhnacademy.nhnpage.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.http.Cookie;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoginController.class)
@ContextConfiguration(classes = {PersonRepository.class})
public class LoginControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private PersonRepository personRepository;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void testLoginPage() throws Exception {
        mockMvc.perform(get("/cs/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
    }

    @Test
    void testLoginSuccessForAdmin() throws Exception {
        Person admin = new Admin("admin", "password", "AdminName");
        when(personRepository.matches("admin", "password")).thenReturn(admin);

        mockMvc.perform(post("/cs/login")
                        .param("id", "admin")
                        .param("pwd", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/admin"))
                .andExpect(cookie().value("SESSIONNHN", notNullValue()))
                .andExpect(request().sessionAttribute("id", "admin"));
    }

    @Test
    void testLoginSuccessForUser() throws Exception {
        Person user = new User("user", "password", "UserName");
        when(personRepository.matches("user", "password")).thenReturn(user);

        mockMvc.perform(post("/cs/login")
                        .param("id", "user")
                        .param("pwd", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/"))
                .andExpect(cookie().value("SESSIONNHN", notNullValue()))
                .andExpect(request().sessionAttribute("id", "user"));
    }

    @Test
    void testLoginFailure() throws Exception {
        when(personRepository.matches("user", "wrongpassword")).thenReturn(null);

        mockMvc.perform(post("/cs/login")
                        .param("id", "user")
                        .param("pwd", "wrongpassword"))
                .andExpect(status().is4xxClientError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdPassNotCorrectException));
    }
}
