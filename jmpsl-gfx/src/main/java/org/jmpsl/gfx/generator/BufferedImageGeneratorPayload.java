/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: BufferedImageGeneratorPayload.java
 * Last modified: 06/03/2023, 17:16
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

package org.jmpsl.gfx.generator;

import lombok.Builder;

import java.awt.Color;

import org.jmpsl.gfx.IBufferedImagePayload;
import org.jmpsl.file.hashcode.FileHashCodeGenerator;

/**
 * Simple POJO record storing image generator payload (sending from application). This class implements
 * {@link IBufferedImagePayload} interface and override methods (by lombok annotation). Fields:
 *
 * <ul>
 *     <li><code>size</code> - image width and height (square)</li>
 *     <li><code>fontSize</code> - image font size (avg for image size 200x200 is 85pt)</li>
 *     <li><code>initials</code> - user initials as char array (only accept 2 elements)</li>
 *     <li><code>id</code> - user id (from database)</li>
 *     <li><code>imageUniquePrefix</code> - user image prefix (avatar, banner etc.)</li>
 *     <li><code>userHashCode</code> - user hash code (generated by {@link FileHashCodeGenerator}, from database)</li>
 *     <li><code>preferredColor</code> - generated image background color (from database, only for re-regenerate)</li>
 * </ul>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Builder
public record BufferedImageGeneratorPayload(
    int size,int fontSize, char[] initials, Long id, String imageUniquePrefix, String userHashCode, Color preferredColor
) implements IBufferedImagePayload {

    @Override
    public String getImageUniquePrefix() {
        return imageUniquePrefix;
    }

    @Override
    public String getUserHashCode() {
        return userHashCode;
    }

    @Override
    public Long getId() {
        return id;
    }
}
