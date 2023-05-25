/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: AbstractJwtRequestFilter.java
 * Last modified: 28/03/2023, 23:14
 * Project name: jmps-library
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
 * file except in compliance with the License. You may obtain a copy of the License at
 *
 *     <http://www.apache.org/license/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under
 * the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the license.
 */

package org.jmpsl.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.io.IOException;
import java.util.function.Function;

/**
 * Abstract class providing basic JWT filter verification. After successfull verification, save user into Spring Security
 * Context container. Passed function expression responsible for custom validated JWT.
 *
 * @author Miłosz Gilga
 * @since 1.0.2_02
 */
public abstract class AbstractJwtRequestFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Function expression responsible for custom validation for JWT. Take token raw value and return extracted
     * user identifier (nickname or email).
     *
     * @since 1.0.2_02
     */
    private final Function<String, String> validateToken;

    public AbstractJwtRequestFilter(
        JwtService jwtService, UserDetailsService userDetailsService, Function<String, String> validateToken
    ) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.validateToken = validateToken;
    }

    /**
     * Validation JWT. Extracted from request headers, validate structure and extracted user principals. If all is good,
     * save found user in Spring Security Context container and move to next middleware.
     *
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
        throws ServletException, IOException {
        final String token = jwtService.extractToken(req);
        if (!StringUtils.hasText(token) || !jwtService.isValid(token).isValid()) {
            chain.doFilter(req, res);
            return;
        }
        final String userEmail = validateToken.apply(token);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        final var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authToken);
        chain.doFilter(req, res);
    }
}
