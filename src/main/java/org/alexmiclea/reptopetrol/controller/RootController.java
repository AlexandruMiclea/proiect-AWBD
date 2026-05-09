package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.user.UserAuthenticationDto;
import org.alexmiclea.reptopetrol.dto.user.UserCreationDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RootController {

    @GetMapping
    public String getMain(Model model) {
        log.debug("GET / called");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            UserAuthenticationDto userAuthenticationDto = new UserAuthenticationDto();
            UserCreationDto userCreationDto = new UserCreationDto();
            model.addAttribute("userAuthenticationDto", userAuthenticationDto);
            model.addAttribute("userCreationDto", userCreationDto);
            return "auth/auth";
        } else {
            return "index/index";
        }
    }
}
