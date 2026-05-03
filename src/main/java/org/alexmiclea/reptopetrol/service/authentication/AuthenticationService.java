package org.alexmiclea.reptopetrol.service.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.user.TokenResponseDto;
import org.alexmiclea.reptopetrol.dto.user.UserAuthenticationDto;
import org.alexmiclea.reptopetrol.dto.user.UserCreationDto;
import org.alexmiclea.reptopetrol.model.authentication.Token;
import org.alexmiclea.reptopetrol.model.user.User;
import org.alexmiclea.reptopetrol.repository.authentication.TokenRepository;
import org.alexmiclea.reptopetrol.repository.authentication.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // ======================== User methods ========================

    public Optional<User> getUserById(UUID uuid) {
        if (userRepository.existsById(uuid)) {
            return Optional.of(userRepository.findById(uuid).orElseThrow());
        } else {
            return Optional.empty();
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<TokenResponseDto> registerUser(UserCreationDto userCreationDto) {

        // validate that the password inputs are equal
        // TODO how do I return an error that is rendered in thymeleaf?

        if (!userCreationDto.getPassword().equals(userCreationDto.getConfirmPassword())) {
            return Optional.empty();
        }

         User user = User.builder()
            .id(UUID.randomUUID())
            .firstname(userCreationDto.getFirstName())
            .lastname(userCreationDto.getLastName())
            .email(userCreationDto.getEmail())
            .password(passwordEncoder.encode(userCreationDto.getPassword()))
            .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);
        return Optional.of(TokenResponseDto.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                .build());
    }

    public Optional<TokenResponseDto> authenticateUser(UserAuthenticationDto userAuthenticationDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthenticationDto.getEmail(),
                        userAuthenticationDto.getPassword()
                )
        );
        User user = userRepository.findByEmail(userAuthenticationDto.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        revokeUserTokens(user);
        saveUserToken(user, jwtToken);

        return Optional.of(TokenResponseDto.builder()
                        .accessToken(jwtToken)
                        .refreshToken(refreshToken)
                .build());
    }

    public Optional<UUID> deleteUser(UUID uuid) {
        if (userRepository.existsById(uuid)) {
            userRepository.deleteById(uuid);
            return Optional.of(uuid);
        } else {
            return Optional.empty();
        }
    }

    // ======================== Token methods ========================

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String refreshToken;
        String userEmail;

        // if we don't have a JWT token on us, return
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        // extract the base64 payload
        refreshToken = authHeader.substring(7);

        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = userRepository.findByEmail(userEmail).orElseThrow();

            // TODO what does this func do?
            if (jwtService.isTokenValid(refreshToken, user)) {
                String accessToken = jwtService.generateToken(user);
                revokeUserTokens(user);
                saveUserToken(user, accessToken);
                TokenResponseDto tokenResponse = TokenResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), tokenResponse);
            }
        }
    }

    // ======================== Helper methods ========================

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .token(jwtToken)
                .user(user)
                .revoked(false)
                .expired(false)
                .build();

        tokenRepository.save(token);
    }

    private void revokeUserTokens(User user) {
        List<Token> validUserTokens = tokenRepository.findAllByUser(user.getId());

        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        tokenRepository.saveAll(validUserTokens);
    }


}
