/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: AuthResolverForRestEntryPoint.java
 * Last modified: 20/02/2023, 03:01
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

package org.jmpsl.security.resolver;

import com.google.gson.Gson;

import org.springframework.security.web.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.AuthenticationException;

import org.jmpsl.communication.locale.LocaleMessageService;
import org.jmpsl.core.exception.GeneralServerExceptionResDto;

import java.io.IOException;
import jakarta.servlet.http.*;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.jmpsl.core.exception.ServerExceptionResDto.generate;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Custom authentication entry point for REST Spring boot security context. DI instance must be declared in Spring
 * security methods chain in {@link SecurityFilterChain} bean. Override commence method resolve and return
 * localized message into client.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@ControllerAdvice
public class AuthResolverForRest implements AuthenticationEntryPoint {

    private final LocaleMessageService localeMessageService;

    AuthResolverForRest(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException {
        final String message = localeMessageService.getMessage("jmpsl.security.AuthenticationException");
        final var resDto = new GeneralServerExceptionResDto(generate(UNAUTHORIZED, req), message);
        res.setStatus(UNAUTHORIZED.value());
        res.setContentType(APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(UTF_8.toString());
        new Gson().toJson(resDto, GeneralServerExceptionResDto.class, res.getWriter());
    }
}
