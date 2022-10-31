/*
 * Copyright (c) 2022 by multiple authors
 *
 * File name: UserImageGenerator.java
 * Last modified: 31/10/2022, 20:54
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

import org.slf4j.*;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.io.IOException;
import java.util.stream.Collectors;
import java.awt.image.BufferedImage;

import static java.util.Objects.*;
import static java.awt.RenderingHints.*;
import static java.awt.Font.TRUETYPE_FONT;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import static pl.miloszgilga.lib.jmpsl.gfx.GfxUtil.*;
import static pl.miloszgilga.lib.jmpsl.gfx.ImageExtension.PNG;

/**
 * Class storing methods responsible for generating default user image. Before run application, declare following
 * properties in <code>application.properties</code> file:
 *
 * <ul>
 *     <li><code>jmpsl.gfx.user-gfx.preferred-font-link</code> - path to font *.ttf file in /resources directory</li>
 *     <li><code>jmpsl.gfx.user-gfx.preferred-font-name</code> - name of custom loaded font</li>
 *     <li><code>jmpsl.gfx.user-gfx.preferred-hex-colors</code> - preferred colors for image generator, as hex array</li>
 *     <li><code>jmpsl.gfx.user-gfx.preferred-foreground-color</code> - preferred image initials text color, by default #fff</li>
 * </ul>
 *
 * @author Miłosz Gilga
 * @since 1.0.2
 */
@Service
public class UserImageGenerator {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserImageGenerator.class);
    private static final Random RANDOM = new Random();

    private static final int MIN_IMAGE_SIZE = 20;
    private static final int MAX_IMAGE_SIZE = 5000;
    private static final int MAX_FONT_SIZE = 500;

    /**
     * Default static colors using for user image generator. Can be overrite by property
     * <code>jmpsl.file.user-gfx.preferred-hex-colors</code> in <code>application.properties</code> file.
     *
     * @since 1.0.2
     */
    private static final String[] DEF_COLORS = {
            "#f83f3d", "#fe5430", "#ff9634", "#ffbf41", "#cad958", "#85c15d", "#029489", "#00bcd2", "#1197ec",
            "#4151b0", "#6a3ab0", "#a128a9", "#ee1860",
    };

    private String fontName;
    private final Environment env;
    private final String foregroundImageColor;
    private final List<Color> preferredHexColors = new ArrayList<>();

    UserImageGenerator(Environment env) {
        foregroundImageColor = env.getProperty("jmpsl.gfx.user-gfx.preferred-foreground-color", "#ffffff");
        this.env = env;
        loadCustomFontFromExternalFile();
        convertAndLoadHexToRgbColorsArray();
        LOGGER.info("Successful insert properties in IserImageGenerator from application.properties file.");
    }

    /**
     * Method responsible for creating default user avatar (simple image with random color and initials). Generates
     * image based passed {@link BufferedImageGeneratorPayload} instance. If payload instance has preferredColor value,
     * then generate image with this color value instead of random color (from declared statically or in properties file).
     *
     * @param payload instance of {@link BufferedImageGeneratorPayload} class with image properties
     * @param extension {@link ImageExtension} enum type of image (png, jpeg etc.)
     * @return generated user image as byte array output stream
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalStateException if image size or font size is too tiny or large or initials array has not 2 elements
     */
    public byte[] generateDefaultUserImage(BufferedImageGeneratorPayload payload, ImageExtension extension) {
        if (payload.getSize() < MIN_IMAGE_SIZE || payload.getSize() > MAX_IMAGE_SIZE) {
            throw new IllegalStateException(String.format(
                    "Image size must be between %d and %d", MIN_IMAGE_SIZE, MAX_IMAGE_SIZE));
        }
        if (payload.getFontSize() < 1 || payload.getFontSize() > MAX_FONT_SIZE) {
            throw new IllegalStateException(String.format("Font size must be between 0 and %d", MAX_FONT_SIZE));
        }
        if (payload.getInitials().length != 2) {
            throw new IllegalStateException("User initials must have 2 characters.");
        }

        final String userInitials = String.valueOf(payload.getInitials());
        final BufferedImage bufferedImage = new BufferedImage(payload.getSize(), payload.getSize(), TYPE_INT_RGB);
        final Graphics2D graphics = bufferedImage.createGraphics();

        graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);
        graphics.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY);

        graphics.setFont(new Font(fontName, Font.PLAIN, payload.getFontSize()));
        if (nonNull(payload.getPreferredColor())) {
            graphics.setPaint(payload.getPreferredColor());
        } else {
            graphics.setPaint(preferredHexColors.get(RANDOM.nextInt(preferredHexColors.size() - 1)));
        }
        graphics.fillRect(0, 0, payload.getSize(), payload.getSize());
        graphics.setColor(Color.decode(foregroundImageColor));

        int xPos = (payload.getSize() - graphics.getFontMetrics().stringWidth(userInitials)) / 2;
        int yPos = ((payload.getSize() - graphics.getFontMetrics().getHeight()) / 2) + graphics.getFontMetrics().getAscent();

        graphics.drawString(userInitials, xPos, yPos);
        graphics.dispose();

        LOGGER.info("Successful generated default image for user. Image properties data: {}", payload);
        return generateByteStreamFromBufferedImage(bufferedImage, extension);
    }

    /**
     * Method responsible for creating default user avatar (simple image with random color and initials). Generates
     * image based passed {@link BufferedImageGeneratorPayload} instance. If payload instance has preferredColor value,
     * then generate image with this color value instead of random color (from declared statically or in properties file).
     * Generate image with .png extension. To generate image with another extension use {@link #generateDefaultUserImage}
     * metod with {@link ImageExtension} parameter.
     *
     * @param payload instance of {@link BufferedImageGeneratorPayload} class with image properties
     * @return generated user image as byte array output stream
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws IllegalArgumentException if passed user id is null
     * @throws IllegalStateException if image size or font size is too tiny or large or initials array has not 2 elements
     */
    public byte[] generateDefaultUserImage(BufferedImageGeneratorPayload payload) {
        return generateDefaultUserImage(payload, PNG);
    }

    /**
     * Inner method responsible for load custom font for user image generator. If custom font not specified, load default
     * system font. Otherwise load font from <code>/resources</code> directory.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     *
     * @throws NullPointerException if <code>jmpsl.file.user-gfx.preferred-font-name</code> property is null
     */
    private void loadCustomFontFromExternalFile() {
        final String fontPath = env.getProperty("jmpsl.gfx.user-gfx.preferred-font-link");
        if (isNull(fontPath)) {
            fontName = getDefaultFont().getFontName();
            return;
        }
        fontName = requireNonNull(env.getProperty("jmpsl.gfx.user-gfx.preferred-font-name"));
        try {
            final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
            final ClassPathResource resource = new ClassPathResource(fontPath);
            graphicsEnvironment.registerFont(Font.createFont(TRUETYPE_FONT, resource.getFile()));
            LOGGER.info("Successful loaded custom font from external file. Font full path: {}", fontPath);
        } catch (IOException | FontFormatException ex) {
            LOGGER.error("Unable to load custom font from file. Exception: {}", ex.getMessage());
        }
    }

    /**
     * Inner method responsible for load preferred randomizer user generator image colors from
     * <code>application.properties</code> file. If colors array is null, insert default colors, declared in this class.
     *
     * @author Miłosz Gilga
     * @since 1.0.2
     */
    private void convertAndLoadHexToRgbColorsArray() {
        final String hexColors = env.getProperty("jmpsl.gfx.user-gfx.preferred-hex-colors");
        if (isNull(hexColors) || hexColors.split(",").length == 0) {
            preferredHexColors.addAll(Arrays.stream(DEF_COLORS).map(Color::decode).collect(Collectors.toSet()));
            return;
        }
        for (final String hexColor : hexColors.split(",")) {
            try {
                preferredHexColors.add(Color.decode(hexColor));
            } catch (NumberFormatException ex) {
                LOGGER.error("Passed hex color is invalid. Color {} not loaded.", hexColor);
            }
        }
        LOGGER.info("Successful loaded user image generator colors: {}", toHexStringArray(preferredHexColors));
    }
}
