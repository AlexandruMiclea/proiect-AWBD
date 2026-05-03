package org.alexmiclea.reptopetrol.service.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.model.authentication.Token;
import org.alexmiclea.reptopetrol.repository.authentication.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        String jwt = authHeader.substring(7);
        Optional<Token> storedToken = tokenRepository.findByToken(jwt);

        if (storedToken.isPresent()) {
            Token retrievedToken = storedToken.get();
            retrievedToken.setExpired(true);
            retrievedToken.setRevoked(true);

            tokenRepository.save(retrievedToken);

            SecurityContextHolder.clearContext();
        }
    }
}
