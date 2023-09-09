/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: AuthResolverForRest.java
 * Last modified: 18/05/2023, 01:08
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

package org.jmpsl.security.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.jmpsl.core.i18n.LocaleSet;
import org.jmpsl.core.i18n.LocaleMessageService;
import org.jmpsl.core.exception.ServerExceptionResDto;
import org.jmpsl.core.exception.GeneralServerExceptionResDto;

/**
 * Custom authentication entry point for REST Spring boot security context. DI instance must be declared in Spring
 * security methods chain in {@link SecurityFilterChain} bean. Override commence method resolve and return
 * localized message into client.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Component
public class AuthResolverForRest implements AuthenticationEntryPoint {

    private final LocaleMessageService localeMessageService;

    AuthResolverForRest(LocaleMessageService localeMessageService) {
        this.localeMessageService = localeMessageService;
    }

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException ex) throws IOException {
        final String message = localeMessageService.getMessage(LocaleSet.SECURITY_AUTHENTICATION_EXC);
        final var resDto = new GeneralServerExceptionResDto(ServerExceptionResDto.generate(HttpStatus.UNAUTHORIZED, req), message);
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        new Gson().toJson(resDto, GeneralServerExceptionResDto.class, res.getWriter());
    }
}
