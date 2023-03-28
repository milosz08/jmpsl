/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: AbstractJwtRequestFilter.java
 * Last modified: 28/03/2023, 14:53
 * Project name: jmps-library
 *
 * Licensed under the MIT license; you may not use this file except in compliance with the License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL COPIES OR
 * SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 *
 * The software is provided “as is”, without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
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
