package org.alexmiclea.reptopetrol.integration;

import jakarta.servlet.http.Cookie;
import org.alexmiclea.reptopetrol.repository.authentication.TokenRepository;
import org.alexmiclea.reptopetrol.repository.authentication.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests the full user registration and authentication HTTP flow.
 * Unlike the service-level tests, these go through the actual servlet stack
 * (controllers, form binding, cookie handling) against H2.
 */
class UserAuthenticationIntegrationTest extends BaseIntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired UserRepository userRepository;
    @Autowired TokenRepository tokenRepository;

    @BeforeEach
    void cleanAuthTables() {
        tokenRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void registerUser_persistsUserAndToken() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "Test")
                        .param("lastName", "User")
                        .param("email", "test@integration.com")
                        .param("password", "SecurePass1!")
                        .param("confirmPassword", "SecurePass1!"))
                .andExpect(status().isOk());

        assertThat(userRepository.count()).isEqualTo(1);
        assertThat(userRepository.findByEmail("test@integration.com")).isPresent();
        assertThat(tokenRepository.count()).isEqualTo(1);
    }

    @Test
    void authenticateUser_returnsJwtCookieAndRedirects() throws Exception {
        // First register the user so there is someone to authenticate
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "Auth")
                        .param("lastName", "Tester")
                        .param("email", "auth@integration.com")
                        .param("password", "SecurePass1!")
                        .param("confirmPassword", "SecurePass1!"))
                .andExpect(status().isOk());

        // Now authenticate and capture the response
        MvcResult result = mockMvc.perform(post("/api/auth/authenticate")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "auth@integration.com")
                        .param("password", "SecurePass1!"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andReturn();

        Cookie jwtCookie = result.getResponse().getCookie("jwt");
        assertThat(jwtCookie).isNotNull();
        assertThat(jwtCookie.getValue()).isNotBlank();
        assertThat(jwtCookie.isHttpOnly()).isTrue();
    }

    @Test
    void registerUser_withMismatchedPasswords_doesNotPersistUser() throws Exception {
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", "Bad")
                        .param("lastName", "User")
                        .param("email", "bad@integration.com")
                        .param("password", "SecurePass1!")
                        .param("confirmPassword", "DifferentPass2!"))
                .andExpect(status().isOk());

        assertThat(userRepository.count()).isZero();
        assertThat(tokenRepository.count()).isZero();
    }
}
