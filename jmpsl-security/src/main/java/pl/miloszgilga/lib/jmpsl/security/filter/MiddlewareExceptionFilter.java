/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: MiddlewareExceptionFilter.java
 * Last modified: 15/10/2022, 21:27
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

package pl.miloszgilga.lib.jmpsl.security.filter;

import org.slf4j.*;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.security.web.authentication.logout.LogoutFilter;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * Spring security filter grabbed all exceptions in filterChain method. Filter must be declared with method
 * <code>addFilterBefore</code> with filter principal of {@link LogoutFilter} class. To grab the exceptions, application
 * must have declared bean of {@link RestControllerAdvice} or {@link ControllerAdvice} in Spring context.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Component
public class MiddlewareExceptionFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(MiddlewareExceptionFilter.class);
    private final HandlerExceptionResolver resolver;

    public MiddlewareExceptionFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain) {
        try {
            chain.doFilter(req, res);
        } catch (final Exception ex) {
            LOGGER.error("Filter chain exception resolver executed exception. Details: {}", ex.getMessage());
            resolver.resolveException(req, res, null, ex);
        }
    }
}
