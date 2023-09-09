/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: ImageGeneratorException.java
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
