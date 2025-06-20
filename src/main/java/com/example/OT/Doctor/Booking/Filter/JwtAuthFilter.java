package com.example.OT.Doctor.Booking.Filter;

import com.example.OT.Doctor.Booking.Config.JwtUtils;
import com.example.OT.Doctor.Booking.Service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JwtAuthFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private final List<RequestMatcher> permitAllMatchers;

    public JwtAuthFilter() {
        this.permitAllMatchers = Arrays.asList(
                new AntPathRequestMatcher("/api/symptoms")
        ).stream().collect(Collectors.toList());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        boolean isPermitAll = permitAllMatchers.stream()
                .anyMatch(matcher -> matcher.matches(request));

        if (isPermitAll) {
            logger.info("Bypassing JWT authentication for permitAll endpoint: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = parseJwt(request);
            logger.info("JWT Token received: {}", jwt);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                Long userId = jwtUtils.getUserIdFromJwtToken(jwt);
                logger.info("Extracted userId from token: {}", userId);

                String role = jwtUtils.getClaimFromJwtToken(jwt, "role");
                if (role == null) {
                    logger.warn("No role found in token for userId: {}", userId);
                    throw new IllegalArgumentException("Role not found in token");
                }
                logger.info("Role extracted from token: {}", role);

                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
                UserDetails userDetails = userDetailsService.loadUserById(userId);
                logger.info("User authorities from database: {}", userDetails.getAuthorities());

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, Collections.singletonList(authority));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("Authentication set for userId: {} with authorities: {}", userId, authentication.getAuthorities());
            } else {
                logger.warn("Invalid or missing JWT token");
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}