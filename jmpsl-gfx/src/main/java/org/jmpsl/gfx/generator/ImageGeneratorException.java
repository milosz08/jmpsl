/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: ImageGeneratorException.java
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
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
 */

package org.jmpsl.gfx.generator;

import org.springframework.http.HttpStatus;

import org.jmpsl.core.i18n.LocaleSet;
import org.jmpsl.core.exception.RestServiceServerException;
import org.jmpsl.core.exception.RestServiceAuthServerException;

/**
 * Custom exceptions (extends {@link RestServiceServerException}) used in image generator JMPS library module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public class ImageGeneratorException {

    /**
     * Custom exception extending {@link RestServiceServerException}, returning JSON POJO object message and throw
     * after set auto-generated image dimensions to not supported.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class ImageNotSupportedDimensionsException extends RestServiceServerException {
        public ImageNotSupportedDimensionsException() {
            super(HttpStatus.BAD_REQUEST, LocaleSet.GFX_IMAGE_NOT_SUPPORTED_DIMENSIONS_EXC);
        }
    }

    /**
     * Custom exception extending {@link RestServiceAuthServerException}, returning JSON POJO object message and throw
     * after set auto-generated image font size to not supported dimensions.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class FontSizeNotSupportedException extends RestServiceServerException {
        public FontSizeNotSupportedException() {
            super(HttpStatus.BAD_REQUEST, LocaleSet.GFX_FONT_SIZE_NOT_SUPPORTED_EXC);
        }
    }

    /**
     * Custom exception extending {@link RestServiceAuthServerException}, returning JSON POJO object message and throw
     * after set auto-generated image initials more than 2 characters.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static class TooMuchInitialsCharactersException extends RestServiceServerException {
        public TooMuchInitialsCharactersException() {
            super(HttpStatus.BAD_REQUEST, LocaleSet.GFX_TOO_MUCH_INITIALS_CHARACTERS_EXC);
        }
    }

}
