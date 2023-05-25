/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: NotAcceptableFileExtensionException.java
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

package org.jmpsl.file.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.jmpsl.file.ContentType;
import org.jmpsl.core.i18n.LocaleSet;
import org.jmpsl.core.exception.RestServiceServerException;

/**
 * Exception throw, when user sent file with unsupported extension. Extended {@link RestServiceServerException}, so return
 * JSON object in response body part.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class NotAcceptableFileExtensionException extends RestServiceServerException {
    public NotAcceptableFileExtensionException(ContentType... types) {
        super(HttpStatus.BAD_REQUEST, LocaleSet.FILE_NOT_ACCEPTABLE_FILE_EXTENSION_EXC, Map.of("extensions",
            Arrays.stream(types).map(ContentType::getRegularName).collect(Collectors.joining(", "))));
    }
}
