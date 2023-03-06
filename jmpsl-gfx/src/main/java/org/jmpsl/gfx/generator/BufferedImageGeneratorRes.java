/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: BufferedImageGeneratorRes.java
 * Last modified: 01/11/2022, 16:05
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

package org.jmpsl.gfx.generator;

import java.awt.Color;

import org.jmpsl.gfx.sender.BufferedImageRes;

/**
 * Simple POJO record storing generated buffered image details as {@link BufferedImageRes} instance and generated image
 * color as {@link Color} instance.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
public record BufferedImageGeneratorRes(BufferedImageRes bufferedImageRes, Color generateImageBackground) {
}
