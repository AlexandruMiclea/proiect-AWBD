package org.alexmiclea.reptopetrol.service.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alexmiclea.reptopetrol.dto.user.TokenResponseDto;
import org.alexmiclea.reptopetrol.dto.user.UserAuthenticationDto;
import org.alexmiclea.reptopetrol.dto.user.UserCreationDto;
import org.alexmiclea.reptopetrol.model.authentication.Token;
import org.alexmiclea.reptopetrol.model.user.Role;
import org.alexmiclea.reptopetrol.model.user.User;
import org.alexmiclea.reptopetrol.repository.authentication.TokenRepository;
import org.alexmiclea.reptopetrol.repository.authentication.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
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

    public List<String> validateUserCreationDto(UserCreationDto userCreationDto) {
        List<String> errors = new ArrayList<>();

        // validate that the password inputs are equal
        if (!userCreationDto.getPassword().equals(userCreationDto.getConfirmPassword())) {
            errors.add("Passwords are not matching!");
        }

        // validate that the email is not used by another user
        if (userRepository.findByEmail(userCreationDto.getEmail()).isPresent()) {
            errors.add("Email is already used by another account!");
        }

        return errors;
    }

    public List<String> validateUserAuthenticationDto(UserAuthenticationDto userAuthenticationDto) {
        List<String> errors = new ArrayList<>();

        // validate that the email is used by any user
        if (userRepository.findByEmail(userAuthenticationDto.getEmail()).isEmpty()) {
            errors.add("Email not used by any account!");
            return errors;
        }

        // Check that the password in the form is good enough to authenticate user
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userAuthenticationDto.getEmail(),
                        userAuthenticationDto.getPassword()
                )
            );
        } catch (Exception e) {
            errors.add("Entered password does not match account password!");
            return errors;
        }

        return errors;
    }

    public TokenResponseDto registerUser(UserCreationDto userCreationDto) {

         User user = User.builder()
            .firstname(userCreationDto.getFirstName())
            .lastname(userCreationDto.getLastName())
            .email(userCreationDto.getEmail())
            .password(passwordEncoder.encode(userCreationDto.getPassword()))
            .role(Role.UNASSIGNED)
            .build();

        User savedUser = userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(savedUser, jwtToken);

        return TokenResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenResponseDto authenticateUser(UserAuthenticationDto userAuthenticationDto) {

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

        return TokenResponseDto.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                .build();
    }

    public List<String> updateUserRole(String email, Role role) {
        // TODO check that the user with given email exists
        // TODO update role
        List<String> errors = new ArrayList<>();

        // validate that the email is used by any user
        if (userRepository.findByEmail(email).isEmpty()) {
            errors.add("Email not used by any account!");
            return errors;
        }

        User user = userRepository.findByEmail(email).orElseThrow();
        user.setRole(role);
        userRepository.save(user);

        return errors;
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

            // VIEW what does this func do?
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
        log.debug("Tokens: {}", validUserTokens);

        if (validUserTokens.isEmpty()) return;

        tokenRepository.deleteAll(validUserTokens);
    }


}
