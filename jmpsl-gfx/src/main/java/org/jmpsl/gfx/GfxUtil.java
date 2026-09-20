/*
 * Copyright (c) 2023 by MULTIPLE AUTHORS
 *
 * File name: GfxUtil.java
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
