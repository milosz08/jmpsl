/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: ImageExtension.java
 * Last modified: 31/10/2022, 21:42
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
import lombok.AllArgsConstructor;

/**
 * Enum class storing all available images extensions.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Getter
@AllArgsConstructor
public enum ImageExtension {

    /**
     * Png image extension enum type.
     *
     * @since 1.0.2
     */
    PNG("png"),

    /**
     * Jpeg image extension enum type.
     *
     * @since 1.0.2
     */
    JPEG("jpeg"),

    /**
     * Gif image extension enum type.
     *
     * @since 1.0.2
     */
    GIF("gif"),

    /**
     * Webp image extension enum type.
     *
     * @since 1.0.2
     */
    WEBP("webp");

    private final String imageExtension;
}
