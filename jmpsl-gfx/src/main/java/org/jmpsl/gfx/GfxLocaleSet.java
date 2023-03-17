/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: GfxLocaleSet.java
 * Last modified: 17/03/2023, 14:26
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

package org.jmpsl.gfx;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.jmpsl.core.i18n.ILocaleEnumSet;

/**
 * Enum set storing all placeholders of internationalization messages for gfx JMPS library module.
 * Implementing {@link ILocaleEnumSet} interface from JMPSL core module.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 * @see ILocaleEnumSet
 */
@Getter
@RequiredArgsConstructor
public enum GfxLocaleSet implements ILocaleEnumSet {
    IMAGE_NOT_SUPPORTED_DIMENSIONS_EXC          ("jmpsl.gfx.exception.ImageNotSupportedDimensionsException"),
    FONT_SIZE_NOT_SUPPORTED_EXC                 ("jmpsl.gfx.exception.FontSizeNotSupportedException"),
    TOO_MUCH_INITIALS_CHARACTERS_EXC            ("jmpsl.gfx.exception.TooMuchInitialsCharactersException");

    /**
     * Internationalization property holder.
     *
     * @since 1.0.2
     */
    private final String holder;
}
