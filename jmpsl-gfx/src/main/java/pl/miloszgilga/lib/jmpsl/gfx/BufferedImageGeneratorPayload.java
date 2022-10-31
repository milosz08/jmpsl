/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: BufferedImageGeneratorPayload.java
 * Last modified: 31/10/2022, 20:45
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

package pl.miloszgilga.lib.jmpsl.gfx;

import lombok.*;
import java.awt.*;

import pl.miloszgilga.lib.jmpsl.file.hashcode.FileHashCodeGenerator;

/**
 * Simple POJO class storing image generator payload (sending from application). This class implements
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
@Data
@Builder
@AllArgsConstructor
public class BufferedImageGeneratorPayload implements IBufferedImagePayload {
    private int size;
    private int fontSize;
    private char[] initials;
    private Long id;
    private String imageUniquePrefix;
    private String userHashCode;
    private Color preferredColor;
}
