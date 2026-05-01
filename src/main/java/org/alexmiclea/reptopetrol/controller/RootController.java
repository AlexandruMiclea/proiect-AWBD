package org.alexmiclea.reptopetrol.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.user.UserAuthenticationDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RootController {

    // TODO check if the user is logged in already - if yes, send to the main page
    // if not, send to the auth page
    @GetMapping
    public String getMain(Model model) {
//        log.info("GET / called");
        model.addAttribute("userAuthenticationDto", new UserAuthenticationDto());
        return "auth/auth";
    }
}
