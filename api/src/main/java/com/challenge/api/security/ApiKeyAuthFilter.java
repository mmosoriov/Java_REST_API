package com.challenge.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Authenticates incoming requests by validating the {@code X-API-Key} header against
 * a pre-shared secret configured via the {@code api.security.key} property.
 *
 * <p>If the key is valid, a {@code ROLE_WEBHOOK_CLIENT} authority is set in the
 * {@link SecurityContextHolder}, allowing downstream authorization to proceed normally.
 * If missing or invalid, no authentication is set and Spring Security's
 * {@link org.springframework.security.web.access.ExceptionTranslationFilter} returns a 401.
 *
 * <p>This pattern is well-suited for machine-to-machine webhook integrations where
 * a single trusted consumer (Employees-R-US) holds a pre-shared secret.
 */
@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    static final String API_KEY_HEADER = "X-API-Key";

    @Value("${api.security.key}")
    private String validApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String providedKey = request.getHeader(API_KEY_HEADER);
        if (validApiKey.equals(providedKey)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    "webhook-client", null, List.of(new SimpleGrantedAuthority("ROLE_WEBHOOK_CLIENT")));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
