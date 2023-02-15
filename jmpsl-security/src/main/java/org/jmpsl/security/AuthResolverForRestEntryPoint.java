/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: AuthResolverForRestEntryPoint.java
 * Last modified: 14/02/2023, 22:26
 * Project name: jmps-library
 *
 * Licensed under the MIT license; you may not use this file except in compliance with the License.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL
 * COPIES OR SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 */

package org.jmpsl.security;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.security.web.*;
import org.springframework.security.core.AuthenticationException;

import jakarta.servlet.http.*;

/**
 * Custom authentication entry point for REST Spring boot security context. DI instance must be declared in Spring
 * security methods chain in {@link SecurityFilterChain} bean. Overrice commence method resolve exception to
 * class with {@link RestControllerAdvice} annotation.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Component
public class AuthResolverForRestEntryPoint implements AuthenticationEntryPoint {

    private final HandlerExceptionResolver resolver;

    AuthResolverForRestEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) {
        resolver.resolveException(req, res, null, ex);
    }
}
