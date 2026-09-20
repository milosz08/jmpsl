/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: MiddlewareExceptionFilter.java
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

package org.jmpsl.security.filter;

import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.web.authentication.logout.LogoutFilter;

/**
 * Spring security filter grabbed all exceptions in filterChain method. Filter must be declared with method
 * <code>addFilterBefore</code> with filter principal of {@link LogoutFilter} class. To grab the exceptions, application
 * must have declared bean of {@link RestControllerAdvice} or {@link ControllerAdvice} in Spring context.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
@Component
public class MiddlewareExceptionFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;

    public MiddlewareExceptionFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) {
        try {
            chain.doFilter(req, res);
        } catch (final Exception ex) {
            log.error("Filter chain exception resolver executed exception. Details: {}", ex.getMessage());
            resolver.resolveException(req, res, null, ex);
        }
    }
}
