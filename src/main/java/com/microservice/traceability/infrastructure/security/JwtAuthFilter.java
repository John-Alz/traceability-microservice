package com.microservice.traceability.infrastructure.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.microservice.traceability.infrastructure.dto.AuthInfoDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (jwtToken != null) {

            jwtToken = jwtToken.substring(7);

            DecodedJWT decodedJWT = jwtUtils.verifyToken(jwtToken);
            String email = jwtUtils.extractSubject(decodedJWT);
            Long id = jwtUtils.extractSpecificClaim(decodedJWT, "id").asLong();
            String role = "ROLE_" + jwtUtils.extractSpecificClaim(decodedJWT, "authorities").asString();
            AuthInfoDto authInfo = new AuthInfoDto(id, email);
            Collection<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(authInfo, null, authorities);
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
        }

        filterChain.doFilter(request, response);
    }
}
