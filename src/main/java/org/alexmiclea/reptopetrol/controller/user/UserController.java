package org.alexmiclea.reptopetrol.controller.user;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.user.TokenResponseDto;
import org.alexmiclea.reptopetrol.dto.user.UserAuthenticationDto;
import org.alexmiclea.reptopetrol.dto.user.UserCreationDto;
import org.alexmiclea.reptopetrol.service.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

// TODO validate requests

@RestController("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

}
