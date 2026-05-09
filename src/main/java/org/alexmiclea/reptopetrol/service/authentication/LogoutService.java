package org.alexmiclea.reptopetrol.service.authentication;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.alexmiclea.reptopetrol.dto.user.TokenResponseDto;
import org.alexmiclea.reptopetrol.model.authentication.Token;
import org.alexmiclea.reptopetrol.repository.authentication.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    // TODO refactor so that you get the token from the coookies
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        Cookie jwtCookie = Arrays.stream(request.getCookies()).filter(cookie -> "jwt".equals(cookie.getName())).findAny().orElse(null);
        if (jwtCookie == null) {
            return;
        }

        String jwt = jwtCookie.getValue();
        Optional<Token> storedToken = tokenRepository.findByToken(jwt);

        if (storedToken.isPresent()) {
            Token retrievedToken = storedToken.get();
            retrievedToken.setExpired(true);
            retrievedToken.setRevoked(true);

            tokenRepository.save(retrievedToken);

            SecurityContextHolder.clearContext();

            Cookie newCookie = new Cookie("jwt", null);
            newCookie.setMaxAge(0);
            response.addCookie(newCookie);
        }
    }
}
