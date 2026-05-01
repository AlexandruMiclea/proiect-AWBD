package org.alexmiclea.reptopetrol.service.authentication;

import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.user.TokenResponseDto;
import org.alexmiclea.reptopetrol.dto.user.UserAuthenticationDto;
import org.alexmiclea.reptopetrol.dto.user.UserCreationDto;
import org.alexmiclea.reptopetrol.repository.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public Optional<TokenResponseDto> registerAccount(UserCreationDto userCreationDto) {

        // validate that the password inputs are equal
        if (!userCreationDto.getPassword().equals(userCreationDto.getConfirmPassword())) {
            return Optional.empty();
        }

        // TODO continue
        return Optional.empty();
    }

    public Optional<TokenResponseDto> authenticateAccount(UserAuthenticationDto userAuthenticationDto) {

        // TODO continue
        return Optional.empty();
    }
}
