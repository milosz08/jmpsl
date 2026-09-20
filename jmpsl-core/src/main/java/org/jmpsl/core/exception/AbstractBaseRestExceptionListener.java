/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: AbstractBaseRestExceptionListener.java
 * Last modified: 19/05/2023, 00:44
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

package org.jmpsl.core.exception;

import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.security.core.AuthenticationException;

import java.util.Map;
import java.util.List;
import java.util.HashMap;

import org.jmpsl.core.i18n.LocaleSet;
import org.jmpsl.core.i18n.LocaleUtil;
import org.jmpsl.core.i18n.LocaleMessageService;

/**
 * Abstract base REST exceptions listeners for most typical exceptions in RestAPIs. Apply this class on Spring bean with
 * {@link RestControllerAdvice} annotation.
 *
 * @author Miłosz Gilga
 * @since 1.0.2_02
 */
@Slf4j
public abstract class AbstractBaseRestExceptionListener {

    private final LocaleMessageService messageService;

    public AbstractBaseRestExceptionListener(LocaleMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * Exception handler responsible for catching all {@link RestServiceServerException} exceptions and sending
     * response as JSON string format.
     *
     * @param ex catching {@link RestServiceServerException} object
     * @param req {@link HttpServletRequest} object
     * @return JSON response entity
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    @ExceptionHandler(RestServiceServerException.class)
    public ResponseEntity<?> restServiceServerException(RestServiceServerException ex, HttpServletRequest req) {
        final String messageWithParams = messageService.getMessage(ex.getLocaleEnumSet());
        final String message = LocaleUtil.extractVariablesFromMessage(messageWithParams, ex.getVariables());
        log.error("REST API exception. Cause: {}", ex.getMessage());
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(
            ex.getStatus(), req), message), ex.getStatus());
    }

    /**
     * Exception handler responsible for catching all {@link NoHandlerFoundException} exceptions and sending
     * response as JSON string format.
     *
     * @param req {@link HttpServletRequest} object
     * @return JSON response entity
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNotFoundError(NoHandlerFoundException ex, HttpServletRequest req) {
        final String message = messageService.getMessage(LocaleSet.NO_HANDLER_FOUND_EXC);
        log.error("Not found. Cause: {}", ex.getMessage());
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(
            HttpStatus.NOT_FOUND, req), message), HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler responsible for catching all {@link RestServiceAuthServerException} exceptions and sending
     * response as JSON string format.
     *
     * @param ex catching {@link RestServiceAuthServerException} object
     * @param req {@link HttpServletRequest} object
     * @return JSON response entity
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(RestServiceAuthServerException.class)
    public ResponseEntity<?> restServiceServerException(RestServiceAuthServerException ex, HttpServletRequest req) {
        final String messageWithParams = messageService.getMessage(ex.getLocaleEnumSet());
        final String message = LocaleUtil.extractVariablesFromMessage(messageWithParams, ex.getVariables());
        log.error("Unauthorized. Cause: {}", ex.getMessage());
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(
            HttpStatus.UNAUTHORIZED, req), message), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Exception handler responsible for catching all {@link AuthenticationException} exceptions and sending
     * response as JSON string format.
     *
     * @param ex catching {@link AuthenticationException} object
     * @param req {@link HttpServletRequest} object
     * @return JSON response entity
     * @author Miłosz Gilga
     * @since 1.0.2_04
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<?> restServiceServerException(AuthenticationException ex, HttpServletRequest req) {
        log.error("Spring security authentication exception. Cause: {}", ex.getMessage());
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(
            HttpStatus.UNAUTHORIZED, req), ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Exception handler responsible for catching all {@link MethodArgumentNotValidException} exceptions and sending
     * response as JSON string format.
     *
     * @param ex catching {@link MethodArgumentNotValidException} object
     * @param req {@link HttpServletRequest} object
     * @return JSON response entity
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> invalidArgumentException(MethodArgumentNotValidException ex, HttpServletRequest req) {
        final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        if (fieldErrors.isEmpty()) {
            final ObjectError objectError = ex.getBindingResult().getAllErrors().get(0);
            return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(
                HttpStatus.BAD_REQUEST, req), messageService.getMessage(objectError.getDefaultMessage())),
                HttpStatus.BAD_REQUEST);
        }
        final Map<String, String> errors = new HashMap<>();
        for (final FieldError fieldError : fieldErrors) {
            errors.put(fieldError.getField(), messageService.getMessage(fieldError.getDefaultMessage()));
        }
        log.error("Bad request. Cause: {}", ex.getMessage());
        return new ResponseEntity<>(new InvalidDtoExceptionResDto(ServerExceptionResDto.generate(
            HttpStatus.BAD_REQUEST, req), errors), HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler responsible for catching all {@link HttpMessageNotReadableException} exceptions and sending
     * response as JSON string format.
     *
     * @param req {@link HttpServletRequest} object
     * @return JSON response entity
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> reqResParseException(HttpMessageNotReadableException ex, HttpServletRequest req) {
        final String message = messageService.getMessage(LocaleSet.HTTP_MESSAGE_NOT_READABLE_EXC);
        log.error("Internal server error. Cause: {}", ex.getMessage());
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(
            HttpStatus.INTERNAL_SERVER_ERROR, req), message), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler responsible for catching all {@link Exception} exceptions and sending
     * response as JSON string format.
     *
     * @param req {@link HttpServletRequest} object
     * @return JSON response entity
     * @author Miłosz Gilga
     * @since 1.0.2_02
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalServerException(Exception ex, HttpServletRequest req) {
        final String message = messageService.getMessage(LocaleSet.INTERNAL_SERVER_ERROR_EXC);
        log.error("Internal server error. Cause: {}", ex.getMessage());
        return new ResponseEntity<>(new GeneralServerExceptionResDto(ServerExceptionResDto.generate(
            HttpStatus.INTERNAL_SERVER_ERROR, req), message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
