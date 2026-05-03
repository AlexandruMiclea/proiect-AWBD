package org.alexmiclea.reptopetrol.controller.authentication;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.user.TokenResponseDto;
import org.alexmiclea.reptopetrol.dto.user.UserAuthenticationDto;
import org.alexmiclea.reptopetrol.dto.user.UserCreationDto;
import org.alexmiclea.reptopetrol.model.user.User;
import org.alexmiclea.reptopetrol.service.authentication.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    // Register
    // Authenticate
    // TODO assign role endpoint for Admin?

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserCreationDto userCreationDto, HttpServletRequest request) {
        log.info("{}", authenticationService.getAllUsers());
        Optional<TokenResponseDto> response = authenticationService.registerUser(userCreationDto);

        if (response.isPresent()) {
            // TODO redo the redirect path
            // redirect to index page
            return "redirect:/index/index";
        } else {
            return "auth/auth";
        }
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@ModelAttribute UserAuthenticationDto userAuthenticationDto) {
        Optional<TokenResponseDto> response = authenticationService.authenticateUser(userAuthenticationDto);

        if (response.isPresent()){
            return "redirect:/index/index";
        } else {
            return "auth/auth";
        }
    }
}
