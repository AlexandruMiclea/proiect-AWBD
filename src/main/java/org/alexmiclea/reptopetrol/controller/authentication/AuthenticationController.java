package org.alexmiclea.reptopetrol.controller.authentication;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.user.TokenResponseDto;
import org.alexmiclea.reptopetrol.dto.user.UserAuthenticationDto;
import org.alexmiclea.reptopetrol.dto.user.UserCreationDto;
import org.alexmiclea.reptopetrol.service.authentication.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {


    // Register
    // Authenticate
    // TODO assign role endpoint for Admin?



    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> registerUser(@ModelAttribute UserCreationDto userCreationDto) {
        Optional<TokenResponseDto> response = authenticationService.registerUser(userCreationDto);

        if (response.isPresent()) {
            return ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<TokenResponseDto> authenticateUser(@ModelAttribute UserAuthenticationDto userAuthenticationDto) {
        Optional<TokenResponseDto> response = authenticationService.authenticateUser(userAuthenticationDto);

        if (response.isPresent()){
            return  ResponseEntity.ok(response.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
