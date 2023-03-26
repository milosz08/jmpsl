/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: NotAcceptableFileExtensionException.java
 * Last modified: 17/03/2023, 16:02
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
