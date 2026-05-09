package org.alexmiclea.reptopetrol.controller.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.user.TokenResponseDto;
import org.alexmiclea.reptopetrol.dto.user.UserAuthenticationDto;
import org.alexmiclea.reptopetrol.dto.user.UserCreationDto;
import org.alexmiclea.reptopetrol.service.authentication.AuthenticationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    // TODO assign role endpoint for Admin?

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public String registerUser(@Valid UserCreationDto userCreationDto, BindingResult result, Model model) {

        // check for field validation errors - if they exist, redirect to the register page
        if (result.hasErrors()) {
            UserAuthenticationDto userAuthenticationDto = new UserAuthenticationDto();
            model.addAttribute("userAuthenticationDto", userAuthenticationDto);
            model.addAttribute("userCreationDto", userCreationDto);
            model.addAttribute("activeForm","register");
            return "auth/auth";
        }

        List<String> errors = authenticationService.validateUserCreationDto(userCreationDto);
        if (!errors.isEmpty()) {
            UserAuthenticationDto userAuthenticationDto = new UserAuthenticationDto();
            model.addAttribute("userAuthenticationDto", userAuthenticationDto);
            model.addAttribute("userCreationDto", userCreationDto);
            model.addAttribute("activeForm","register");
            model.addAttribute("registerError", errors);
            return "auth/auth";
        }

        // TODO wat to do with the token?
        TokenResponseDto response = authenticationService.registerUser(userCreationDto);

        model.addAttribute("userAuthenticationDto", new UserAuthenticationDto());
        model.addAttribute("userCreationDto", new UserCreationDto());
        model.addAttribute("activeForm","authenticate");
        return "auth/auth";
    }

    @PostMapping("/authenticate")
    public String authenticateUser(@Valid UserAuthenticationDto userAuthenticationDto, BindingResult result, Model model, HttpServletResponse response) {
        log.debug("sal");

        // check for field validation errors - if they exist, redirect to the register page
        if (result.hasErrors()) {
            UserCreationDto userCreationDto = new UserCreationDto();
            model.addAttribute("userAuthenticationDto", userAuthenticationDto);
            model.addAttribute("userCreationDto", userCreationDto);
            model.addAttribute("activeForm","authenticate");
            return "auth/auth";
        }

        List<String> errors = authenticationService.validateUserAuthenticationDto(userAuthenticationDto);
        if (!errors.isEmpty()) {
            UserCreationDto userCreationDto = new UserCreationDto();
            model.addAttribute("userAuthenticationDto", userAuthenticationDto);
            model.addAttribute("userCreationDto", userCreationDto);
            model.addAttribute("activeForm","authenticate");
            model.addAttribute("registerError", errors);
            return "auth/auth";
        }

        TokenResponseDto tokenResponse = authenticationService.authenticateUser(userAuthenticationDto);
        Cookie cookie = new Cookie("jwt", tokenResponse.getAccessToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);

        response.addCookie(cookie);
        return "redirect:/";
    }
}
