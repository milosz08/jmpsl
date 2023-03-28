/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: AbstractBaseRestExceptionListener.java
 * Last modified: 28/03/2023, 20:41
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

package org.jmpsl.core.exception;

import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.validation.FieldError;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

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
        final List<String> errors = ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage).toList();
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
