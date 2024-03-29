/*
 * Copyright (c) 2023 by multiple authors
 *
 * File name: GfxUtil.java
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
 * The software is provided "as is", without warranty of any kind, express or implied, including but not limited
 * to the warranties of merchantability, fitness for a particular purpose and noninfringement. In no event
 * shall the authors or copyright holders be liable for any claim, damages or other liability, whether in an
 * action of contract, tort or otherwise, arising from, out of or in connection with the software or the use
 * or other dealings in the software.
 */

package org.jmpsl.gfx;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;

/**
 * Class storing utilities static methods for graphics generators, senders and manipulators.
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Slf4j
public class GfxUtil {

    private GfxUtil() {
    }

    /**
     * Static method responsible for converting {@link BufferedImage} instance into byte array output stream.
     *
     * @param bufferedImage instance of {@link BufferedImage} class
     * @param extension image extension from {@link ImageExtension} enum class
     * @return byte array stream based passed {@link BufferedImage} instance
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed {@link BufferedImage} instance is null
     */
    public static byte[] generateByteStreamFromBufferedImage(BufferedImage bufferedImage, ImageExtension extension) {
        Assert.notNull(bufferedImage, "BufferedImage instance cannot be null.");
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, extension.getImageExtension(), byteArrayOutputStream);
            byteArrayOutputStream.close();
        } catch (IOException ex) {
            log.error("Unable to save BufferedImage object into output byte stream. Exception: {}", ex.getMessage());
        }
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Static method return default system font (from Java Swing {@link JEditorPane} Component).
     *
     * @return default system font as {@link Font} instance
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    public static Font getDefaultFont() {
        return new JEditorPane().getFont();
    }

    /**
     * Static method convert list of {@link Color} instances into string more readable format (for printing in logger
     * purposes).
     *
     * @param colors list of {@link Color} instances
     * @return string readable format of list of {@link Color} instances
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if any of {@link Color} instance in list is null
     */
    public static String toHexStringArray(List<Color> colors) {
        if (colors.stream().anyMatch(Objects::isNull)) {
            throw new NullPointerException("All color list instances cannot be null.");
        }
        return "[ " + colors.stream().map(GfxUtil::convertRgbToHex).collect(Collectors.joining(",")) + " ]";
    }

    /**
     * Static method responsible for converting {@link Color} instance as RGB into HEX notation (with #).
     *
     * @param color instance of {@link Color} class as RGB color
     * @return formatted string as HEX color, ex. #00f6h8
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed {@link Color} instance is null
     */
    public static String convertRgbToHex(Color color) {
        Assert.notNull(color, "Color instance cannot be null.");
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
