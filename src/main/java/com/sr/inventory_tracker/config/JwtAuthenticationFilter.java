package com.sr.inventory_tracker.config;

import com.sr.inventory_tracker.service.CustomUserDetailsService;
import com.sr.inventory_tracker.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;

    public JwtAuthenticationFilter(CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        log.info("Authorization Header: {}", authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.info("Authorization header is missing or not a Bearer token.");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7);
        try {
            String username = jwtService.extractUsername(token);

            log.info("Extracted JWT: {}", token);
            log.info("Extracted username: {}", username);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            log.info("Current Authentication: {}", authentication);

            if (authentication == null && username != null) {
                log.info("No valid authentication found. Attempting to authenticate the user.");

                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

                if (jwtService.isValidToken(token, userDetails)) {

                    log.info("Validated JWT token");

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    log.info("Authentication token set in the security context for user: {}", username);

                }

            }
        } catch (ExpiredJwtException e) {
            log.error("JWT Token has expired: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("An error occurred while processing the JWT token: {}", e.getMessage());
            throw e;
        }

        filterChain.doFilter(request, response);

    }
}
